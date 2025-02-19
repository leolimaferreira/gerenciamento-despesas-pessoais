package io.github.leolimaferreira.gerenciamento_despesas_pessoais.exceptions;

import lombok.Getter;

public class CampoInvalidoException extends RuntimeException{

    @Getter
    private String campo;

    public CampoInvalidoException(String gampo, String mensagem) {
        super(mensagem);
        this.campo = campo;
    }

}
