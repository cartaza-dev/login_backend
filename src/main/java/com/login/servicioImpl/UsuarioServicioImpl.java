package com.login.servicioImpl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.login.modelo.Usuario;
import com.login.modelo.UsuarioRol;
import com.login.repositorio.RolRepositorio;
import com.login.repositorio.UsuarioRepositorio;
import com.login.servicio.UsuarioServicio;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private RolRepositorio rolRepositorio;

	@Override
	public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception {
	
		
		Usuario usuarioLocal = usuarioRepositorio.findByUsername(usuario.getUsername());
		
		if(usuarioLocal != null) {
			
			throw new Exception("El usuario ya existe");
			
		} else {
			for(UsuarioRol usuarioRol : usuarioRoles) {
				rolRepositorio.save(usuarioRol.getRol());
			}
			usuario.getUsuarioRoles().addAll(usuarioRoles);
			usuarioLocal = usuarioRepositorio.save(usuario);
		}
		
		return usuarioLocal;
	}

	@Override
	public Usuario obtenerUsuario(String username) {
		
		return usuarioRepositorio.findByUsername(username);
	}

	@Override
	public void eliminarUsuario(Long id) {
		 usuarioRepositorio.deleteById(id);
	
	}
	
	
	

}
