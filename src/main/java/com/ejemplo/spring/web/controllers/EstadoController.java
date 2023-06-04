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

import com.ejemplo.spring.web.models.Estado;
import com.ejemplo.spring.web.services.IEstadoService;

@Controller
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private IEstadoService estadoService;
	
	@GetMapping({"/index","/index/"})
	public String indexPaginacion(Model model, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false,defaultValue = "") String enviar) {
		Estado estado = new Estado();
		
		
		if(enviar== "") {
			Page<Estado> estados = estadoService.listarEstado(PageRequest.of(page, 4,Sort.by("id").descending()));
			model.addAttribute("usuarios", estados);
		}else {
			Page<Estado> estados = estadoService.findByNombreLike("%"+enviar+"%",PageRequest.of(page, 4,Sort.by("id").descending()));
			model.addAttribute("usuarios", estados);
		}
		model.addAttribute("paginacion", "/estados/index/");
		model.addAttribute("envioBuscar", enviar);
		model.addAttribute("estado", estado);
		return "estados/index";
	}	
	
	
	
	@PostMapping("/crear")
	public String crear(@Validated Estado es, BindingResult result) {	
		if (result.hasErrors()){
	        System.out.println("error");
	        List<ObjectError> allErrors = result.getAllErrors();
	        for (ObjectError o : allErrors){
	            System.out.println("error -->  " + o.getDefaultMessage());
	        }
	    }
		Page<Estado> estado = estadoService.findByNombreLike("%"+es.getNombre()+"%", PageRequest.of(0, 4,Sort.by("id").descending()));

		if(estado.isEmpty()) {
			estadoService.guardar(es);
		}
		
		return "redirect:/estados/index";
	}	
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable("id") Integer id) {	
		Estado estado = estadoService.BuscarPorId(id);
		
		if(estado == null) {
			return "redirect:/estado/index";
		}
		
		try {
			estadoService.eliminarEstado(id);
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    }

		return "redirect:/estados/index";
	}	
	
}
