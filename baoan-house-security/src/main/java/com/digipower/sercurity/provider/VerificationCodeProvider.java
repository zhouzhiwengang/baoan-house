package com.digipower.sercurity.provider;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.digipower.entity.SysVerificationCode;
import com.digipower.sercurity.token.VerificationCodeAuthenticationToken;
import com.digipower.sercurity.userservice.UserDetailServiceImpl;
import com.digipower.service.SysVerificationCodeService;

@Component
public class VerificationCodeProvider implements AuthenticationProvider{
	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	@Autowired
	private SysVerificationCodeService sysVerificationCodeService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		VerificationCodeAuthenticationToken token = (VerificationCodeAuthenticationToken) authentication;
		String username = (String) token.getPrincipal();// 返回用户名;
		String password = (String) token.getCredentials();// 返回密码
		String code = token.getCode(); // 返回验证码
		
		// 验证码校验
		this.checkCode(code);
		// 用户验证
		UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(username);
		// 密码验证
		this.checkPassword(userDetails, password);
		
		 // 此时鉴权成功后，应当重新 new 一个拥有鉴权的 authenticationResult 返回
		VerificationCodeAuthenticationToken authenticationResult = new VerificationCodeAuthenticationToken(userDetails.getAuthorities(), username, password, code);
		authenticationResult.setDetails(token.getDetails());
		return authenticationResult;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return (VerificationCodeAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
	private void checkCode(String code){
		QueryWrapper<SysVerificationCode> queryWrapper = new QueryWrapper<SysVerificationCode>();
		queryWrapper.eq("code", code);
		SysVerificationCode object = sysVerificationCodeService.getOne(queryWrapper);
		if(object == null){
			 throw new BadCredentialsException("验证码错误");
		}
		// 验证码验证成功,数据库移除对应指定验证码记录
		sysVerificationCodeService.remove(queryWrapper);
	}
	
	private void checkPassword(UserDetails userDetails, String password){
		if(!String.valueOf(userDetails.getPassword()).equalsIgnoreCase(password)){
			throw new BadCredentialsException("密码错误");
		}
	}

}
