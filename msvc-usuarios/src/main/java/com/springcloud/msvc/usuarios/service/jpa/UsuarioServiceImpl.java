package com.springcloud.msvc.usuarios.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springcloud.msvc.usuarios.entity.Usuario;
import com.springcloud.msvc.usuarios.repository.IUsuarioRepository;
import com.springcloud.msvc.usuarios.service.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioRepository repository;
	
	@Override
	public List<Usuario> listar() {
		return repository.findAll();
	}

	@Override
	public Usuario buscarPorId(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Usuario crear(Usuario usuario) {
		return repository.save(usuario);
	}

	@Override
	public void eliminar(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Optional<Usuario> buscarPorCorreo(String email) {
		return repository.findByEmail(email);
	}

}
