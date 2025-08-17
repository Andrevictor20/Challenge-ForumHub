package com.br.alura.Challenge_ForumHub.controller;


import com.br.alura.Challenge_ForumHub.dto.DadosAtualizacaoTopico;
import com.br.alura.Challenge_ForumHub.dto.DadosCadastroTopico;
import com.br.alura.Challenge_ForumHub.dto.DadosDetalhamentoTopico;
import com.br.alura.Challenge_ForumHub.dto.DadosListagemTopico;
import com.br.alura.Challenge_ForumHub.model.Topico;
import com.br.alura.Challenge_ForumHub.model.Usuario;
import com.br.alura.Challenge_ForumHub.repository.TopicoRepository;
import com.br.alura.Challenge_ForumHub.services.TopicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("topicos")
@RequiredArgsConstructor
public class TopicoController {

    @Autowired
    private final TopicoService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados, @AuthenticationPrincipal Usuario autor, UriComponentsBuilder uriBuilder) {
        var topico = service.cadastrar(dados, autor);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(@PageableDefault(size=10,sort={"id"}) Pageable paginacao){
        var page = service.listar(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var topico = service.detalhar(id);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoTopico dados) {
        var topicoAtualizado = service.atualizar(id, dados);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topicoAtualizado));
    }


}
