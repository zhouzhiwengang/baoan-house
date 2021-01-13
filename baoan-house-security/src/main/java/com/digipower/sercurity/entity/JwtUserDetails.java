package com.digipower.sercurity.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class JwtUserDetails implements UserDetails {
	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	private String username;
	
	private String userPin;

    private String password;
 
	private String code;
    
    private String sid;

    private Collection<? extends GrantedAuthority> authorities;
    
    public String getUserPin() {
		return userPin;
	}

	public void setUserPin(String userPin) {
		this.userPin = userPin;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
	
	   
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public void setUserName(String username){
		this.username = username;
		
	}

	public JwtUserDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JwtUserDetails(String username, String password, String userPin, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.userPin = userPin;
        this.authorities = authorities;
    }
	
    public JwtUserDetails(String username, String userPin, String password, String code,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.username = username;
		this.userPin = userPin;
		this.password = password;
		this.code = code;
		this.authorities = authorities;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }
    
   

    @Override
    public String getUsername() {
        return username;
    }

    // 账户是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账户是否未被锁
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
