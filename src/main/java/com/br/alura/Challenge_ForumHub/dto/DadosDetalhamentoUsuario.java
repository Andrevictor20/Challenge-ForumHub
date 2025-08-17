package com.br.alura.Challenge_ForumHub.dto;

import com.br.alura.Challenge_ForumHub.model.Usuario;

public record DadosDetalhamentoUsuario(
        Long id,
        String nome,
        String login
) {
    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getLogin());
    }
}