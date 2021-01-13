package com.digipower.sercurity.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.digipower.common.entity.Result;
import com.digipower.sercurity.entity.JwtUserDetails;
import com.digipower.sercurity.token.VerificationCodeAuthenticationToken;
import com.digipower.sercurity.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VerificationCodeLoginFilter extends AbstractAuthenticationProcessingFilter {
	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
	public static final String SPRING_SECURITY_FORM_CODE_KEY = "code";

	private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
	private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
	private String codeParameter = SPRING_SECURITY_FORM_CODE_KEY;
	

	/**
	 * 是否仅 POST 方式
	 */
	private boolean postOnly = true;

	 /**
     * 获取授权管理, 创建VerificationCodeLoginFilter时获取
     */
    private AuthenticationManager authenticationManager;
	

	public VerificationCodeLoginFilter(String defaultFilterProcessesUrl,AuthenticationManager authenticationManager) {
	
		super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "POST"));
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		// TODO Auto-generated method stub
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		String username = obtainUsername(request);
		String password = obtainPassword(request);
		String code = obtainCode(request);

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		if (code == null) {
			code = "";
		}

		username = username.trim();
		code = code.trim();

		VerificationCodeAuthenticationToken authRequest = new VerificationCodeAuthenticationToken(username, password,
				code);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return authenticationManager.authenticate(authRequest);
	}

	protected String obtainPassword(HttpServletRequest request) {
		return request.getParameter(passwordParameter);
	}

	protected String obtainUsername(HttpServletRequest request) {
		return request.getParameter(usernameParameter);
	}

	protected String obtainCode(HttpServletRequest request) {
		return request.getParameter(codeParameter);
	}

	protected void setDetails(HttpServletRequest request, VerificationCodeAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	/**
	 * TODO 一旦调用 springSecurity认证登录成功，立即执行该方法
	 *
	 * @param request
	 * @param response
	 * @param chain
	 * @param authResult
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException {

		// 生成jwt,并返回
		VerificationCodeAuthenticationToken token = (VerificationCodeAuthenticationToken)authResult;
		JwtUserDetails userEntity = new JwtUserDetails();
		if(token.getPrincipal() != null){
			userEntity.setUserName(String.valueOf(token.getPrincipal()));
		}
		if(token.getAuthorities() != null && token.getAuthorities().size() > 0){
			userEntity.setAuthorities(token.getAuthorities());
		}
		if(token.getCredentials() != null){
			userEntity.setPassword(String.valueOf(token.getCredentials()));
		}
		String jwtToken = JwtTokenUtil.generateToken(userEntity);

		ObjectMapper objectMapper = new ObjectMapper();
		response.setContentType("application/json;charset=UTF-8");
		ServletOutputStream out = response.getOutputStream();
		String str = objectMapper.writeValueAsString(Result.ok().setDatas("token", jwtToken));
		out.write(str.getBytes("UTF-8"));
		out.flush();
		out.close();
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException ex) throws IOException, ServletException {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			if (ex instanceof UsernameNotFoundException || ex instanceof BadCredentialsException) {
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().write(objectMapper.writeValueAsString(Result.error("401", "用户名或密码错误")));
			} else if (ex instanceof InternalAuthenticationServiceException) {
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().write(objectMapper.writeValueAsString(Result.error("401", "没有账号信息")));
			} else if (ex instanceof DisabledException) {
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().write(objectMapper.writeValueAsString(Result.error("401", "账户被禁用")));
			} else {
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().write(objectMapper.writeValueAsString(Result.error("401", "登录失败!")));
			}
		} catch (Exception e) {

		}
	}

}
