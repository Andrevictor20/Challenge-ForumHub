package com.br.alura.Challenge_ForumHub.services;

import com.br.alura.Challenge_ForumHub.dto.*;
import com.br.alura.Challenge_ForumHub.infra.exception.ValidacaoException;
import com.br.alura.Challenge_ForumHub.model.Resposta;
import com.br.alura.Challenge_ForumHub.model.Usuario;
import com.br.alura.Challenge_ForumHub.repository.RespostaRepository;
import com.br.alura.Challenge_ForumHub.repository.TopicoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RespostaService {

    private final TopicoRepository topicoRepository;
    private final RespostaRepository respostaRepository;

    @Transactional
    @CacheEvict(value = {"respostas", "respostas-por-topico"}, allEntries = true)
    public Resposta criar(DadosCadastroResposta dados, Usuario autor) {
        var topico = topicoRepository.findById(dados.topicoId())
                .orElseThrow(() -> new ValidacaoException("Tópico não encontrado!"));
        var resposta = new Resposta(dados, autor, topico);
        respostaRepository.save(resposta);
        return resposta;
    }

    @Cacheable(value = "respostas-por-topico", key = "#idTopico + '-' + #paginacao.pageNumber + '-' + #paginacao.pageSize")
    public Page<DadosDetalhamentoResposta> listarPorTopico(Long idTopico, Pageable paginacao) {
        if (!topicoRepository.existsById(idTopico)) {
            throw new EntityNotFoundException("Tópico não encontrado!");
        }
        return respostaRepository.findAllByTopicoIdAndAtivoTrue(idTopico, paginacao).map(DadosDetalhamentoResposta::new);
    }

    @Cacheable(value = "respostas", key = "#id")
    public Resposta detalhar(Long id) {
        return buscarRespostaPorId(id);
    }

    @Transactional
    @CacheEvict(value = {"respostas", "respostas-por-topico"}, allEntries = true)
    public Resposta atualizar(Long id, DadosAtualizacaoResposta dados, Usuario autor) {
        var resposta = buscarRespostaPorId(id);
        validarAutor(resposta, autor);
        resposta.atualizarMensagem(dados.mensagem());
        return resposta;
    }

    @Transactional
    @CacheEvict(value = {"respostas", "respostas-por-topico"}, allEntries = true)
    public void deletar(Long id, Usuario autor) {
        var resposta = buscarRespostaPorId(id);
        validarAutor(resposta, autor);
        resposta.excluir();
    }

    @Transactional
    @CacheEvict(value = {"respostas", "respostas-por-topico", "topicos"}, allEntries = true)
    public void marcarComoSolucao(Long id, Usuario autorDoTopico) {
        var resposta = buscarRespostaPorId(id);
        var topico = resposta.getTopico();

        if (!topico.getAutor().equals(autorDoTopico)) {
            throw new ValidacaoException("Apenas o autor do tópico pode marcar uma solução.");
        }

        topico.marcarRespostaComoSolucao(resposta);
    }

    private Resposta buscarRespostaPorId(Long id) {
        return respostaRepository.findById(id)
                .filter(Resposta::getAtivo)
                .orElseThrow(() -> new EntityNotFoundException("Resposta não encontrada!"));
    }

    private void validarAutor(Resposta resposta, Usuario autor) {
        if (!resposta.getAutor().equals(autor)) {
            throw new ValidacaoException("Você não tem permissão para modificar esta resposta.");
        }
    }
}