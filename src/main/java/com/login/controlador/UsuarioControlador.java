package com.login.controlador;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login.configuracion.EnviarCorreo;
import com.login.modelo.Rol;
import com.login.modelo.Usuario;
import com.login.modelo.UsuarioRol;
import com.login.servicio.UsuarioServicio;


@RestController
@CrossOrigin("*")
@RequestMapping("/usuarios")
public class UsuarioControlador {

	@Autowired
	private UsuarioServicio usuarioServicio;
	
	@Autowired
	private EnviarCorreo validarCuenta;
	
	

	/* Probando devolver string */
	@GetMapping
	public String usuarios() {
		return "Bienvenidos";
	}

	/* Probando devolver string */
	@GetMapping("home")
	public String hola() {
		return "Bienvenido a ecommerce";
	}

	/* RETORNA EL USUARIO GUARDADO.
	@PostMapping("/guardarUsuario")
	public Usuario guardarUsuario(@RequestBody Usuario usuario) throws Exception {

		Set<UsuarioRol> roles = new HashSet<>();

		Rol rol = new Rol();
		rol.setRolId(2L);
		rol.setName("USER");

		UsuarioRol usuarioRol = new UsuarioRol();

		usuarioRol.setUsuario(usuario);
		usuarioRol.setRol(rol);

		roles.add(usuarioRol);

		return usuarioServicio.guardarUsuario(usuario, roles);
		
	}*/
	

	@PostMapping("/guardarUsuario")
	public  void guardarUsuario(@RequestBody Usuario usuario) throws Exception {

		Set<UsuarioRol> roles = new HashSet<>();

		Rol rol = new Rol();
		rol.setRolId(2L);
		rol.setName("USER");

		UsuarioRol usuarioRol = new UsuarioRol();

		usuarioRol.setUsuario(usuario);
		usuarioRol.setRol(rol);

		roles.add(usuarioRol);
	
	    usuarioServicio.guardarUsuario(usuario, roles);
	    
	    String urlFront = "http://192.168.100.32:3000";
	    
	    validarCuenta.enviarCorreoValidacion(usuario.getEmail(), usuario, urlFront);
		
	}
		
	@GetMapping("/{username}")
	public Usuario obtenerUsuario(@PathVariable("username") String username) {
		return usuarioServicio.obtenerUsuario(username);
	}

	@DeleteMapping("/{usuarioId}")
	public void eliminarUsuario(@PathVariable("usuarioId") Long usuarioId) {
		usuarioServicio.eliminarUsuario(usuarioId);
	}

}
