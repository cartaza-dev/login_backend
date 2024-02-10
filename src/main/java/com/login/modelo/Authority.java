package com.login.modelo;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {


	private static final long serialVersionUID = 1L;
	
	private String authority;
	
	
	
	public Authority(String authority) {
		this.authority = authority;
	}


   /* Este metodo lo implementa desde GrantedAuthority */
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return null;
	}

}
