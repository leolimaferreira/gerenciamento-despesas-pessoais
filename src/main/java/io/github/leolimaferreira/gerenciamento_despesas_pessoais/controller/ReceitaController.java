package io.github.leolimaferreira.gerenciamento_despesas_pessoais.controller;

import io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.DespesaDTO;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.ReceitaDTO;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.ResultadoPesquisaDespesaDTO;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.ResultadoPesquisaReceitaDTO;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.mappers.DespesaMapper;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.mappers.ReceitaMapper;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Categoria;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Despesa;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Receita;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.service.ReceitaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/receitas")
public class ReceitaController {

    private final ReceitaService receitaService;
    private final ReceitaMapper mapper;

    @PostMapping
    public void salvar(@RequestBody @Valid ReceitaDTO dto) {
        Receita receita = mapper.toEntity(dto);
        receitaService.salvar(receita);
        //return ResponseEntity.created().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaReceitaDTO> obterDetalhes(@PathVariable("id") String id) {
        return receitaService.obterPorID(UUID.fromString(id))
                .map(receita -> {
                    ResultadoPesquisaReceitaDTO dto = mapper.toDTO(receita);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> excluir(@PathVariable("id") String id) {
        return receitaService.obterPorID(UUID.fromString(id))
                .map(receita -> {
                    receitaService.excluir(receita);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @Validated
    @GetMapping
    public ResponseEntity<Page<ResultadoPesquisaReceitaDTO>> pesquisa(
            @RequestParam(value = "descricao", required = false)
            String descricao,
            @RequestParam(value = "valor", required = false)
            BigDecimal valor,
            @RequestParam(value = "mes-despesa", required = false)
            @Min(value = 1, message = "campo fora do tamanho padrão")
            @Max(value = 12, message = "campo fora do tamanho padrão")
            Integer mesReceita,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina
    ) {
        Page<Receita> paginaResultado = receitaService.pesquisa(
                descricao, valor, mesReceita, pagina, tamanhoPagina);

        Page<ResultadoPesquisaReceitaDTO> resultado = paginaResultado.map(mapper::toDTO);

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(
            @PathVariable("id") String id, @RequestBody @Valid ReceitaDTO dto
    ) {
        return receitaService.obterPorID(UUID.fromString(id))
                .map(receita -> {
                    Receita entidadeAux = mapper.toEntity(dto);
                    receita.setDescricao(entidadeAux.getDescricao());
                    receita.setValor(entidadeAux.getValor());
                    receita.setDataReceita(entidadeAux.getDataReceita());
                    receita.setUsuario(entidadeAux.getUsuario());

                    receitaService.atualizar(receita);

                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }
}
