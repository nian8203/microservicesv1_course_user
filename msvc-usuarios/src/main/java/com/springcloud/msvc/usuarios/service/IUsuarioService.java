package com.springcloud.msvc.usuarios.service;

import java.util.List;
import java.util.Optional;

import com.springcloud.msvc.usuarios.entity.Usuario;

public interface IUsuarioService {

	List<Usuario> listar();
	Usuario buscarPorId(Long id);
	Usuario crear(Usuario usuario);
	void eliminar(Long id);
	Optional<Usuario> buscarPorCorreo(String email);
}
