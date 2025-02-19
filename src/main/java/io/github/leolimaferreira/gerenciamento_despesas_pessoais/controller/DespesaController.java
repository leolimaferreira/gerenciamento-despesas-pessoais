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
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/despesas")
public class DespesaController {

    private final DespesaService despesaService;
    private final DespesaMapper mapper;

    @PostMapping
    public void salvar(@RequestBody @Valid DespesaDTO dto) {
        Despesa despesa = mapper.toEntity(dto);
        despesaService.salvar(despesa);
        //return ResponseEntity.created().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaDespesaDTO> obterDetalhes(@PathVariable("id") String id) {
        return despesaService.obterPorID(UUID.fromString(id))
                .map(despesa -> {
                    ResultadoPesquisaDespesaDTO dto = mapper.toDTO(despesa);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

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
