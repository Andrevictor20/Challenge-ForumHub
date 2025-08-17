package com.br.alura.Challenge_ForumHub.dto;

import com.br.alura.Challenge_ForumHub.model.Resposta;
import java.time.LocalDateTime;

public record DadosDetalhamentoResposta(
        Long id,
        String mensagem,
        LocalDateTime dataCriacao,
        Boolean solucao,
        String nomeAutor
) {
    public DadosDetalhamentoResposta(Resposta resposta) {
        this(
                resposta.getId(),
                resposta.getMensagem(),
                resposta.getDataCriacao(),
                resposta.getSolucao(),
                resposta.getAutor().getNome()
        );
    }
}