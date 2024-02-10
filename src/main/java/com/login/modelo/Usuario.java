package com.login.modelo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

	/*
	 * Este campo se utiliza para identificar la versión de la clase durante la
	 * deserialización y ayuda a garantizar la compatibilidad entre versiones. No es
	 * obligatorio agregarlo.
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long usuarioId;

	private String name;

	private String username;

	private String password;

	private String email;

	private Boolean enabled = false;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "usuario")
	@JsonIgnore
	private Set<UsuarioRol> usuarioRoles = new HashSet<>();

	public Usuario() {
		super();
	}

	public Usuario(Long id) {
		super();
		this.usuarioId = id;
	}

	public Usuario(Long id, String name, String username, String password, String email, Boolean enabled) {
		super();
		this.usuarioId = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
	}

	public Long getId() {
		return usuarioId;
	}

	public void setId(Long id) {
		this.usuarioId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<UsuarioRol> getUsuarioRoles() {
		return usuarioRoles;
	}

	public void setUsuarioRoles(Set<UsuarioRol> usuarioRoles) {
		this.usuarioRoles = usuarioRoles;
	}

	
	
	/*
	 * En este metodo obtenemos los nombres de los roles que tenga el usuario y los
	 * guardamos en el conjunto "Autoridades"
	 */
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Set<Authority> autoridades = new HashSet<>();

		this.usuarioRoles.forEach(usuarioRol -> {

			autoridades.add(new Authority(usuarioRol.getRol().getName()));

		});
		return autoridades;
	}

	/* Desactivamos la expiraciòn de la cuenta por ahora */
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	/* Desactivamos el bloqueo de la cuenta por ahora */
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	/* Desactivamos la expiraciòn de las credenciales de la cuenta por ahora */
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	  public boolean isEnabled() {
        return enabled;
    }

}
