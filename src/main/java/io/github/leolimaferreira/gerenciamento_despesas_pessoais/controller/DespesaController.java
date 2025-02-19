package io.github.leolimaferreira.gerenciamento_despesas_pessoais.controller;

import io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.DespesaDTO;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.ResultadoPesquisaDespesaDTO;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.mappers.DespesaMapper;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Categoria;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Despesa;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.service.DespesaService;
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
public class DespesaController implements GenericController {

    private final DespesaService despesaService;
    private final DespesaMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public ResponseEntity<Void> salvar(@RequestBody @Valid DespesaDTO dto) {
        Despesa despesa = mapper.toEntity(dto);
        despesaService.salvar(despesa);
        URI location = gerarHeaderLocation(despesa.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO', 'CONVIDADO')")
    public ResponseEntity<ResultadoPesquisaDespesaDTO> obterDetalhes(@PathVariable("id") String id) {
        return despesaService.obterPorID(UUID.fromString(id))
                .map(despesa -> {
                    ResultadoPesquisaDespesaDTO dto = mapper.toDTO(despesa);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @DeleteMapping("{id}")
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
