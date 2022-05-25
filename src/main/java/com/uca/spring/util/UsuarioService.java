package com.uca.spring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uca.spring.model.Usuario;
import com.uca.spring.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	public UsuarioRepository repo;

	public Usuario login(String username, String password){
		Usuario user = repo.findByUsernameAndPassword(username, password);
		return user;
	}

}
