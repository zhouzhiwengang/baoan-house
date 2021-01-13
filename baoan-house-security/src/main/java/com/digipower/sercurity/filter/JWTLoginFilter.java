package com.digipower.sercurity.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.digipower.common.entity.Result;
import com.digipower.sercurity.entity.JwtUserDetails;
import com.digipower.sercurity.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

/***
 * TODO  登录  ===> POST请求（ 账号:username=?, 密码：password=?）
 *
 * 登录会调用springSecurity的登录方法进行验证
 *<p>
 * ===== 登录成功
 * http状态status状态返回200，并且自定义响应状态code返回200，响应头存放token，key = token，value = jwt生成的token内容
 * ===== 登录失败
 * http状态status状态返回401，并且自定义响应状态code返回401，并提示对应的内容
 * ===== 权限不足
 *  http状态status状态返回403，并且自定义响应状态code返回403，并提示对应的内容
 * </p>
 * @author zzg
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {
	 /**
     * 获取授权管理, 创建JWTLoginFilter时获取
     */
    private AuthenticationManager authenticationManager;

  
    /**
     * 创建JWTLoginFilter,构造器，定义后端登陆接口-【/auth/login】，当调用该接口直接执行 attemptAuthentication 方法
     *
     * @param authenticationManager
     */
    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/auth/login");
    }


    /**
     * TODO 一旦调用登录接口 /auth/login，立即执行该方法
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
    	JwtUserDetails user = null;
    	ObjectMapper objectMapper = new ObjectMapper();
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), JwtUserDetails.class);
        } catch (IOException e) {
        	try{
	            response.setContentType("application/json;charset=UTF-8");
	  	      	response.getWriter().write(objectMapper.writeValueAsString(Result.error("401","没有传递对应的参数")));
        	} catch(Exception message){
        		
        	}
            return null;
        }
        // 调用springSecurity的 XiJiaUserDetailsServiceImpl 的 loadUserByUsername 方法进行登录认证，传递账号密码
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>()));
    }

    /**
     * TODO  一旦调用 springSecurity认证登录成功，立即执行该方法
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        // 生成jwt,并返回
    	JwtUserDetails userEntity = (JwtUserDetails) authResult.getPrincipal();
        String jwtToken = JwtTokenUtil.generateToken(userEntity);

        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        String str = objectMapper.writeValueAsString(Result.ok().setDatas("token", jwtToken));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }


    /**
     * TODO 一旦调用 springSecurity认证失败 ，立即执行该方法
     *
     * @param request
     * @param response
     * @param ex
     * @throws IOException
     * @throws ServletException
     */
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) {
    	 ObjectMapper objectMapper = new ObjectMapper();
    	try{
	    	if (ex instanceof UsernameNotFoundException || ex instanceof BadCredentialsException) {
	        	response.setContentType("application/json;charset=UTF-8");
	  	      	response.getWriter().write(objectMapper.writeValueAsString(Result.error("401","用户名或密码错误")));
	        } else if (ex instanceof InternalAuthenticationServiceException) {
	        	response.setContentType("application/json;charset=UTF-8");
	  	      	response.getWriter().write(objectMapper.writeValueAsString(Result.error("401","没有账号信息")));
	        } else if (ex instanceof DisabledException) {
	        	response.setContentType("application/json;charset=UTF-8");
	  	      	response.getWriter().write(objectMapper.writeValueAsString(Result.error("401","账户被禁用")));
	        } else {
	        	response.setContentType("application/json;charset=UTF-8");
	  	      	response.getWriter().write(objectMapper.writeValueAsString(Result.error("401","登录失败!")));
	        }
    	} catch(Exception e){
    		
    	}
    }
}
