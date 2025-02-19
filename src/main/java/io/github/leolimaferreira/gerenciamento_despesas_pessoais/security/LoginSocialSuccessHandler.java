package io.github.leolimaferreira.gerenciamento_despesas_pessoais.security;

import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Usuario;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String SENHA_PADRAO = "321";

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();

        String email = oAuth2User.getAttribute("email");

        Usuario usuario = usuarioService.obterPorEmail(email);

        if(usuario == null) {
            usuario = cadastrarUsuarioNaBase(email);
        }

        authentication = new CustomAuthentication(usuario);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private Usuario cadastrarUsuarioNaBase(String email) {
        Usuario usuario;
        usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(SENHA_PADRAO);
        usuario.setCargos(List.of("USUARIO"));

        usuarioService.salvar(usuario);

        return usuario;
    }

}
