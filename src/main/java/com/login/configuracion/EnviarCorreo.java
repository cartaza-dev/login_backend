package com.login.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.login.modelo.Usuario;

@Component
public class EnviarCorreo {

	@Autowired
	private JavaMailSender mail;

	@Autowired
	private JwtUtils jwtUtils;

	public ResponseEntity<?> enviarCorreoValidacion(String mail_received, Usuario usuario, String urlBase) {

		SimpleMailMessage email = new SimpleMailMessage();

		// A quien envìo el mail.
		email.setTo(mail_received);

		// Desde donde envio el mail
		email.setFrom("carlaartaza@outlook.com");

		// Tittulo del correo
		email.setSubject("Validacion de correo");

		//Genero el token

		String token = this.jwtUtils.generateToken(usuario);

		// Crear el enlace con el token y la URL base del frontend
		String enlace = urlBase + "/validarCuenta?token=" + token;

		// Incluir el enlace en el cuerpo del mensaje
		String cuerpoMensaje = "Haz clic en el siguiente enlace para validar tu cuenta: " + enlace;
		
		
		email.setText(cuerpoMensaje);

		// Enviamos el correo
		mail.send(email);

		// Respondemos el estado de la peticiòn http
		return new ResponseEntity<>(true, HttpStatus.OK);

	}

}
