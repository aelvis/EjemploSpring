package com.ejemplo.spring.web.Security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ejemplo.spring.web.models.Rol;
import com.ejemplo.spring.web.models.Usuario;
import com.ejemplo.spring.web.services.IUsuarioService;

import jakarta.transaction.Transactional;

@Component
public class FormLogin implements UserDetailsService{

	private final IUsuarioService usuarioService;
	
	public FormLogin(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println(username);
		
		Usuario usua = usuarioService.buscarPorCorreo(username);
		
		if(usua == null) {
			throw new UsernameNotFoundException("El usuario no existe");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(Rol ro: usua.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(ro.getNombre()));
		}
		
		if(authorities.isEmpty()) {
			throw new UsernameNotFoundException("El usuario no roles asignados");
		}
		
		System.out.println(usua.getCorreo());
		System.out.println(usua.getPassword());
		
		return new User(usua.getCorreo(), usua.getPassword(), authorities);
	}

}
