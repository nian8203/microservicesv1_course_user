package com.springcloud.msvc.usuarios.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springcloud.msvc.usuarios.entity.Usuario;
import com.springcloud.msvc.usuarios.service.IUsuarioService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UsuarioController {

	@Autowired
	private IUsuarioService service;
	
	@GetMapping("/usuario")
	public List<Usuario> listar(){
		return service.listar();
	}
	
	@GetMapping("/usuario/{id}")
	public ResponseEntity<?> buscarPorId(@RequestBody Usuario usuario, @PathVariable Long id){
		Usuario user = service.buscarPorId(id);
		
		if (user != null) {
			return ResponseEntity.status(HttpStatus.OK).body(user);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
	}
	
	@PostMapping("/usuario")
	public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result){

//		if (result.hasErrors()){
//			result.getFieldErrors().forEach(err -> {
//				errores.put(err.getField(), "El campo '"+ err.getField() + "' " + err.getDefaultMessage());
//			});
//			return ResponseEntity.badRequest().body(errores);
//		}

		if (result.hasErrors()) {
			return validar(result);
		}

		if (!usuario.getEmail().isEmpty() && service.buscarPorCorreo(usuario.getEmail()).isPresent()){
			return  ResponseEntity.badRequest().body(Collections.singletonMap("menasaje","Este correo ya se encuentra registrado!"));
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(usuario));
	}

	@PutMapping("/usuario/{id}")
	public ResponseEntity<?> actualizar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id){
		if (result.hasErrors()) {
			return validar(result);
		}

		Usuario actualizado = null;
		Usuario actual = service.buscarPorId(id);
		
		if (actual == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		if (!usuario.getEmail().isEmpty() && !usuario.getEmail().equalsIgnoreCase(actual.getEmail()) && service.buscarPorCorreo(usuario.getEmail()).isPresent()){
			return  ResponseEntity.badRequest().body(Collections.singletonMap("menasaje","Este correo ya se encuentra registrado!"));
		}

		actual.setEmail(usuario.getEmail());
		actual.setNombre(usuario.getNombre());
		actual.setPassword(usuario.getPassword());
		
		actualizado = service.crear(actual);
		return ResponseEntity.status(HttpStatus.CREATED).body(actualizado);
	}

	@DeleteMapping("/usuario/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		
		Usuario user = service.buscarPorId(id);
		
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		service.eliminar(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
	}

	private ResponseEntity<Map<String, Object>> validar(BindingResult result) {
		Map<String, Object> errores = new HashMap<>();
		List<String> errors = result.getFieldErrors().stream()
				.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
				.collect(Collectors.toList());

		errores.put("errors", errors);
		return new ResponseEntity<Map<String, Object>>(errores, HttpStatus.BAD_REQUEST);
	}
	
	
	
}
