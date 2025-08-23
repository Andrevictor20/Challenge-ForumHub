package com.br.alura.Challenge_ForumHub.services;

import com.br.alura.Challenge_ForumHub.dto.DadosAtualizacaoTopico;
import com.br.alura.Challenge_ForumHub.dto.DadosCadastroTopico;
import com.br.alura.Challenge_ForumHub.dto.DadosListagemTopico;
import com.br.alura.Challenge_ForumHub.infra.exception.ValidacaoException;
import com.br.alura.Challenge_ForumHub.model.Topico;
import com.br.alura.Challenge_ForumHub.model.Usuario;
import com.br.alura.Challenge_ForumHub.repository.TopicoRepository;
import com.br.alura.Challenge_ForumHub.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Topico cadastrar(DadosCadastroTopico dados, Usuario autor) {
        if (topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            throw new ValidacaoException("Já existe um tópico com mesmo título e mensagem!");
        }

        var topico = new Topico(dados, autor);
        topicoRepository.save(topico);
        return topico;
    }

    public Page<DadosListagemTopico> listar(Pageable paginacao, String nomeCurso) {
        if (nomeCurso != null && !nomeCurso.isEmpty()) {
            return topicoRepository.findByCursoNomeContainingIgnoreCaseAndAtivoTrue(nomeCurso, paginacao).map(DadosListagemTopico::new);
        }
        return topicoRepository.findAllByEstadoDoTopicoTrue(paginacao).map(DadosListagemTopico::new);
    }

    public Topico detalhar(Long id) {
        return topicoRepository.findByIdAndEstadoDoTopicoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado!"));
    }

    @Transactional
    public void deletar(Long id) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado!"));

        topico.excluir();
    }

    @Transactional
    public Topico atualizar(Long id, DadosAtualizacaoTopico dados) {
        var topico = detalhar(id);
        topico.atualizarInformacoes(dados);
        return topico;
    }
}