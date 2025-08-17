package com.br.alura.Challenge_ForumHub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosCadastroUsuario(
        @NotBlank String nome,
        @NotBlank String login,
        @Size(min = 6, message = "A senha deve conter no mínimo 6 caracteres") @NotBlank String senha
) {}