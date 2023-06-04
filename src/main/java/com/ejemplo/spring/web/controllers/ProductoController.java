package com.ejemplo.spring.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductoController {

	@GetMapping("/index")
	public String index() {
		return "productos/index";
	}
}
