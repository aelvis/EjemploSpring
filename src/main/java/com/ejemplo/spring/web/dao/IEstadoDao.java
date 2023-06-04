package com.ejemplo.spring.web.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ejemplo.spring.web.models.Estado;

public interface IEstadoDao extends JpaRepository<Estado, Integer>{
		
	public List<Estado> findByNombreLike(String nombre);
	
	public Page<Estado> findByNombreLike(String nombre, Pageable pageable);
}
