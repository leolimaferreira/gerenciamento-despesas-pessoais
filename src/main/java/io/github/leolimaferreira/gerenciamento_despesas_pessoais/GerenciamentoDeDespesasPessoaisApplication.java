package io.github.leolimaferreira.gerenciamento_despesas_pessoais;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GerenciamentoDeDespesasPessoaisApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciamentoDeDespesasPessoaisApplication.class, args);
	}

}
