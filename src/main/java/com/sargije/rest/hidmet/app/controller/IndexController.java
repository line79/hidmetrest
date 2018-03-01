package com.sargije.rest.hidmet.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	/*
	 @RequestMapping("/greetings")
	    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
	        model.addAttribute("name", name);
	        return "greetings";
	    }
	*/ 
	 @RequestMapping("/")
	    public String greeting(Model model) {
	        return "basic";
	    }
}
