package com.springapp.mvc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/access")
public class AccessController {

    @RequestMapping(value = "/denied")
    public String authAccessDenied() {
        return "access_denied";
    }

}
