package com.ejemplo.spring.web.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ejemplo.spring.web.models.Pedido;
import com.ejemplo.spring.web.models.Producto;
import com.ejemplo.spring.web.models.Usuario;

public interface IUsuarioService {

	Usuario buscarPorCorreo(String correo);
	
	List<Usuario> listarUsuario();
	
	List<Usuario> buscarPorCorreoLike(String correo);
	
	Page<Usuario> listarUsuario(Pageable pageable);
	
	Page<Usuario> findByCorreoLike(String title, Pageable pageable);
	
	Usuario BuscarPorId(Integer id);
	
	Usuario guardar(Usuario usuario);
	
	void eliminarUsuario(Integer id);
	
	List<Producto> findByNombre(String nombre);
	
	Producto findProductoById(Long id);
	
	void savePedido(Pedido pedido);
}
