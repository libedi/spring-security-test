package com.libedi.mytest.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * 인증 실패 핸들러
 * @author libedi
 *
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	private Log logger = LogFactory.getLog(this.getClass());
	
	private String loginIdName;				// 로그인 id 값이 들어오은 input 태그 name
	private String loginPasswordName;		// 로그인 password 값이 들어오는 input 태그 name
	private String loginRedirectName;		// 로그인 성공시 redirect 할 URL이 지정되어 있는 input 태그 name
	private String exceptionMsgName;		// 예외 메시지를 request의 attribute에 저장할 떄 사용될 key값
	private String defaultFailureUrl;		// 화면에 보여줄 URL(로그인 화면)

	public CustomAuthenticationFailureHandler() {
		this.loginIdName 		= "j_username";
		this.loginPasswordName 	= "j_password";
		this.loginRedirectName 	= "loginRedirect";
		this.exceptionMsgName 	= "securityExceptionMsg";
		this.defaultFailureUrl 	= "/login.do";
	}
	
	public String getLoginIdName() {
		return loginIdName;
	}

	public void setLoginIdName(String loginIdName) {
		this.loginIdName = loginIdName;
	}

	public String getLoginPasswordName() {
		return loginPasswordName;
	}

	public void setLoginPasswordName(String loginPasswordName) {
		this.loginPasswordName = loginPasswordName;
	}

	public String getLoginRedirectName() {
		return loginRedirectName;
	}

	public void setLoginRedirectName(String loginRedirectName) {
		this.loginRedirectName = loginRedirectName;
	}

	public String getExceptionMsgName() {
		return exceptionMsgName;
	}

	public void setExceptionMsgName(String exceptionMsgName) {
		this.exceptionMsgName = exceptionMsgName;
	}

	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		/*
		 * TODO request 객체의 attribute에 사용자가 실패시 입력했던
		 * 로그인 id와 비밀번호를 저장해두어 로그인 페이지에서 이를 접근하도록 한다.
		 */
		String loginId = request.getParameter(this.loginIdName);
		String loginPassword = request.getParameter(this.loginPasswordName);
		String loginRedirect = request.getParameter(this.loginRedirectName);
		
		request.setAttribute(this.loginIdName, loginId);
		request.setAttribute(this.loginPasswordName, loginPassword);
		request.setAttribute(this.loginRedirectName, loginRedirect);
		
		logger.debug(request.getSession(false).getAttribute("SPRING_SECURITY_LAST_EXCEPTION"));
		// request 객체의 attribute에 예외 메시지 저장
		logger.debug(exception.getMessage());
		request.setAttribute(this.exceptionMsgName, exception.getMessage());
		
		// 로그인 실패한 request와 response를 그대로 넘기기 위해 forward 한다.
		request.getRequestDispatcher(this.defaultFailureUrl).forward(request, response);
	}
}
