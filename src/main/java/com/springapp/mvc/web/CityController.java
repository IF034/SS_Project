package com.springapp.mvc.web;

import com.springapp.mvc.entity.City;
import com.springapp.mvc.service.CityService;
import com.springapp.mvc.utils.CheckBoxesContainer;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

@Controller
public class CityController {
    public final static int START_INDEX_FOR_PAGINATION = 0;
    public final static int MAX_NUMBER_OF_ELEMENTS_ON_PAGE = 5;
    public final static int DEFAULT_PAGE_NUMBER = 1;
    static final Logger logger = Logger.getLogger(CityController.class);

    @Autowired
    private CityService cityService;

    private ModelMap cityModelMap(){
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("city", new City());
        modelMap.addAttribute("citiesContainer", new CheckBoxesContainer());
        modelMap.addAttribute("action", "add");
        modelMap.addAttribute("startPage", "/city/");
     //   modelMap.addAttribute("numberOfPages", cityService.numberOfPagesForPagination(MAX_NUMBER_OF_ELEMENTS_ON_PAGE));

        return modelMap;
    }

    @RequestMapping(value = "city")
    public String cities(ModelMap modelMap) {
        modelMap.addAllAttributes(cityModelMap());
    //    modelMap.addAttribute("sourceList", cityService.getScope(START_INDEX_FOR_PAGINATION, MAX_NUMBER_OF_ELEMENTS_ON_PAGE));
    //    modelMap.addAttribute("numberOfPages", cityService.numberOfPagesForPagination(MAX_NUMBER_OF_ELEMENTS_ON_PAGE));
        modelMap.addAttribute("sourceList", cityService.getAll());
        modelMap.addAttribute("currentPageNumber", DEFAULT_PAGE_NUMBER);
        return "city";
    }

    @RequestMapping(value = "city/&currentPageNumber={pageNumber}", method = RequestMethod.GET)
    public String cities(ModelMap modelMap, @PathVariable int pageNumber) {
        modelMap.addAllAttributes(cityModelMap());
     //   modelMap.addAttribute("sourceList", cityService.getScope(MAX_NUMBER_OF_ELEMENTS_ON_PAGE *(pageNumber-1), MAX_NUMBER_OF_ELEMENTS_ON_PAGE));
     //   modelMap.addAttribute("numberOfPages", cityService.numberOfPagesForPagination(MAX_NUMBER_OF_ELEMENTS_ON_PAGE));
        modelMap.addAttribute("currentPageNumber", pageNumber);
        return "city";
    }

    @RequestMapping(value = "/cityDelete", method = RequestMethod.GET)
    @ResponseBody
    public String deleteCity(@RequestParam MultiValueMap<String, String> params) {
        JSONObject json = new JSONObject();
        Integer cityId = Integer.parseInt(params.getFirst("city"));
        logger.info("catched "+cityId);
        cityService.delete(cityId);
        try {
            json.put("status","OK");

        } catch (JSONException e) {
            logger.error("can't form Json response"+ e);
        }
        return (json.toString());
    }


    @RequestMapping(value = "/cityAdd", method = RequestMethod.GET)
    @ResponseBody
    public String addCity(@RequestParam MultiValueMap<String, String> params) {
        JSONObject json = new JSONObject();
        String name = params.getFirst("name");

        City city = new City();
        city.setName(name);
        cityService.add(city);

        Integer idCity = city.getId();

        try {
            logger.warn("add new city");
            json.put("status","OK");
            json.put("idCity", idCity);
        } catch (JSONException e) {
            logger.error("can't form Json response"+ e);
        }
        return (json.toString());
    }

    @RequestMapping(value = "/cityUpdate", method = RequestMethod.GET)
    @ResponseBody
    public String updateCity(@RequestParam MultiValueMap<String, String> params) {
        JSONObject json = new JSONObject();
        String name = params.getFirst("name");
        Integer id = Integer.parseInt(params.getFirst("id"));
        City city = new City();
        city.setName(name);
        city.setId(id);
        cityService.update(city);

        try {
            json.put("status","OK");

        } catch (JSONException e) {
            logger.error("can't form Json response"+ e);
        }
        return (json.toString());
    }


}
