package com.springcloud.msvc.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springcloud.msvc.usuarios.entity.Usuario;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

}
