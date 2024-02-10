package com.login.servicio;

import java.util.Set;

import com.login.modelo.Usuario;
import com.login.modelo.UsuarioRol;


public interface UsuarioServicio {
	
	public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles )throws Exception;
	
	public Usuario obtenerUsuario(String username);
	
	public void eliminarUsuario(Long id);

}
