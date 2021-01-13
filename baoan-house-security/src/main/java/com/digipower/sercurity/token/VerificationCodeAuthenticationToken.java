package com.digipower.sercurity.token;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class VerificationCodeAuthenticationToken extends AbstractAuthenticationToken {
	
	private final Object principal;
	private Object credentials;
	private String code;
	
	public VerificationCodeAuthenticationToken(Object principal,
			Object credentials, String code) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		this.code = code;
		setAuthenticated(false);
	}
	
	

	public VerificationCodeAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal,
			Object credentials, String code) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		this.code = code;
		super.setAuthenticated(true); 
	}



	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return this.credentials;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return this.principal;
	}


	public String getCode() {
		return this.code;
	}
	
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
 
        super.setAuthenticated(false);
    }



	@Override
	public void eraseCredentials() {
		// TODO Auto-generated method stub
		super.eraseCredentials();
		credentials = null;
	}
	
	


	
	
	
	


	

}
