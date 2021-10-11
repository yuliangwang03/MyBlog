package com.wyl.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @author russe
 */
@Controller
public class AboutController {


    @GetMapping("/AboutMe")
    public String about(){
        return "AboutMe";
    }
}
