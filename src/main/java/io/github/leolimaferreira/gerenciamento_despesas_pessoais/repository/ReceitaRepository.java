package io.github.leolimaferreira.gerenciamento_despesas_pessoais.repository;

import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Receita;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReceitaRepository extends JpaRepository<Receita, UUID> {
    boolean existsByUsuario(Usuario usuario);
}
