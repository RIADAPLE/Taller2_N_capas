package com.uca.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uca.spring.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Integer>{
	Usuario findByUsernameAndPassword(String usuario, String contra);
}
