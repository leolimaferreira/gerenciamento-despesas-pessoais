package io.github.leolimaferreira.gerenciamento_despesas_pessoais.repository.specs;

import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Categoria;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Despesa;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class DespesaSpecs {

    public static Specification<Despesa> descricaoLike(String descricao) {
        return (root, query, cb) -> cb.like( cb.upper(root.get("descricao")), "%" + descricao.toUpperCase() + "%");
    }

    public static Specification<Despesa> valorEqual(BigDecimal valor) {
        return (root, query, cb) -> cb.equal(root.get("valor"), valor);
    }

    public static Specification<Despesa> categoriaEqual(Categoria categoria) {
        return (root, query, cb) -> cb.equal(root.get("categoria"), categoria);
    }

    public static Specification<Despesa> mesDespesaEqual(Integer mesDespesa) {
        return (root, query, cb) -> cb.equal( cb.function("EXTRACT", Integer.class, cb.literal("MONTH"), root.get("dataDespesa")), mesDespesa);
    }

}