package com.springapp.mvc.web;

import com.springapp.mvc.entity.Category;
import com.springapp.mvc.entity.Enterprise;
import com.springapp.mvc.entity.EnterpriseRatio;
import com.springapp.mvc.entity.User;
import com.springapp.mvc.service.CategoryService;
import com.springapp.mvc.service.CityService;
import com.springapp.mvc.service.EnterpriseRatioService;
import com.springapp.mvc.service.EnterpriseService;
import com.springapp.mvc.service.UserService;
import com.springapp.mvc.utils.UrlParameterValidator;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DefaultController {
    public static final int START_INDEX_FOR_PAGINATION = 0;
    public static final int MAX_NUMBER_OF_ELEMENTS_ON_PAGE = 5;
    public static final int TOP_ENTERPRISES_COUNT = 10;
    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int BUFFER_ARRAY_LENGTH = 8192;
    static final Logger LOGGER = Logger.getLogger(DefaultController.class);
    public static final int PAGE_SIZE = 5;

    @Autowired
    private UrlParameterValidator parameterValidator;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private EnterpriseRatioService enterpriseRatioService;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    private ModelMap indexModelAttribute() {
        ModelMap model = new ModelMap();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            User user = userService.getUser(auth.getName());
            if (user != null) {
                model.addAttribute("user", user);
            }
        }
        model.addAttribute("CategoryId", 0);
        model.addAttribute("AllEnterprisesCount", enterpriseService.countItems());
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("cities", cityService.getAll());
        model.addAttribute("enterprisesCount", method1(0));
        model.addAttribute("OrderByRatio", true);
        model.addAttribute("OrderByDiscount", true);
        model.addAttribute("AllEnterprisesInAllCats", enterpriseService.getAllEnterprisesInCategories());
        model.addAttribute("selectedCity", "Choose city");
        model.addAttribute("topList", enterpriseService.getTopList(TOP_ENTERPRISES_COUNT));
        model.addAttribute("startPage", "/index/");
        model.addAttribute("numberOfPages", enterpriseService.getEnterprisePage(DEFAULT_PAGE_NUMBER).getTotalPages());


        return model;
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @RequestMapping(value = {"", "/", "welcome" }, method = RequestMethod.GET)
    public String listEnterprises(ModelMap model) {

        model.addAllAttributes(indexModelAttribute());
        model.addAttribute("enterprises", enterpriseService.getEnterprisePage(DEFAULT_PAGE_NUMBER).getContent());
        model.addAttribute("currentPageNumber", DEFAULT_PAGE_NUMBER);

        return "index";
    }

    @RequestMapping(value = {"/index/&currentPageNumber={pageNumber}" }, method = RequestMethod.GET)
    public String listEnterprisesPagination(ModelMap model, @PathVariable int pageNumber) {

        model.addAllAttributes(indexModelAttribute());
        model.addAttribute("enterprises", enterpriseService.getEnterprisePage(pageNumber).getContent());
        /*model.addAttribute("enterprises", enterpriseService.getScope(MAX_NUMBER_OF_ELEMENTS_ON_PAGE *(pageNumber-1),
                                                                        MAX_NUMBER_OF_ELEMENTS_ON_PAGE));*/
        model.addAttribute("currentPageNumber", pageNumber);

        return "index";
    }


    @RequestMapping(value = "/params", method = RequestMethod.GET)
    public String listEnterprisesById(@RequestParam MultiValueMap<String, String> params, ModelMap model) {

        model.mergeAttributes(params.toSingleValueMap());
        int categoryId = parameterValidator.getInt(params.getFirst("CategoryId"));
        int townId = parameterValidator.getInt(params.getFirst("TownId"));
        int pageNumber = parameterValidator.getInt(params.getFirst("currentPageNumber"));
        /*boolean orderByDiscount = parameterValidator.getBoolean(params.getFirst("OrderByDiscount"));
        boolean orderByRatio = parameterValidator.getBoolean(params.getFirst("OrderByRatio"));
        int numberOfPagesForPagination = 0;*/
        String sortedByEnterpriseProperty = "name";
        Page<Enterprise> pageOfEnterprises = enterpriseService.getAllByForPage(categoryId, townId, pageNumber - 1,
                                                                                PAGE_SIZE, Sort.Direction.ASC,
                                                                                sortedByEnterpriseProperty);
        model.addAttribute("enterprises", pageOfEnterprises.getContent());
        model.addAttribute("numberOfPages", pageOfEnterprises.getTotalPages());
        model.addAttribute("enterprisesCount", method1(townId));
        model.addAttribute("AllEnterprisesCount", enterpriseService.countEnterprises(0, townId));
        model.addAttribute("selectedCity", townId == 0 ? "All" : cityService.get(townId).getName());
        model.addAttribute("topList", enterpriseService.getTopList(TOP_ENTERPRISES_COUNT));
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("cities", cityService.getAll());
        model.addAttribute("startPage", "/index/");

        return "index";
    }

    private List<AbstractMap.SimpleEntry<Category, Integer>> method1(int townId) {
        List<AbstractMap.SimpleEntry<Category, Integer>> values =
                new ArrayList<AbstractMap.SimpleEntry<Category, Integer>>();
        for (Category category : categoryService.getAll()) {
            values.add(new AbstractMap.SimpleEntry<Category, Integer>(category,
                       (int) enterpriseService.countEnterprises(category.getId(), townId)));
            LOGGER.info(category.getName() + " | " + enterpriseService.countEnterprises(category.getId(), townId));
        }
        return values;
    }


    @RequestMapping(value = "/ajaxParams", method = RequestMethod.GET)
    @ResponseBody
    public String ratioStars(@RequestParam MultiValueMap<String, String> params) {
        JSONObject json = new JSONObject();
        if (params.getFirst("user-id").equals("")) {
            try {
                json.put("status", "ERR");
                json.put("msg", "Login first, please!");
            } catch (JSONException e) {
                LOGGER.error("can't form Json response" + e);
            }
            return (json.toString());
        }
        Integer userId = Integer.parseInt(params.getFirst("user-id"));

        Integer enterpriseId = Integer.parseInt(params.getFirst("enterprise-id"));
        Integer value = Integer.parseInt(params.getFirst("score"));

        if (!(enterpriseRatioService.userAlreadyVote(userId, enterpriseId))) {
            EnterpriseRatio enterpriseRatio = new EnterpriseRatio();
            enterpriseRatio.setUser(userService.get(userId));
            enterpriseRatio.setEnterprise(enterpriseService.get(enterpriseId));
            enterpriseRatio.setValue(value);
            enterpriseRatioService.add(enterpriseRatio);
            enterpriseService.calculateSummaryRatio(enterpriseId);
            LOGGER.info("ratio added");
//            Integer votesValue = enterpriseRatioService.votesValue();
            try {
                json.put("status", "OK");
                json.put("msg", "Thank you");
            } catch (JSONException e) {
                LOGGER.error("can't form Json response" + e);
            }
            return (json.toString());
        } else {
            try {
                json.put("status", "ERR");
                json.put("msg", "You have already vote!");
            } catch (JSONException e) {
                LOGGER.error("can't form Json response" + e);
            }
            return (json.toString());
        }

    }

    @RequestMapping(value = "/ajaxRatio", method = RequestMethod.GET)
    @ResponseBody
    public String ratioStars1(@RequestParam MultiValueMap<String, String> params) {
        JSONObject json = new JSONObject();
        Integer user;
        if (!(params.getFirst("user").equals(""))) {
            user = Integer.parseInt(params.getFirst("user"));
        } else {
            user = 0;
        }
        Integer enterprise = Integer.parseInt(params.getFirst("enterprise"));
        Integer value = enterpriseRatioService.getVoteValue(enterprise);
        Integer votes = enterpriseRatioService.getVotes(enterprise);
        boolean readOnly = enterpriseRatioService.userAlreadyVote(user, enterprise);
        try {
            json.put("status", "OK");
            json.put("msg", value);
            json.put("vis", readOnly);
            json.put("value", value);
            json.put("votes", votes);
        } catch (JSONException e) {
            LOGGER.error("can't form Json response" + e);
        }
        return (json.toString());
    }

    @RequestMapping(value = "/image/{enterpriseId}", method = RequestMethod.GET)
    public void getImages(@PathVariable("enterpriseId") int enterpriseId, HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
            Enterprise enterprise = enterpriseService.get(enterpriseId);
            byte[] thumb = enterprise.getLogo();
            response.setContentType("image/jpeg");
            response.setContentLength(thumb.length);

            BufferedInputStream input = null;
            BufferedOutputStream output = null;

            try {
                input = new BufferedInputStream(new ByteArrayInputStream(thumb));
                output = new BufferedOutputStream(response.getOutputStream());
                byte[] buffer = new byte[BUFFER_ARRAY_LENGTH];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
            } catch (IOException e) {
                LOGGER.error("There are errors in reading/writing image stream "
                        + e.getMessage());
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException ignore) {
                        LOGGER.error(ignore);
                    }
                }
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException ignore) {
                        LOGGER.error(ignore);
                    }
                }
            }
    }


}
 /*   @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user, BindingResult result) {
        userRepository.save(user);
        return "redirect:/user";
    }*/


   /* @RequestMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId) {
        userRepository.delete(userRepository.findOne(userId));
        return "redirect:/user";
    }*/


