package io.github.leolimaferreira.gerenciamento_despesas_pessoais.validator;

import io.github.leolimaferreira.gerenciamento_despesas_pessoais.exceptions.RegistroDuplicadoException;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Usuario;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    public void validar(Usuario usuario) {
        if(existeUsuarioCadastrado(usuario)) {
            throw new RegistroDuplicadoException("Usuário já cadastrado!");
        }
    }

    private boolean existeUsuarioCadastrado(Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(usuario.getEmail());
        if(usuario.getId() == null) {
            return usuarioOptional.isPresent();
        }

        return !usuario.getId().equals(usuarioOptional.get().getId()) && usuarioOptional.isPresent();
    }
}
