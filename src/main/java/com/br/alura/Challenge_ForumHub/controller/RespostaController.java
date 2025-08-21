package com.br.alura.Challenge_ForumHub.controller;

import com.br.alura.Challenge_ForumHub.dto.*;
import com.br.alura.Challenge_ForumHub.model.Usuario;
import com.br.alura.Challenge_ForumHub.services.RespostaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/respostas")
@RequiredArgsConstructor
public class RespostaController {

    private final RespostaService service;

    @PostMapping
    @Transactional
    public ResponseEntity criar(@RequestBody @Valid DadosCadastroResposta dados, @AuthenticationPrincipal Usuario autor, UriComponentsBuilder uriBuilder) {
        var resposta = service.criar(dados, autor);
        var uri = uriBuilder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoResposta(resposta));
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var resposta = service.detalhar(id);
        return ResponseEntity.ok(new DadosDetalhamentoResposta(resposta));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoResposta dados, @AuthenticationPrincipal Usuario autor) {
        var resposta = service.atualizar(id, dados, autor);
        return ResponseEntity.ok(new DadosDetalhamentoResposta(resposta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id, @AuthenticationPrincipal Usuario autor) {
        service.deletar(id, autor);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/solucao")
    @Transactional
    public ResponseEntity marcarComoSolucao(@PathVariable Long id, @AuthenticationPrincipal Usuario autorDoTopico) {
        service.marcarComoSolucao(id, autorDoTopico);
        return ResponseEntity.ok().build();
    }
}