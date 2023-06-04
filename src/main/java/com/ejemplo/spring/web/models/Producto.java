package com.ejemplo.spring.web.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="productos")
@Setter
@Getter
public class Producto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="no puede estár vacío")
	private String nombre;
	
	@NotEmpty(message="no puede estár vacío")
	private Double precio;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@PrePersist
	public void prePersist() {
		fecha = new Date();
	}
	
}
