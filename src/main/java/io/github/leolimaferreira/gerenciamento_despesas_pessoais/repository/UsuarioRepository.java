package io.github.leolimaferreira.gerenciamento_despesas_pessoais.repository;

import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByEmail(String email);
}
