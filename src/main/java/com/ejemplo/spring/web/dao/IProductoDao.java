package com.ejemplo.spring.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ejemplo.spring.web.models.Producto;

public interface IProductoDao extends JpaRepository<Producto, Long>{

	public List<Producto> findByNombre(String nombre);
	
}
