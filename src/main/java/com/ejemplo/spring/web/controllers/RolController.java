package com.ejemplo.spring.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ejemplo.spring.web.models.Rol;
import com.ejemplo.spring.web.services.IRolService;

@Controller
@RequestMapping("/roles")
public class RolController {
	@Autowired
	private IRolService rolService;
	
	@GetMapping({"/index","/index/"})
	public String indexPaginacion(Model model, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false,defaultValue = "") String enviar) {
		Rol rol = new Rol();
		
		
		if(enviar== "") {
			Page<Rol> roles = rolService.listarRol(PageRequest.of(page, 4,Sort.by("id").descending()));
			model.addAttribute("usuarios", roles);
		}else {
			Page<Rol> roles = rolService.findByNombreLike("%"+enviar+"%",PageRequest.of(page, 4,Sort.by("id").descending()));
			model.addAttribute("usuarios", roles);
		}
		model.addAttribute("paginacion", "/roles/index/");
		model.addAttribute("envioBuscar", enviar);
		model.addAttribute("rol", rol);
		return "roles/index";
	}	
	
	
	
	@PostMapping("/crear")
	public String crear(@Validated Rol rolG, BindingResult result) {	
		if (result.hasErrors()){
	        System.out.println("error");
	        List<ObjectError> allErrors = result.getAllErrors();
	        for (ObjectError o : allErrors){
	            System.out.println("error -->  " + o.getDefaultMessage());
	        }
	    }
		Page<Rol> rol = rolService.findByNombreLike("%"+rolG.getNombre()+"%", PageRequest.of(0, 4,Sort.by("id").descending()));

		if(rol.isEmpty()) {
			rolService.guardar(rolG);
		}
		
		return "redirect:/roles/index";
	}	
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable("id") Integer id) {	
		Rol rol = rolService.BuscarPorId(id);
		
		if(rol == null) {
			return "redirect:/roles/index";
		}
		
		try {
			rolService.eliminarRol(id);
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    }

		return "redirect:/roles/index";
	}		
	
}
