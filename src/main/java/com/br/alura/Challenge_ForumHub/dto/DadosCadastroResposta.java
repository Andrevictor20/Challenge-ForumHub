package com.br.alura.Challenge_ForumHub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroResposta(
        @NotBlank String mensagem,

        @NotNull Long topicoId
) {
}