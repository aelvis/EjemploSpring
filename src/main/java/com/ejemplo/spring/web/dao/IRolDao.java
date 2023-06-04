package com.ejemplo.spring.web.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ejemplo.spring.web.models.Rol;

public interface IRolDao extends JpaRepository<Rol, Integer>{
	
public List<Rol> findByNombreLike(String nombre);

public Page<Rol> findByNombreLike(String nombre, Pageable pageable);
}
