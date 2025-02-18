package io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record DespesaDTO(
        UUID id,
        @NotBlank(message = "campo obrigatório")
        @Size(min = 5, max = 255, message = "campo fora do tamanho padrão")
        String descricao,
        @NotNull(message = "campo obrigatório")
        @DecimalMin(value = "0.01", message = "o valor deve ser maior que zero")
        BigDecimal valor,
        @NotNull(message = "campo obrigatório")
        @Size(max = 30, message = "campo fora do tamanho padrão")
        String categoria,
        @NotNull(message = "campo obrigatorio")
        @PastOrPresent(message = "nao pode ser uma data futura")
        LocalDate dataDespesa,
        @NotNull(message = "campo obrigatório")
        UUID idUsuario
) {
}
