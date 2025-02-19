package io.github.leolimaferreira.gerenciamento_despesas_pessoais.validator;

import io.github.leolimaferreira.gerenciamento_despesas_pessoais.exceptions.RegistroDuplicadoException;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Usuario;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
        Usuario usuarioEncontrado = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioEncontrado == null) {
            return false;
        }

        return usuario.getId()== null && !usuario.getId().equals(usuarioEncontrado.getId());
    }
}
