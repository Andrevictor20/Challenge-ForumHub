package com.br.alura.Challenge_ForumHub.services;

import com.br.alura.Challenge_ForumHub.dto.DadosCadastroTopico;
import com.br.alura.Challenge_ForumHub.dto.DadosListagemTopico;
import com.br.alura.Challenge_ForumHub.infra.exception.ValidacaoException;
import com.br.alura.Challenge_ForumHub.model.Topico;
import com.br.alura.Challenge_ForumHub.repository.TopicoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TopicoService {

    private final TopicoRepository repository;

    @Transactional
    public Topico cadastrar(DadosCadastroTopico dados) {
         if (repository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
             throw new ValidacaoException("Já existe um tópico com mesmo título e mensagem!");
         }
        var topico = new Topico(dados);
        repository.save(topico);
        return topico;
    }

    public Page<DadosListagemTopico> listar(Pageable paginacao) {
        return repository.findAllByEstadoDoTopicoTrue(paginacao).map(DadosListagemTopico::new);
    }

    public Topico detalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado!"));
    }
}