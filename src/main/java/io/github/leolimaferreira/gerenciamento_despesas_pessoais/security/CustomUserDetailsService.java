package io.github.leolimaferreira.gerenciamento_despesas_pessoais.security;

import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Usuario;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.obterPorEmail(login);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(usuario.getCargos().toArray(new String[usuario.getCargos().size()]))
                .build();
    }
}
