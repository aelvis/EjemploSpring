package com.ejemplo.spring.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ejemplo.spring.web.models.Estado;
import com.ejemplo.spring.web.models.Rol;
import com.ejemplo.spring.web.models.Usuario;
import com.ejemplo.spring.web.services.IEstadoService;
import com.ejemplo.spring.web.services.IRolService;
import com.ejemplo.spring.web.services.IUsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	// @InitBinder
	// public void miBinder(WebDataBinder binder) {
	// StringTrimmerEditor recortarEspacios = new StringTrimmerEditor(true);

	// binder.registerCustomEditor(Usuario.class, recortarEspacios);
	// }

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IEstadoService estadoService;

	@Autowired
	private IRolService rolService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/index")
	public String index(Model model) {

		List<Usuario> usua = usuarioService.listarUsuario();
		model.addAttribute("usuarios", usua);

		List<Estado> esta = estadoService.listarEstados();
		List<Rol> rol = rolService.listarRol();

		Usuario usuario = new Usuario();

		model.addAttribute("estados", esta);
		model.addAttribute("rols", rol);
		model.addAttribute("usuario", usuario);

		return "usuarios/index";
	}

	@GetMapping({ "/index/paginacion", "/index/paginacion/" })
	public String indexPaginacion(Model model, @RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "") String enviar) {

		List<Estado> esta = estadoService.listarEstados();
		List<Rol> rol = rolService.listarRol();

		Usuario usuario = new Usuario();

		if (enviar == "") {
			Page<Usuario> usua = usuarioService.listarUsuario(PageRequest.of(page, 3, Sort.by("id").descending()));
			model.addAttribute("usuarios", usua);
		} else {
			Page<Usuario> usu = usuarioService.findByCorreoLike("%" + enviar + "%",
					PageRequest.of(page, 3, Sort.by("id").descending()));
			model.addAttribute("usuarios", usu);
		}
		model.addAttribute("paginacion", "/usuarios/index/paginacion/");
		model.addAttribute("estados", esta);
		model.addAttribute("envioBuscar", enviar);
		model.addAttribute("rols", rol);
		model.addAttribute("usuario", usuario);
		return "usuarios/indexP";
	}

	@PostMapping("/crear")
	public String crear(@Valid Usuario usu, BindingResult result, Model model) {
		if (result.hasErrors()) {

			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});
			model.addAttribute("errores", errores);
			System.out.println(errores);
			return "usuarios/index";
		}
		if (usu.getId() == null) {
			String encodedPassword = passwordEncoder.encode(usu.getPassword());
			usu.setPassword(encodedPassword);
		} else {
			if (usu.getPassword().isEmpty()) {
				Usuario usua = usuarioService.BuscarPorId(usu.getId());
				usu.setPassword(usua.getPassword());
			} else {
				String encodedPassword = passwordEncoder.encode(usu.getPassword());
				usu.setPassword(encodedPassword);
			}
		}

		usuarioService.guardar(usu);

		return "redirect:/usuarios/index";
	}

	@PostMapping("/actualizar")
	public String update(Usuario usu, Model model) {

		if (usu.getId() == null) {
			String encodedPassword = passwordEncoder.encode(usu.getPassword());
			usu.setPassword(encodedPassword);
		} else {
			if (usu.getPassword().isEmpty()) {
				Usuario usua = usuarioService.BuscarPorId(usu.getId());
				usu.setPassword(usua.getPassword());
			} else {
				String encodedPassword = passwordEncoder.encode(usu.getPassword());
				usu.setPassword(encodedPassword);
			}
		}

		usuarioService.guardar(usu);

		return "redirect:/usuarios/index";
	}

	@GetMapping("/index/{id}")
	public String index(Model model, @PathVariable("id") Integer id) {

		Usuario usu = usuarioService.BuscarPorId(id);

		if (usu == null) {
			return "redirect:/usuarios/index";
		}

		usu.setPassword("");

		List<Estado> esta = estadoService.listarEstados();
		List<Rol> rol = rolService.listarRol();

		model.addAttribute("usuario", usu);
		model.addAttribute("estados", esta);
		model.addAttribute("rols", rol);

		return "usuarios/usuario-id";
	}

	@GetMapping("/ver/{id}")
	public String ver(Model model, @PathVariable("id") Integer id) {

		Usuario usu = usuarioService.BuscarPorId(id);

		if (usu == null) {
			return "redirect:/usuarios/index";
		}

		List<Estado> esta = estadoService.listarEstados();
		List<Rol> rol = rolService.listarRol();

		model.addAttribute("usuario", usu);
		model.addAttribute("estados", esta);
		model.addAttribute("rols", rol);

		return "usuarios/ver";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable("id") Integer id) {
		Usuario usua = usuarioService.BuscarPorId(id);

		if (usua == null) {
			return "redirect:/usuarios/index";
		}

		usuarioService.eliminarUsuario(id);

		return "redirect:/usuarios/index";
	}

}
