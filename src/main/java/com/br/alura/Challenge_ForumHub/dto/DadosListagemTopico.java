package com.br.alura.Challenge_ForumHub.dto;

import com.br.alura.Challenge_ForumHub.model.Topico;

public record DadosListagemTopico(
        Long id,
        String titulo,
        String mensagem,
        String nomeAutor,
        Boolean autorAtivo,
        String curso
) {
    public DadosListagemTopico(Topico topico){
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getAutor().getNome(),
                topico.getAutor().getAtivo(),
                topico.getCurso()
                );
    }
}


