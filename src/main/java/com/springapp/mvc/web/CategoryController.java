package com.springapp.mvc.web;

import com.springapp.mvc.entity.Category;
import com.springapp.mvc.service.CategoryService;
import com.springapp.mvc.utils.CheckBoxesContainer;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.log4j.Logger;
@Controller
public class CategoryController {
    static final Logger logger = Logger.getLogger(CategoryController.class);


    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = {"categories", "Categories", "Category"})
    public String categoriesHome() {
        return "redirect:category";
    }

    @RequestMapping(value = "category")
    public String categories(ModelMap modelMap) {
        modelMap.addAttribute("category", new Category());
        modelMap.addAttribute("categoriesContainer", new CheckBoxesContainer());
        modelMap.addAttribute("sourceList", categoryService.getAll());
        modelMap.addAttribute("action", "add");
        return "category";
    }

    @RequestMapping(value = "/categoryDelete", method = RequestMethod.GET)
    @ResponseBody
    public String deleteCategory(@RequestParam MultiValueMap<String, String> params) {
        JSONObject json = new JSONObject();
        Integer categoryId = Integer.parseInt(params.getFirst("category"));
        logger.info("catched "+categoryId);
        categoryService.delete(categoryId);
        try {
            json.put("status","OK");

        } catch (JSONException e) {
            logger.error("can't form Json response "+ e);
        }
        return (json.toString());
    }


    @RequestMapping(value = "/categoryAdd", method = RequestMethod.GET)
    @ResponseBody
    public String addCategory(@RequestParam MultiValueMap<String, String> params) {
        JSONObject json = new JSONObject();
        String name = params.getFirst("name");

        Category category = new Category();
        category.setName(name);
        categoryService.add(category);

        Integer idCategory = category.getId();

        try {
            json.put("status","OK");
            json.put("idCategory", idCategory);
        } catch (JSONException e) {
            logger.error("can't form Json response "+ e);
        }
        return (json.toString());
    }

    @RequestMapping(value = "/categoryUpdate", method = RequestMethod.GET)
    @ResponseBody
    public String updateCategory(@RequestParam MultiValueMap<String, String> params) {
        JSONObject json = new JSONObject();
        String name = params.getFirst("name");
        Integer id = Integer.parseInt(params.getFirst("id"));
        Category category = new Category();
        category.setName(name);
        category.setId(id);
        categoryService.update(category);

        try {
            json.put("status","OK");

        } catch (JSONException e) {
            logger.error("can't form Json response"+ e);
        }
        return (json.toString());
    }



}