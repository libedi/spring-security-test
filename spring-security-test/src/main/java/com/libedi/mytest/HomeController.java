package com.libedi.mytest;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.libedi.mytest.vo.MemberInfo;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value={"/", "/main"})
	public String main(HttpServletRequest request, Model model){
		logger.info("main");
		
		Authentication auth = (Authentication)request.getUserPrincipal();
		String name = "";
		if(auth != null){
			Object principle = auth.getPrincipal();
			if(principle != null && principle instanceof MemberInfo){
				name = ((MemberInfo)principle).getName();
			}
		}
		model.addAttribute("name", name);
		
		return "main";
	}
	
	@RequestMapping(value="/login")
	public String login(Model model){
		logger.info("login");
		return "login";
	}
	
}
