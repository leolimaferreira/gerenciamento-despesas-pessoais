package io.github.leolimaferreira.gerenciamento_despesas_pessoais.controller;

import io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.DespesaDTO;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.ResultadoPesquisaDespesaDTO;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.mappers.DespesaMapper;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Categoria;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Despesa;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.service.DespesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/despesas")
@Tag(name = "Despesas")
public class DespesaController implements GenericController {

    private final DespesaService despesaService;
    private final DespesaMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @Operation(summary = "Salvar", description = "Cadastrar nova Despesa")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrada com sucesso."),
            @ApiResponse(responseCode = "202", description = "Erro de validação."),
    })
    public ResponseEntity<Void> salvar(@RequestBody @Valid DespesaDTO dto) {
        Despesa despesa = mapper.toEntity(dto);
        despesaService.salvar(despesa);
        URI location = gerarHeaderLocation(despesa.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO', 'CONVIDADO')")
    @Operation(summary = "Obter Detalhes", description = "Retorna os dados da despesa pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Despesa encontrada."),
            @ApiResponse(responseCode = "404", description = "Despesa não encontrada.")
    })
    public ResponseEntity<ResultadoPesquisaDespesaDTO> obterDetalhes(@PathVariable("id") String id) {
        return despesaService.obterPorID(UUID.fromString(id))
                .map(despesa -> {
                    ResultadoPesquisaDespesaDTO dto = mapper.toDTO(despesa);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @DeleteMapping("{id}")
    @Operation(summary = "Deletar", description = "Deleta uma despesa existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deletada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Despesa não encontrada."),
    })
    public ResponseEntity<Object> excluir(@PathVariable("id") String id) {
        return despesaService.obterPorID(UUID.fromString(id))
                .map(despesa -> {
                    despesaService.excluir(despesa);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @Validated
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO', 'CONVIDADO')")
    @Operation(summary = "Pesquisar", description = "Realiza pesquisa de todas as despesas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso."),
    })
    public ResponseEntity<Page<ResultadoPesquisaDespesaDTO>> pesquisa(
            @RequestParam(value = "descricao", required = false)
            String descricao,
            @RequestParam(value = "valor", required = false)
            BigDecimal valor,
            @RequestParam(value = "categoria", required = false)
            Categoria categoria,
            @RequestParam(value = "mes-despesa", required = false)
            @Min(value = 1, message = "campo fora do tamanho padrão")
            @Max(value = 12, message = "campo fora do tamanho padrão")
            Integer mesDespesa,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina
    ) {
        Page<Despesa> paginaResultado = despesaService.pesquisa(
                descricao, valor, categoria, mesDespesa, pagina, tamanhoPagina);

        Page<ResultadoPesquisaDespesaDTO> resultado = paginaResultado.map(mapper::toDTO);

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @Operation(summary = "Atualizar", description = "Atualiza uma despesa existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Despesa não encontrada."),
    })
    public ResponseEntity<Object> atualizar(
            @PathVariable("id") String id, @RequestBody @Valid DespesaDTO dto
    ) {
        return despesaService.obterPorID(UUID.fromString(id))
                .map(despesa -> {
                    Despesa entidadeAux = mapper.toEntity(dto);
                    despesa.setDescricao(entidadeAux.getDescricao());
                    despesa.setValor(entidadeAux.getValor());
                    despesa.setCategoria(entidadeAux.getCategoria());
                    despesa.setUsuario(entidadeAux.getUsuario());

                    despesaService.atualizar(despesa);

                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }
}
