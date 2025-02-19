package io.github.leolimaferreira.gerenciamento_despesas_pessoais.service;

import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Receita;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.repository.ReceitaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static io.github.leolimaferreira.gerenciamento_despesas_pessoais.repository.specs.ReceitaSpecs.*;

@Service
@RequiredArgsConstructor
public class ReceitaService {

    private final ReceitaRepository receitaRepository;

    public Receita salvar(Receita receita) {return receitaRepository.save(receita);}

    public void excluir(Receita receita) {receitaRepository.delete(receita);}

    public Optional<Receita> obterPorID(UUID id) {return receitaRepository.findById(id);}

    public void atualizar(Receita receita) {
        if(receita.getId() == null) {
            throw new IllegalArgumentException("Receita não cadastrada");
        }

        receitaRepository.save(receita);
    }

    public Page<Receita> pesquisa(
            String descricao,
            BigDecimal valor,
            Integer mesReceita,
            Integer pagina,
            Integer tamanhoPagina
    ) {
        Specification<Receita> specs = Specification.where(((root, query, cb) -> cb.conjunction()));

        if(descricao != null) {
            specs = specs.and(descricaoLike(descricao));
        }

        if(valor != null) {
            specs = specs.and(valorEqual(valor));
        }


        if(mesReceita != null) {
            specs = specs.and(mesReceitaEqual(mesReceita));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        return receitaRepository.findAll(specs, pageRequest);
    }
}

