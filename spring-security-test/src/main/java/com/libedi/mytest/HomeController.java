package com.libedi.mytest;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.libedi.mytest.vo.MemberInfo;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
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
	
	/**
	 * 암호화 테스트 화면<br/>
	 * 회원가입시나 정보수정시 사용하면 됨
	 * @param targetStr
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/testEncode", method={RequestMethod.GET, RequestMethod.POST})
	public String testEncode(@RequestParam(value="targetStr", required=false, defaultValue="")String targetStr, Model model){
		if(StringUtils.hasText(targetStr)){
			// 암호화 작업
			String bCryptString = this.passwordEncoder.encode(targetStr);
			logger.debug("targetStr : {}, encodeStr : {}", targetStr, bCryptString);
			model.addAttribute("encodeStr", bCryptString);
		}
		return "testEncode";
	}
	
}
