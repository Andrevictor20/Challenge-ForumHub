package com.br.alura.Challenge_ForumHub.model;

import com.br.alura.Challenge_ForumHub.dto.DadosCadastroResposta;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "respostas")
@Entity(name = "Resposta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensagem;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    private Boolean solucao;
    private Boolean ativo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;


    public Resposta(DadosCadastroResposta dados, Usuario autor, Topico topico) {
        this.mensagem = dados.mensagem();
        this.autor = autor;
        this.topico = topico;
        this.dataCriacao = LocalDateTime.now();
        this.solucao = false;
        this.ativo = true;
    }

    public void atualizarMensagem(String novaMensagem) {
        if (novaMensagem != null && !novaMensagem.isBlank()) {
            this.mensagem = novaMensagem;
        }
    }

    public void excluir() {
        this.ativo = false;
    }

    public void marcarComoSolucao() {
        this.solucao = true;
    }

    public void desmarcarComoSolucao() {
        this.solucao = false;
    }

}