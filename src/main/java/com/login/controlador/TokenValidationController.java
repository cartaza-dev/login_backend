package com.login.controlador;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.login.configuracion.JwtUtils;
import com.login.modelo.Usuario;
import com.login.repositorio.UsuarioRepositorio;
import com.login.servicioImpl.UserDetailsServicelmpl;

import io.jsonwebtoken.ExpiredJwtException;


@RestController
@CrossOrigin("*")
public class TokenValidationController {

 
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsServicelmpl userDetailsService;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	
	//Validamos el TOKEN, que fuè enviado al correo electronico del usuario(al registrarse) mediante una URL del frontend. 
	//A la cual si accedes envia una consulta a este endpoint con el token ya extraido de la URL,  para validarlo.
	@PostMapping("/validar-cuenta")
	public ResponseEntity<String> validarToken(@RequestBody Map<String, String> payload) {
		
		
	    try {//Intentamos hacer la validaciòn.
	        String token = payload.get("token");

	        //Obtenemos el usuario de ese token.
	        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtils.extractUsername(token));
	        
	        //Validamos el token, si el mismo es correcto, procedemos a cambiar el estado del usuario a ACTIVO. (Enabled = true)
	        //Lo que le permitirà iniciar sesiòn, de lo contrario no podrà hacerlo.
	        if (jwtUtils.validateToken(token, userDetails)) {
	        	
	        	//Buscamos el usuario
	        	 Usuario usuario = usuarioRepositorio.findByUsername(userDetails.getUsername());
	        	 
	        	 //Activamos su cuenta.
	        	 usuario.setEnabled(true);
	        	 
	        	 //Guardamos los cambios en la DB.
	        	 usuarioRepositorio.save(usuario); 
	        	 
	        	 //Devolvemos la respuesta ok.
	            return ResponseEntity.ok("Token válido");

	        } else { //Si el token es erroneo, devolvemos el siguiente mensaje.
	            return ResponseEntity.badRequest().body("Token no válido");  
	        }
	        //Si el token ha expirado, lanzarà la siguiente excepciòn.
	    } catch (ExpiredJwtException ex) {        
	        return ResponseEntity.badRequest().body("El token ha expirado");
	        
	    }  catch (Exception e) {  //Si el token es erroneo, devolvemos el error.
	        e.printStackTrace();
	        return ResponseEntity.badRequest().body("Error al validar el token: " + e.getMessage());
	    }
	}

	
	/* ESTE NO CORRESPONDE, PORQUE  NO ESTOY ENVIANDO DESDE EL FRONT  EL TOKEN EN LA CABECERA, SINO EN EL BODY.
	 *  @PostMapping("/validar-cuenta")
	    public ResponseEntity<String> validarToken(@RequestParam("token") String token) {
	        try {
	            // Validar el token
	            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtils.extractUsername(token));

	            if (jwtUtils.validateToken(token, userDetails)) {
	                return ResponseEntity.ok("Token válido");
	                
	                
	            } else {
	                return ResponseEntity.badRequest().body("Token no válido");
	            }
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("Error al validar el token: " + e.getMessage());
	        }
	    }*/
}