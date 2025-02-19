package io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultadoPesquisaReceitaDTO(
        UUID id,
        String descricao,
        BigDecimal valor,
        LocalDate dataReceita,
        UsuarioDTO usuario
) {
}
