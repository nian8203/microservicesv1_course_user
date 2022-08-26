package com.springcloud.msvc.cursos.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.springcloud.msvc.cursos.entity.Curso;
import com.springcloud.msvc.cursos.service.ICursoService;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CursoController {

	@Autowired
	private ICursoService service;
	
	@GetMapping("/curso")
	public ResponseEntity<List<Curso>> listar(){
		return ResponseEntity.ok(service.listar());
	}

	@GetMapping("/curso/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id){
		Curso cur = service.buscarPorId(id);

		if (cur == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		return ResponseEntity.status(HttpStatus.OK).body(cur);
	}
	
	@PostMapping("/curso")
	public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result){
		if (result.hasErrors()) {
			return validar(result);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(curso));
	}

	@PutMapping("/curso/{id}")
	public ResponseEntity<?> actualizar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
		if (result.hasErrors()) {
			return validar(result);
		}

		Curso actual = service.buscarPorId(id);
		Curso actualizado = null;

		if (actual == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		actual.setNombre(curso.getNombre());
		actualizado = service.crear(actual);

		return ResponseEntity.status(HttpStatus.CREATED).body(actualizado);

	}

	@DeleteMapping("/curso/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		Curso c = service.buscarPorId(id);

		if (c == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

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
