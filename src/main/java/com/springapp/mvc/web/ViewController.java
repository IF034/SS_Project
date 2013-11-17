package com.springapp.mvc.web;

import com.springapp.mvc.entity.Enterprise;
import com.springapp.mvc.service.CategoryService;
import com.springapp.mvc.service.CityService;
import com.springapp.mvc.service.EnterpriseService;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ViewController {
    static final Logger LOGGER = Logger.getLogger(ViewController.class);
    public static final int MAX_NUMBER_OF_ITEMS_ON_PAGE = 300;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private CategoryService categoryService;

    /*@Autowired
    private EnterpriseRatioService enterpriseRatioService;*/

    @Autowired
    private CityService cityService;

    @RequestMapping(value = {"views", "Views", "View" })
    public String viewsHome() {
        return "redirect:view";
    }

    @RequestMapping(value = "view")
    public String views(ModelMap modelMap) {
        modelMap.addAttribute("enterprise", new Enterprise());
        modelMap.addAttribute("enterpriseList", enterpriseService.getAll());
        modelMap.addAttribute("categoryList", categoryService.getAll());
        modelMap.addAttribute("allEnterprises", enterpriseService.getAllEnterprisesInCategories());
        modelMap.addAttribute("cityList", cityService.getAll());
        modelMap.addAttribute("action", "add");
        return "view";
    }

    @RequestMapping(value = "view/deleteEnterprise/{enterpriseId}", method = RequestMethod.GET)
    public String delEnterprise(@PathVariable int enterpriseId, ModelMap model) {
        enterpriseService.delete(enterpriseId);
        model.addAttribute("selectedTab", 1);
        return "forward:/view";
    }

    @RequestMapping(value = "/findEnterprises", method = RequestMethod.GET)
    @ResponseBody
    public String searchEnterprises(@RequestParam MultiValueMap<String, String> params) {
        JSONObject json = new JSONObject();
        /*String name = params.getFirst("name");*/
        Integer cityId = Integer.parseInt(params.getFirst("cityId"));
        Integer categoryId = Integer.parseInt(params.getFirst("categoryId"));
        Pageable pageable = new PageRequest(0, MAX_NUMBER_OF_ITEMS_ON_PAGE);
        Page<Enterprise> result = enterpriseService.getAllByForPage(categoryId, cityId, pageable);
        LOGGER.info(result.getContent().size());
        try {
            json.put("size", result.getContent().size());
            StringBuilder enterprisesId = new StringBuilder();
            for (Enterprise enterprise : result) {
                enterprisesId.append(enterprise.getId());
                enterprisesId.append("|");
            }
//            enterprisesId.deleteCharAt(enterprisesId.length() - 1);
            json.put("resultSet", enterprisesId.toString());
            json.put("status", "OK");
        } catch (JSONException e) {
            LOGGER.error("can't form Json response");

        }
        return (json.toString());
    }
}
