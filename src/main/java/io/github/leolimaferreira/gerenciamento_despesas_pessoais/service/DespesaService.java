package io.github.leolimaferreira.gerenciamento_despesas_pessoais.service;

import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Categoria;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Despesa;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.repository.DespesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static io.github.leolimaferreira.gerenciamento_despesas_pessoais.repository.specs.DespesaSpecs.*;

@Service
@RequiredArgsConstructor
public class DespesaService {

    private final DespesaRepository despesaRepository;

    public Despesa salvar(Despesa despesa) {return despesaRepository.save(despesa);}

    public void excluir(Despesa despesa) {despesaRepository.delete(despesa);}

    public Optional<Despesa> obterPorID(UUID id) {return despesaRepository.findById(id);}

    public void atualizar(Despesa despesa) {
        if(despesa.getId() == null) {
            throw new IllegalArgumentException("Despesa não cadastrada");
        }

        despesaRepository.save(despesa);
    }

    public Page<Despesa> pesquisa(
            String descricao,
            BigDecimal valor,
            Categoria categoria,
            Integer mesDespesa,
            Integer pagina,
            Integer tamanhoPagina
    ) {
        Specification<Despesa> specs = Specification.where(((root, query, cb) -> cb.conjunction()));

        if(descricao != null) {
            specs = specs.and(descricaoLike(descricao));
        }

        if(valor != null) {
            specs = specs.and(valorEqual(valor));
        }

        if(categoria != null) {
            specs = specs.and(categoriaEqual(categoria));
        }

        if(mesDespesa != null) {
            specs = specs.and(mesDespesaEqual(mesDespesa));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        return despesaRepository.findAll(specs, pageRequest);
    }
}
