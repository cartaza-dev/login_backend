package com.login.servicioImpl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.login.modelo.Usuario;
import com.login.repositorio.UsuarioRepositorio;


/* Esta clase sirve para recuperar un nombre de usuario, 
 * una contraseña y otros atributos para autenticarse con 
 * un nombre de usuario y una contraseña.
 */

@Service
public class UserDetailsServicelmpl implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	System.out.println("Intentando cargar usuario: " + username);
    	
    	
        Usuario usuario = this.usuarioRepository.findByUsername(username);
        if(usuario == null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return usuario;
    }

}