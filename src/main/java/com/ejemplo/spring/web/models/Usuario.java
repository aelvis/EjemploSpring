package com.ejemplo.spring.web.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Table(name="usuarios")
@Entity
@Setter
@Getter
//@AllArgsConstructor

public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NonNull
	@NotEmpty(message="no puede estár vacío")
	@Size(min=2, max=30)
	private String correo;
	
	@NotEmpty(message="no puede estár vacío")
	private String password;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@ManyToMany(cascade = {
	          CascadeType.PERSIST,
	          CascadeType.MERGE
	      })
	@JoinTable(name="usuario_rol", joinColumns = { @JoinColumn(name="usuario_id") }, inverseJoinColumns = {@JoinColumn(name="rol_id")})
	private Set<Rol> roles;
	
	@OneToOne(cascade = {
	          CascadeType.PERSIST,
	          CascadeType.MERGE
	      },fetch = FetchType.EAGER)
	@JoinColumn(name="estado_id")
	private Estado estado;

	@OneToMany(mappedBy="usuario", cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Pedido> pedidos;
	
	@PrePersist
	public void prePersist() {
		fecha = new Date();
	}
	public Usuario() {
		pedidos = new ArrayList<Pedido>();
	}
	public void addPedidos(Pedido pedido) {
		pedidos.add(pedido);
	}
	
	 public void addTag(Rol rol) {
		    this.roles.add(rol);
		    rol.getUsuario().add(this);
		  }
	public void removeRol(long rolId) {
	    Rol rol = this.roles.stream().filter(t -> t.getId() == rolId).findFirst().orElse(null);
	    if (rol != null) {
	      this.roles.remove(rol);
	      rol.getUsuario().remove(this);
	    }
	}
	
}
