package com.ejemplo.spring.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ejemplo.spring.web.models.Pedido;

public interface IPedidoDao extends JpaRepository<Pedido, Integer>{

}
