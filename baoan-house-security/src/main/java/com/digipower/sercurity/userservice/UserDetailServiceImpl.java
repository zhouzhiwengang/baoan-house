package com.digipower.sercurity.userservice;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.digipower.entity.UcasAuthUserInfo;
import com.digipower.sercurity.entity.JwtUserDetails;
import com.digipower.service.UcasAuthUserInfoService;

@Component("userDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	private UcasAuthUserInfoService ucasAuthUserInfoService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		// 根据用户名去查找用户信息
		QueryWrapper<UcasAuthUserInfo> queryWrapper = new QueryWrapper<UcasAuthUserInfo>();
		queryWrapper.eq("user_pin", username);
		List<UcasAuthUserInfo> list = ucasAuthUserInfoService.list(queryWrapper);

		if (CollectionUtils.isEmpty(list)) {
			throw new UsernameNotFoundException(String.format("Not user Found with '%s'", username));
		}
		UcasAuthUserInfo customer = list.get(0);
		return new JwtUserDetails(customer.getUserName(), customer.getPassword(), customer.getUserPin(), getGrantedAuthority());
    
	}

	/**
	 * 用户登入成功默认所有角色权限
	 * @return
	 */
	private List<GrantedAuthority> getGrantedAuthority() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("all"));
		return authorities;
	}

}
