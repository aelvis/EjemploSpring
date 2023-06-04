package com.ejemplo.spring.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ejemplo.spring.web.dao.IPedidoDao;
import com.ejemplo.spring.web.dao.IProductoDao;
import com.ejemplo.spring.web.dao.IUsuarioDao;
import com.ejemplo.spring.web.models.Pedido;
import com.ejemplo.spring.web.models.Producto;
import com.ejemplo.spring.web.models.Usuario;

@Service
public class UsuarioService implements IUsuarioService{

	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Autowired
	private IProductoDao productoDao;
	
	@Autowired
	private IPedidoDao pedidoDao;
	
	
	
	@Override
	public Usuario buscarPorCorreo(String correo) {
		return usuarioDao.findByCorreo(correo);
	}

	@Override
	public List<Usuario> listarUsuario() {
		return usuarioDao.findAll(Sort.by("id").descending());
	}
	
	@Override
	public Page<Usuario> listarUsuario(Pageable pageable) {
		return usuarioDao.findAll(pageable);
	}
	
	@Override
	public Page<Usuario> findByCorreoLike(String title, Pageable pageable) {
		return usuarioDao.findByCorreoLike(title, pageable);
	}
	
	@Override
	public List<Usuario> buscarPorCorreoLike(String correo) {
		return usuarioDao.findByCorreoLike(correo);
	}
	@Override
	public Usuario BuscarPorId(Integer id) {
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	public Usuario guardar(Usuario usuario) {
		return usuarioDao.save(usuario);
	}

	@Override
	public void eliminarUsuario(Integer id) {
		usuarioDao.deleteById(id);
		
	}
	
	@Override
	public List<Producto> findByNombre(String nombre) {
		return productoDao.findByNombre(nombre);
	}

	@Override
	public Producto findProductoById(Long id) {
		return productoDao.findById(id).orElse(null);
	}

	@Override
	public void savePedido(Pedido pedido) {
		pedidoDao.save(pedido);
		
	}


}
