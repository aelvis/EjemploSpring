package com.ejemplo.spring.web.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ejemplo.spring.web.models.Rol;

public interface IRolService {

	public List<Rol> listarRol();
	
	public Page<Rol> listarRol(Pageable pageable);
	
	public Page<Rol> findByNombreLike(String nombre, Pageable pageable);
	
	public Rol BuscarPorId(Integer id);
	
	public Rol guardar(Rol rol);
	
	void eliminarRol(Integer id);
}
