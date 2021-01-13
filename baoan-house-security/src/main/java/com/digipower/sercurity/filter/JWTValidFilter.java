package com.digipower.sercurity.filter;

import java.io.IOException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.digipower.common.entity.Result;
import com.digipower.sercurity.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;

public class JWTValidFilter extends BasicAuthenticationFilter {
	/**
     * SecurityConfig 配置中创建该类实例
     */
    public JWTValidFilter(AuthenticationManager authenticationManager) {
        // 获取授权管理
        super(authenticationManager);
    }


    /**
     * 拦截请求
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    	ObjectMapper objectMapper = new ObjectMapper();
    	// 获取token, 没有token直接放行
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token) || "null".equals(token)) {
            super.doFilterInternal(request, response, chain);
            return;
        }
        // 有token进行权限验证
        String username = null;
        try {
            //  获取账号
            username = JwtTokenUtil.getUsername(token);
        } catch (ExpiredJwtException ex) {
        	response.setContentType("application/json;charset=UTF-8");
  	      	response.getWriter().write(objectMapper.writeValueAsString(Result.error("10000","登录过期")));
            return;
        } catch (Exception e) {
        	response.setContentType("application/json;charset=UTF-8");
  	      	response.getWriter().write(objectMapper.writeValueAsString(Result.error("10000","JWT解析错误")));
            return;
        }
        //  添加账户的权限信息，和账号是否为空，然后保存到Security的Authentication授权管理器中
        if (StringUtils.isNotBlank(username)) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, new ArrayList<SimpleGrantedAuthority>()));
        }
        super.doFilterInternal(request, response, chain);
    }
}
