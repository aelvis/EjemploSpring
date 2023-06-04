package com.ejemplo.spring.web.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ejemplo.spring.web.models.ItemPedido;
import com.ejemplo.spring.web.models.Pedido;
import com.ejemplo.spring.web.models.Producto;
import com.ejemplo.spring.web.models.Usuario;
import com.ejemplo.spring.web.services.IUsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pedidos")
@SessionAttributes("pedido")
public class PedidosController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("/crear/{id}")
	public String crear(Model model, @PathVariable Integer id, RedirectAttributes flash) {
		
		Usuario usu = usuarioService.BuscarPorId(id);
		
		if(usu == null) {
			flash.addAttribute("error", "El cliente no existe en la base de Datos");
			return "redirect:/usuarios/index";
		}
		
		Pedido pedido = new Pedido();
		pedido.setUsuario(usu);
		
		model.addAttribute("pedido", pedido);
		
		return "pedidos/crear";
	}
	
	@GetMapping(value ="/productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
		return usuarioService.findByNombre(term);
	}
	@PostMapping("/crear-pedidos")
	public String guardar(@Valid Pedido pedido, 
			BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad, 
			RedirectAttributes flash,
			SessionStatus status) {
		
			
		
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Pedido");
			return "pedidos/crear";
		}

		if (itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "Crear Pedido");
			model.addAttribute("error", "Error: El Pedido NO puede no tener líneas!");
			return "pedidos/crear";
		}
		
		
		
		for (int i = 0; i < itemId.length; i++) {
			Producto producto = usuarioService.findProductoById(itemId[i]);
			ItemPedido linea = new ItemPedido();
			linea.setCantidad(cantidad[i]);
			linea.setProducto(producto);
			linea.setPedido(pedido);
			pedido.addItemPedido(linea);
			

			log.info("ID: " + itemId[i].toString() + ", cantidad: " + cantidad[i].toString());
		} 
		usuarioService.savePedido(pedido);
		status.setComplete();

		flash.addFlashAttribute("success", "Factura creada con éxito!");

		return "redirect:/usuarios/ver/" + pedido.getUsuario().getId();
	}
}
