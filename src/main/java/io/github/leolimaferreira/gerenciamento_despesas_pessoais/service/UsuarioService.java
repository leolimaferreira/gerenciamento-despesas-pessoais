package io.github.leolimaferreira.gerenciamento_despesas_pessoais.service;

import io.github.leolimaferreira.gerenciamento_despesas_pessoais.exceptions.OperacaoNaoPermitidaException;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Usuario;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.repository.DespesaRepository;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.repository.ReceitaRepository;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.repository.UsuarioRepository;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.validator.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ReceitaRepository receitaRepository;
    private final DespesaRepository despesaRepository;
    private final UsuarioValidator validator;
    //private final PasswordEncoder encoder;

    public Usuario salvar(Usuario usuario) {
        //usuario.setSenha(encoder.encode(usuario.getSenha()));
        validator.validar(usuario);
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> obterPorId(UUID id) {return usuarioRepository.findById(id);}

    public void deletar(Usuario usuario) {
        if (possuiDespesas(usuario) || possuiReceitas(usuario)) {
            throw new OperacaoNaoPermitidaException(
                    "Não é permitido excluir um Usuário que possui receitas ou despesas cadastradas!"
            );
        }
        usuarioRepository.delete(usuario);
    }

    public void atualizar(Usuario usuario) {
        if(usuario.getId() == null) {
            throw new IllegalArgumentException("Usuário não cadastrado!");
        }
        validator.validar(usuario);
        usuarioRepository.save(usuario);
    }

    private boolean possuiReceitas(Usuario usuario) {return receitaRepository.existsByUsuario(usuario);}

    private boolean possuiDespesas(Usuario usuario) {return despesaRepository.existsByUsuario(usuario);}
}
