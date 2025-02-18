package io.github.leolimaferreira.gerenciamento_despesas_pessoais.controller;

import io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.UsuarioDTO;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.mappers.UsuarioMapper;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Usuario;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper mapper;

    @PostMapping
    public void salvar(@RequestBody @Valid UsuarioDTO dto) {
        Usuario usuario = mapper.toEntity(dto);
        usuarioService.salvar(usuario);
    }

    @GetMapping("{id}")
    public ResponseEntity<UsuarioDTO> obterDetalhes(@PathVariable("id") String id) {
        Optional<Usuario> usuarioOptional = usuarioService.obterPorId(UUID.fromString(id));

        return usuarioOptional
                .map(usuario -> {
                    UsuarioDTO dto = mapper.toDTO(usuario);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id) {

        Optional<Usuario> usuarioOptional = usuarioService.obterPorId(UUID.fromString(id));

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        usuarioService.deletar(usuarioOptional.get());

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
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
