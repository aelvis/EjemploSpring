package com.ejemplo.spring.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ejemplo.spring.web.dao.IEstadoDao;
import com.ejemplo.spring.web.models.Estado;

@Service
public class EstadoService implements IEstadoService{

	@Autowired
	private IEstadoDao estadoDao;
	
	@Override
	public List<Estado> listarEstados() {
		return estadoDao.findAll();
	}

	@Override
	public Page<Estado> listarEstado(Pageable pageable) {
		return estadoDao.findAll(pageable);
	}

	@Override
	public Page<Estado> findByNombreLike(String nombre, Pageable pageable) {
		return estadoDao.findByNombreLike(nombre, pageable);
	}

	@Override
	public Estado BuscarPorId(Integer id) {
		return estadoDao.findById(id).orElse(null);
	}

	@Override
	public Estado guardar(Estado estado) {
		return estadoDao.save(estado);
	}

	@Override
	public void eliminarEstado(Integer id) {
		estadoDao.deleteById(id);
	}

}
