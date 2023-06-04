package com.ejemplo.spring.web.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ejemplo.spring.web.models.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, Integer>{

	public Usuario findByCorreo(String correo);
	
	public List<Usuario> findByCorreoLike(String correo);
	
	public Page<Usuario> findByCorreoLike(String title, Pageable pageable);
	
}
