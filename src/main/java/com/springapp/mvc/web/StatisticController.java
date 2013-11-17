package com.springapp.mvc.web;

import com.springapp.mvc.entity.Category;
import com.springapp.mvc.entity.Enterprise;
import com.springapp.mvc.service.CategoryService;
import com.springapp.mvc.service.CityService;
import com.springapp.mvc.service.CommentService;
import com.springapp.mvc.service.EnterpriseRatioService;
import com.springapp.mvc.service.EnterpriseService;
import com.springapp.mvc.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StatisticController {
    static final Logger LOGGER = Logger.getLogger(StatisticController.class);
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

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = {"statistics", "Statistics", "Statistic" })
    public String statisticHome() {
        return "redirect:statistic";
    }

    @RequestMapping(value = "statistic")
    public String statistic(ModelMap modelMap) {
        modelMap.addAttribute("enterprise", new Enterprise());
        modelMap.addAttribute("enterpriseList", enterpriseService.getAll());
        modelMap.addAttribute("categoryList", categoryService.getAll());
        modelMap.addAttribute("cityList", cityService.getAll());
        modelMap.addAttribute("userList", userService.getAll());
        modelMap.addAttribute("categoryPairs", getCatNamePair());
        modelMap.addAttribute("allEnterprises", enterpriseService.getAllEnterprisesInCategories());
        modelMap.addAttribute("cityCounts", enterpriseService.countEnterprisesInAllCities());
        modelMap.addAttribute("EnterpriseVotePair", getEnterpriseVotePair());
        modelMap.addAttribute("categorySize", categoryService.getAll().size());
        modelMap.addAttribute("positiveRatings", commentService.getPositiveVotesOfAllUsers());
        modelMap.addAttribute("negativeRatings", commentService.getNegativeVotesOfAllUsers());
        modelMap.addAttribute("action", "add");
        return "statistic";
    }

    private List<AbstractMap.SimpleEntry<String, Integer>> getCatNamePair() {
        List<AbstractMap.SimpleEntry<String, Integer>> values =
                new ArrayList<AbstractMap.SimpleEntry<String, Integer>>();
        for (Category category : categoryService.getAll()) {
            values.add(new AbstractMap.SimpleEntry<String, Integer>(category.getName(),
                       enterpriseService.countEnterpriseByCategory(category.getId())));
            LOGGER.info(category.getName() + " | " + enterpriseService.countEnterpriseByCategory(category.getId()));
        }
        return values;
    }

    private List<AbstractMap.SimpleEntry<String, Integer>> getEnterpriseVotePair() {
        List<AbstractMap.SimpleEntry<String, Integer>> values =
                new ArrayList<AbstractMap.SimpleEntry<String, Integer>>();
        for (Enterprise enterprise : enterpriseService.getAll()) {
            values.add(new AbstractMap.SimpleEntry<String, Integer>(enterprise.getName(),
                      (enterpriseRatioService.getVoteValue(
                              enterprise.getId()) == 0 ? 1 : enterpriseRatioService.getVoteValue(enterprise.getId()))));
            LOGGER.info("Enterprise name: " + enterprise.getName() + " | "
                    + enterpriseRatioService.getVoteValue(enterprise.getId()));
        }
        return values;
    }

}
