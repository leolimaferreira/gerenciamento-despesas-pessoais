package io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Erro Campo")
public record ErroCampo(String campo, String erro) {
}
