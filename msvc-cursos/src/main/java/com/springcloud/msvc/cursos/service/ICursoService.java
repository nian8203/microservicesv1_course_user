package com.springcloud.msvc.cursos.service;

import java.util.List;

import com.springcloud.msvc.cursos.entity.Curso;

public interface ICursoService {

	List<Curso> listar();
	Curso buscarPorId(Long id);
	Curso crear(Curso curso);
	void eliminar(Long id);
}
