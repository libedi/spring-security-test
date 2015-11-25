package com.libedi.mytest.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	private String targetUrlParameter;
	private String defaultUrl;
	private boolean useReferer;
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public CustomAuthenticationSuccessHandler() {
		this.targetUrlParameter = "";
		this.defaultUrl = "/";
		this.useReferer = false;
	}
	
	public String getTargetUrlParameter() {
		return targetUrlParameter;
	}

	public void setTargetUrlParameter(String targetUrlParameter) {
		this.targetUrlParameter = targetUrlParameter;
	}

	public String getDefaultUrl() {
		return defaultUrl;
	}

	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}

	public boolean isUseReferer() {
		return useReferer;
	}

	public void setUseReferer(boolean useReferer) {
		this.useReferer = useReferer;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
			Authentication authentication) throws IOException, ServletException {
		/*
		 * TODO 이 예제에서는 로그인 후 이동할 URL을 지정하면 해당 URL로 이동하는 기능을 추가한다.
		 * 
		 * 1. 지정된 request 에 로그인 작업을 마친 뒤 redirect 할 URL을 지정했다면 이 URL로 redirect 한다.
		 * 2. 만약 지정된 URL이 없다면 세션에 저장된 URL로 redirect 한다.
		 * 3. 세션에 저장된 URL도 없다면, request의 REFERER 헤더값을 읽어서 로그인 페이지 방문하기 전 페이지의 URL을 읽어서 거기로 이동하도록 한다. (선택)
		 * 4. 위 세가지 모두 만족하지 않는다면, CustomAuthenticationSuccessHandler 클래스에 있는 defaultUrl 속성에 저장된 URL로 이동하도록 한다.
		 */
		
		clearAuthenticationAttributes(request);
		
		int intRedirectStrategy = this.decideRedirectStrategy(request, response);
		
		switch(intRedirectStrategy){
		case 1:
			this.useTargetUrl(request, response);
			break;
		case 2:
			this.useSessionUrl(request, response);
			break;
		case 3:
			this.useRefererUrl(request, response);
			break;
		case 0:
			this.useDefaultUrl(request, response);
		}
	}
	
	/**
	 * 인증 성공후 어떤 URL로 redirect 할지를 결정한다.<br/>
	 * 판단 기준은 targetUrlParameter 값을 읽으 URL이 존재할 경우 그것을 1순위<br/>
	 * 1순위 URL이 없을 경우, Spring Security가 세션에 저장한 URL을 2순위<br/>
	 * 2순위 URL이 없을 경우, Request의 REFERER를 사용하고 그 REFERER URL이 존재할 경우 그 URL을 3순위<br/>
	 * 3순위 URL이 없을 경우, Default URL을 4순위로 한다.
	 * @param request
	 * @param response
	 * @return	1 : targetUrlParameter 값을 읽은 URL<br/>
	 * 			2 : Session에 저장되어 있는 URL<br/>
	 * 			3 : referer 헤더에 있는 URL<br/>
	 * 			0 : default URL
	 */
	private int decideRedirectStrategy(HttpServletRequest request, HttpServletResponse response){
		int result = 0;
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		String targetUrl = request.getParameter(targetUrlParameter);
		String refererUrl = request.getHeader("REFERER");
		
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(targetUrlParameter) && StringUtils.hasText(targetUrl)){
			result = 1;
		} else if(savedRequest != null){
			result = 2;
		} else if(this.useReferer && StringUtils.hasText(refererUrl)){
			result = 3;
		} else {
			result = 0;
		}
		
		return result;
	}
	
	/**
     * Removes temporary authentication-related data which may have been stored in the session
     * during the authentication process.
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
	
	private void useTargetUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SavedRequest savedRequest = this.requestCache.getRequest(request, response);
		
		if(savedRequest != null){
			this.requestCache.removeRequest(request, response);
		}
		String targetUrl = request.getParameter(this.targetUrlParameter);
		this.redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	
	private void useSessionUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SavedRequest savedRequest = this.requestCache.getRequest(request, response);
		String targetUrl = savedRequest.getRedirectUrl();
		this.redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	
	private void useRefererUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String targetUrl = request.getHeader("REFERER");
		this.redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	private void useDefaultUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		this.redirectStrategy.sendRedirect(request, response, defaultUrl);
	}

}
