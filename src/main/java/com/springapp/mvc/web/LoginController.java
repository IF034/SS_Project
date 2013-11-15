package com.springapp.mvc.web;

import com.springapp.mvc.entity.User;
import com.springapp.mvc.service.RoleService;
import com.springapp.mvc.service.UserService;
import com.springapp.mvc.utils.openid.OpenIDUserReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: p0ma
 * Date: 14.10.13
 * Time: 0:48
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    private final String DEFAULT_ROLE_NAME = "ROLE_USER";
    private final String LOGINZA_URL = "http://loginza.ru/api/authinfo?token=";
    private final String NICKNAME_REGEX = "[^a-z^A-Z^0-9]";
    private final int NICKNAME_MAX_LENGTH = 30;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private OpenIDUserReader openIDUserReader;

    @RequestMapping(method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/failed", method = RequestMethod.GET)
    public String loginFailed(Model model) {
        model.addAttribute("error", "Login failed");
        return "login";
    }

    @RequestMapping(value = "/openid", method = RequestMethod.POST)
    public String loginOpenId(@RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        String url = LOGINZA_URL + token;

        User openIdUser = openIDUserReader.readUserFromUrl(url);

        redirectAttributes.addFlashAttribute("openIdUser", openIdUser);

        return "redirect:/login/openid/validate";


    }

    @RequestMapping(value = "/openid/failed", method = RequestMethod.GET)
    public String loginOpenIdFailed() {
        return "openid_login_failed";
    }

    @RequestMapping(value = "/openid/validate", method = RequestMethod.GET)
    public String validateOpenIdUser(@ModelAttribute("openIdUser") @Valid User openIdUser, BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("openIdUser", openIdUser);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.openIdUser", bindingResult
            );
            return "redirect:/login/openid/failed";
        }

        if (openIdUser != null) {
            User user = authenticateOpenIdUser(openIdUser);

            if (user != null) {

                String password = user.getPassword();

                PasswordEncoder encoder = new Md5PasswordEncoder();
                user.setPassword(encoder.encodePassword(password, null));
                userService.update(user);

                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities()
                        )
                );

                user.setPassword(password);
                userService.update(user);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else {
            return "redirect:/openid/failed";
        }

        return "redirect:/";
    }

    private User authenticateOpenIdUser(User newOpenIdUser) {
        User oldOpenIdUser = userService.getUserByOpenIdIdentity(newOpenIdUser.getOpenIdIdentity());

        if (oldOpenIdUser == null) {
            newOpenIdUser.setRole(roleService.getRole(DEFAULT_ROLE_NAME));
            newOpenIdUser.setNickname(generateUUID(NICKNAME_REGEX, NICKNAME_MAX_LENGTH));
            newOpenIdUser.setPassword(generateUUID(NICKNAME_MAX_LENGTH));
            userService.add(newOpenIdUser);
            oldOpenIdUser = newOpenIdUser;
        } else {
            if (checkOpenIdUserForUpdate(newOpenIdUser, oldOpenIdUser)) {
                newOpenIdUser.setId(oldOpenIdUser.getId());
                newOpenIdUser.setRole(oldOpenIdUser.getRole());
                userService.update(newOpenIdUser);
                oldOpenIdUser = newOpenIdUser;
            }
        }

        return oldOpenIdUser;
    }

    private boolean checkOpenIdUserForUpdate(User newOpenIdUser, User oldOpenIdUser) {
        if (!oldOpenIdUser.getName().equals(newOpenIdUser.getName())) {
            return true;
        }
        if (!oldOpenIdUser.getSurname().equals(newOpenIdUser.getSurname())) {
            return true;
        }
        if (!oldOpenIdUser.getMail().equals(newOpenIdUser.getMail())) {
            return true;
        }
        return false;
    }

    private String generateUUID(String regex, int length) {
        String uuid = UUID.randomUUID().toString().replaceAll(NICKNAME_REGEX, "");
        return uuid.substring(0, uuid.length() > length ? length : uuid.length());
    }

    private String generateUUID(int length) {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, uuid.length() > length ? length : uuid.length());
    }

}
