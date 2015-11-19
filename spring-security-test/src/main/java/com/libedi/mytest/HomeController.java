package com.libedi.mytest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value={"/", "/main"})
	public String main(Model model){
		logger.info("main");
		return "main";
	}
	
	@RequestMapping(value="/login")
	public String login(Model model){
		logger.info("login");
		return "login";
	}
	
}
