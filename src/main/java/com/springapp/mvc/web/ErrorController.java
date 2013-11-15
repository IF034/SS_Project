package com.springapp.mvc.web;// Created with IntelliJ IDEA by Yaroslav Kovbas (Xardas)

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("error")
public class ErrorController {
    static final Logger logger = Logger.getLogger(ErrorController.class);


    @RequestMapping("/404")
    public String handleNotFountError(@RequestParam MultiValueMap<String, String> params,ModelMap map){
       map.addAttribute("errorMessage", params.getFirst("error"));
        logger.fatal("Error 404"+ params.getFirst("error"));
       return "404";
    }

    @RequestMapping("/500")
    public String handle500Error(ModelMap map){
        map.addAttribute("exception", new Exception("Unsuspected user behavior ^^ "));
        logger.fatal("Error 404 Unsuspected user behavior ^^");
        return "500";
    }

}
