package com.springcloud.msvc.cursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springcloud.msvc.cursos.entity.Curso;

public interface ICursoRepository extends JpaRepository<Curso, Long> {
	

}
