package com.springcloud.msvc.cursos.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springcloud.msvc.cursos.entity.Curso;
import com.springcloud.msvc.cursos.repository.ICursoRepository;
import com.springcloud.msvc.cursos.service.ICursoService;

@Service
public class CursoServiceImpl implements ICursoService {

	@Autowired
	private ICursoRepository repository;
	
	@Override
	public List<Curso> listar() {
		return repository.findAll();
	}

	@Override
	public Curso buscarPorId(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Curso crear(Curso curso) {
		return repository.save(curso);
	}

	@Override
	public void eliminar(Long id) {
		repository.deleteById(id);
	}

}
