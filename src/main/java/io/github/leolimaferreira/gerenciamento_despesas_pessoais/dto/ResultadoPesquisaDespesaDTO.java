package io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultadoPesquisaDespesaDTO(
        UUID id,
        String descricao,
        BigDecimal valor,
        String categoria,
        LocalDate dataDespesa,
        UsuarioDTO usuario
) {
}
