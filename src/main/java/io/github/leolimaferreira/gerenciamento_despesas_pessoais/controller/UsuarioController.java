package io.github.leolimaferreira.gerenciamento_despesas_pessoais.controller;

import io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.UsuarioDTO;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.mappers.UsuarioMapper;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Usuario;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios")
public class UsuarioController implements GenericController{

    private final UsuarioService usuarioService;
    private final UsuarioMapper mapper;

    @PostMapping
    @Operation(summary = "Salvar", description = "Cadastrar novo usuárip")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "202", description = "Erro de validação."),
            @ApiResponse(responseCode = "409", description = "Usuário já cadastrado.")
    })
    public ResponseEntity<Void> salvar(@RequestBody @Valid UsuarioDTO dto) {
        Usuario usuario = mapper.toEntity(dto);
        usuarioService.salvar(usuario);
        URI location = gerarHeaderLocation(usuario.getId());
        return ResponseEntity.created(location).build();
    }


    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO', 'CONVIDADO')")
    @Operation(summary = "Obter Detalhes", description = "Retorna os dados do usuário pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    })
    public ResponseEntity<UsuarioDTO> obterDetalhes(@PathVariable("id") String id) {
        Optional<Usuario> usuarioOptional = usuarioService.obterPorId(UUID.fromString(id));

        return usuarioOptional
                .map(usuario -> {
                    UsuarioDTO dto = mapper.toDTO(usuario);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar", description = "Deleta um usuário existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "400", description = "Usuário possui receitas/despesas cadastradas.")
    })
    public ResponseEntity<Void> deletar(@PathVariable("id") String id) {

        Optional<Usuario> usuarioOptional = usuarioService.obterPorId(UUID.fromString(id));

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        usuarioService.deletar(usuarioOptional.get());

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @Operation(summary = "Atualizar", description = "Atualiza um usuário existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "409", description = "Usuário já cadastrado.")
    })
    public ResponseEntity<Void> atualizar (
            @PathVariable("id") String id, @RequestBody @Valid UsuarioDTO dto
    ) {
        Optional<Usuario> usuarioOptional = usuarioService.obterPorId(UUID.fromString(id));

        if(usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = usuarioOptional.get();
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        usuario.setCargos(dto.cargos());

        usuarioService.atualizar(usuario);

        return ResponseEntity.noContent().build();
    }
}
