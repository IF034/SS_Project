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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.log4j.Logger;

@Controller
public class CategoryController {
    static final Logger LOGGER = Logger.getLogger(CategoryController.class);
    public static final int DEFAULT_PAGE_NUMBER = 1;


    @Autowired
    private CategoryService categoryService;

    private ModelMap categoryModelMap() {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("category", new Category());
        modelMap.addAttribute("categoriesContainer", new CheckBoxesContainer());
        modelMap.addAttribute("action", "add");
        modelMap.addAttribute("startPage", "/category/");
        modelMap.addAttribute("numberOfPages", categoryService.getCategoriesPage(DEFAULT_PAGE_NUMBER).getTotalPages());

        return modelMap;
    }

    @RequestMapping(value = {"categories", "Categories", "Category" })
    public String categoriesHome() {
        return "redirect:category";
    }

    @RequestMapping(value = "category")
    public String categories(ModelMap modelMap) {
        modelMap.addAllAttributes(categoryModelMap());
        modelMap.addAttribute("sourceList", categoryService.getCategoriesPage(DEFAULT_PAGE_NUMBER).getContent());
        modelMap.addAttribute("currentPageNumber", DEFAULT_PAGE_NUMBER);
        return "category";
    }

    @RequestMapping(value = "category/&currentPageNumber={pageNumber}", method = RequestMethod.GET)
    public String cities(ModelMap modelMap, @PathVariable int pageNumber) {
        modelMap.addAllAttributes(categoryModelMap());
        modelMap.addAttribute("sourceList", categoryService.getCategoriesPage(pageNumber).getContent());
        modelMap.addAttribute("currentPageNumber", pageNumber);
        return "category";
    }


    @RequestMapping(value = "/categoryDelete", method = RequestMethod.GET)
    @ResponseBody
    public String deleteCategory(@RequestParam MultiValueMap<String, String> params) {
        JSONObject json = new JSONObject();
        Integer categoryId = Integer.parseInt(params.getFirst("category"));
        LOGGER.info("catched " + categoryId);
        categoryService.delete(categoryId);
        try {
            json.put("status", "OK");

        } catch (JSONException e) {
            LOGGER.error("can't form Json response " + e);
        }
        return json.toString();
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
            json.put("status", "OK");
            json.put("idCategory", idCategory);
        } catch (JSONException e) {
            LOGGER.error("can't form Json response " + e);
        }
        return json.toString();
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
            json.put("status", "OK");

        } catch (JSONException e) {
            LOGGER.error("can't form Json response" + e);
        }
        return json.toString();
    }
}
