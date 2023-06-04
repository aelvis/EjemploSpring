package com.ejemplo.spring.web.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name="pedidos")
@Setter
@Getter
//@AllArgsConstructor
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty(message="no puede estár vacío")
	private String descripcion;
	
	@NotEmpty(message="no puede estár vacío")
	private String observacion;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;
	
	//@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	//@JoinColumn(name="pedido_id")
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemPedido> items;
	
	@PrePersist
	public void prePersist() {
		fecha = new Date();
	}	
	
	public Pedido() {
		this.items = new ArrayList<ItemPedido>();
	}
	
	public void addItemPedido(ItemPedido item) {
		items.add(item);
	}
	
	public Double getTotal() {
		Double total = 0.0;

		int size = items.size();

		for (int i = 0; i < size; i++) {
			total += items.get(i).calcularImporte();
		}
		return total;
	}



	
	
}
