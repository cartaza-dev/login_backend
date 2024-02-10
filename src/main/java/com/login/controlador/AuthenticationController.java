package com.login.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.login.configuracion.JwtUtils;
import com.login.modelo.JwtRequest;
import com.login.modelo.JwtResponse;
import com.login.modelo.Usuario;
import com.login.repositorio.UsuarioRepositorio;
import com.login.servicioImpl.UserDetailsServicelmpl;

import java.security.Principal;

@RestController
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServicelmpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @PostMapping("/generate-token")
    public ResponseEntity<?> generarToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try{
        	//Buscamos el usuario por su username.
        	Usuario usuario = usuarioRepositorio.findByUsername(jwtRequest.getUsername());
        	
        	//Validamos si el usuario ha validado su cuenta vìa mail
        	if( usuario.getEnabled() == false) {
        		 throw new Exception("Debes validar tu cuenta");
        	}else if(usuario.getEnabled() ==  true) { //Si su estado es TRUE le permite autenticarse
        		  autenticar(jwtRequest.getUsername(),jwtRequest.getPassword());
        	}
        	
        }catch (Exception exception){
            exception.printStackTrace();
            throw new Exception("Usuario no encontrado");
        }

        UserDetails userDetails =  this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void autenticar(String username,String password) throws Exception {
        try{
        
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (DisabledException exception){
            throw  new Exception("USUARIO DESHABILITADO " + exception.getMessage());
        }catch (BadCredentialsException e){
            throw  new Exception("Credenciales invalidas " + e.getMessage());
        }
    }

    @GetMapping("/actual-usuario")
    public Usuario obtenerUsuarioActual(Principal principal){
        return (Usuario) this.userDetailsService.loadUserByUsername(principal.getName());
    }
}