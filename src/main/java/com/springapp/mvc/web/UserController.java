package com.springapp.mvc.web;

import com.springapp.mvc.entity.User;
import com.springapp.mvc.service.RoleService;
import com.springapp.mvc.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.HashMap;

@Controller
@RequestMapping("user")
public class UserController {
    static final Logger logger = Logger.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "")
    public String users(ModelMap modelMap) {
        modelMap.addAttribute("user", new User());
        modelMap.addAttribute("userList", userService.getAll());
        modelMap.addAttribute("sourceRoles", userService.getAllRoles());
        modelMap.addAttribute("action", "add");
        return "user";
    }

    @RequestMapping(value = "/profile/{userName}", method = RequestMethod.GET)
    public String getUserProfile(@PathVariable String userName, ModelMap map) {
        User user = userService.getUser(userName);
        if (user == null) {
            map.addAttribute("errorMessage", String.format("User %s not exist or was deleted", userName));
            return "404";
        } else {
            map.addAttribute("user", user);
            return "profile";
        }

    }

    /*  //@ModelAttribute @Valid Shop shop,
      BindingResult result,
      final RedirectAttributes redirectAttributes*/

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(@Valid User user, BindingResult bindingResult, ModelMap modelMap) {
        if(bindingResult.hasErrors())
        {
            modelMap.addAttribute("userList", userService.getAll());
            modelMap.addAttribute("sourceRoles", userService.getAllRoles());
            modelMap.addAttribute("action", "add");
            return "user";
        }
        user.setRole(roleService.get(user.getRole().getId()));
        logger.info(String.format("INFO: User added//name=%s surname=%s nickname=%s",user.getName(),user.getSurname(),user.getNickname()));
        userService.add(user);
        return "redirect:/user";
    }

    @RequestMapping(value = "/edit/{userId}", method = RequestMethod.GET)
    public String editUser(@PathVariable int userId, ModelMap modelMap) {
        modelMap.addAttribute("user", userService.get(userId));
        modelMap.addAttribute("userList", userService.getAll());
        modelMap.addAttribute("sourceRoles", userService.getAllRoles());
        modelMap.addAttribute("action", "edit");
        HashMap<Integer, Integer> integerHashMap= null;
        return "user";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute User user) {
       logger.info(String.format("INFO: User updated//name=%s surname=%s nickname=%s",user.getName(),user.getSurname(),user.getNickname()));
        userService.update(user);
        return "redirect:/user";
    }


    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("userId") int userId) {
        userService.delete(userId);
        return "redirect:/user";
    }


}
