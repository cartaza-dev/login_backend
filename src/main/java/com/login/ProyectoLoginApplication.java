package com.login;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.login.modelo.Rol;
import com.login.modelo.Usuario;
import com.login.modelo.UsuarioRol;
import com.login.servicio.UsuarioServicio;



@SpringBootApplication
public class ProyectoLoginApplication {
	@Autowired
	private UsuarioServicio usuarioServicio;
	
	

	public static void main(String[] args) {
		SpringApplication.run(ProyectoLoginApplication.class, args);
	}

	public void run(String... args) throws Exception {
		/*
		Usuario usuario = new Usuario();
		usuario.setName("Carla");
		usuario.setPassword("12345");
		usuario.setUsername("Carla");
		usuario.setEnabled(true);
		usuario.setEmail("carlaartaza@outlook.com");
		
		Rol rol = new Rol();
		rol.setName("ADMIN");
		
		Set<UsuarioRol> usuarioRoles = new HashSet<>();
		UsuarioRol usuarioRol = new UsuarioRol();
		usuarioRol.setRol(rol);
		usuarioRol.setUsuario(usuario);
		usuarioRoles.add(usuarioRol);
		
		Usuario usuarioGuardado = usuarioServicio.guardarUsuario(usuario, usuarioRoles);
		System.out.println(usuarioGuardado.getUsername());
		*/
	}
}
