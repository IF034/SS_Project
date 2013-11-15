package com.springapp.mvc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * User: student
 * Date: 10/14/13
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping(value = "/access")
public class AccessController {

    @RequestMapping(value = "/denied")
    public String authAccessDenied()
    {
        return "access_denied";
    }

}
