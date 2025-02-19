package io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Resultado da Pesquisa de Receita")
public record ResultadoPesquisaReceitaDTO(
        UUID id,
        String descricao,
        BigDecimal valor,
        LocalDate dataReceita,
        UsuarioDTO usuario
) {
}
