package com.ejemplo.spring.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ejemplo.spring.web.dao.IRolDao;
import com.ejemplo.spring.web.models.Rol;

@Service
public class RolService implements IRolService{

	@Autowired
	private IRolDao rolDao;
	
	@Override
	public List<Rol> listarRol() {
		return rolDao.findAll();
	}

	@Override
	public Page<Rol> listarRol(Pageable pageable) {
		return rolDao.findAll(pageable);
	}

	@Override
	public Page<Rol> findByNombreLike(String nombre, Pageable pageable) {
		return  rolDao.findByNombreLike(nombre, pageable);
	}

	@Override
	public Rol BuscarPorId(Integer id) {
		return rolDao.findById(id).orElse(null);
	}

	@Override
	public Rol guardar(Rol rol) {
		return rolDao.save(rol);
	}

	@Override
	public void eliminarRol(Integer id) {
		rolDao.deleteById(id);
	}

}
