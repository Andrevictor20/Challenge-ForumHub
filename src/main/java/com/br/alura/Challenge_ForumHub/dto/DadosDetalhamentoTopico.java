package com.br.alura.Challenge_ForumHub.dto;

import com.br.alura.Challenge_ForumHub.model.Topico;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataDeCriacao,
        String autor,
        String curso) {

    public DadosDetalhamentoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataDeCriacao(),
                topico.getAutor(),
                topico.getCurso()
        );
    }
}