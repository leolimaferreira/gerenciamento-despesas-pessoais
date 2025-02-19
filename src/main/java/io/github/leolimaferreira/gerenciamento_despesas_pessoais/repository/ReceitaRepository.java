package io.github.leolimaferreira.gerenciamento_despesas_pessoais.repository;

import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Receita;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReceitaRepository extends JpaRepository<Receita, UUID> {
    boolean existsByUsuario(Usuario usuario);

    Page<Receita> findAll(Specification<Receita> specs, Pageable pageRequest);
}
