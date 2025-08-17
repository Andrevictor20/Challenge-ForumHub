package com.br.alura.Challenge_ForumHub.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoResposta(
        @NotBlank
        String mensagem
) {}