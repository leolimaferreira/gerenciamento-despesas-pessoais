# Gerenciamento de Despesas Pessoais API

Esta é uma API RESTful desenvolvida em Java com Spring Boot para gerenciamento de despesas e receitas pessoais. O objetivo deste projeto é permitir que os usuários gerenciem suas finanças de forma eficiente, categorizando despesas e receitas, e oferecendo funcionalidades de autenticação e autorização.

## Funcionalidades

- **Cadastro de Usuários**: Permite que novos usuários se registrem no sistema.
- **Autenticação e Autorização**: Usuários podem fazer login para acessar suas informações. O sistema utiliza roles para controle de acesso.
- **Gerenciamento de Despesas**: Usuários podem adicionar, listar, atualizar e deletar suas despesas.
- **Gerenciamento de Receitas**: Usuários podem adicionar, listar, atualizar e deletar suas receitas.
- **Categorias de Despesas**: As despesas podem ser categorizadas em diferentes tipos para melhor controle financeiro.

## Tecnologias Utilizadas

- **Java 21**: Linguagem de programação principal.
- **Spring Boot**: Framework para construção da API.
- **Hibernate**: ORM (Object-Relational Mapping) para interação com o banco de dados.
- **PostgreSQL**: Sistema de gerenciamento de banco de dados.
- **Spring Security**: Para gerenciar a autenticação e autorização dos usuários.
- **Flyway**: Para controle de versionamento do banco de dados.

## Estrutura do Projeto

A estrutura do projeto é organizada da seguinte forma:


## Instalação

### Pré-requisitos

- JDK 21
- Maven
- PostgreSQL

### Configuração

1. Clone o repositório:
   ```bash
   git clone https://github.com/leolimaferreira/gerenciamento-despesas-pessoais.git
   ```
2. Navegue até o diretório do projeto:
   ```bash
   cd gerenciamento_despesas_pessoais
   ```
3. Configure o banco de dados no arquivo src/main/resources/application.yml:
   ```yml
   spring:
     datasource:
      url: jdbc:postgresql://localhost:5432/seu_banco_de_dados
      username: seu_usuario
      password: sua_senha
   ```
4. Execute o projeto:
   ```bash
   mvn spring-boot:run
   ```

### Uso
A API pode ser acessada nas seguintes rotas:

### Usuários

- POST /usuarios: Cadastra um novo usuário.
- GET /usuarios/{id}: Obtém os detalhes do usuário.
- PUT /usuarios/{id}: Atualiza um usuário existente.
- DELETE /usuarios/{id}: Deleta um usuário.

### Despesas

- POST /despesas: Adiciona uma nova despesa.
- GET /despesas: Lista todas as despesas.
- PUT /despesas/{id}: Atualiza uma despesa existente.
- DELETE /despesas/{id}: Deleta uma despesa.

### Receitas

- POST /receitas: Adiciona uma nova receita.
- GET /receitas: Lista todas as receitas.
- PUT /receitas/{id}: Atualiza uma receita existente.
- DELETE /receitas/{id}: Deleta uma receita.

## Contato
- **Nome: Leonardo Ferreira
- **Email:leonardo.limaferreira718@gmail.com

