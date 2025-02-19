package io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

@Schema(name = "Usuário")
public record UsuarioDTO(
        UUID id,
        @NotBlank(message = "campo obrigatório")
        @Size(min = 10, max = 255, message = "campo fora do tamanho padrão")
        String email,
        @NotBlank(message = "campo obrigatório")
        @Size(min = 8, max = 255, message = "campo fora do tamanho padrão")
        String senha,
        List<String> cargos
) {
}
