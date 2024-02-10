package com.login.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.login.modelo.Rol;



@Repository
public interface RolRepositorio extends JpaRepository<Rol, Long>{

}
