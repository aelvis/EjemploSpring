package com.ejemplo.spring.web.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ejemplo.spring.web.models.Estado;

public interface IEstadoService {

	public List<Estado> listarEstados();
	
	public Page<Estado> listarEstado(Pageable pageable);
	
	public Page<Estado> findByNombreLike(String nombre, Pageable pageable);
	
	public Estado BuscarPorId(Integer id);
	
	public Estado guardar(Estado estado);
	
	void eliminarEstado(Integer id);	
	
}
