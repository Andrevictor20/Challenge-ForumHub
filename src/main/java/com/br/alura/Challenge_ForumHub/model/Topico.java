package com.br.alura.Challenge_ForumHub.model;

import com.br.alura.Challenge_ForumHub.dto.DadosAtualizacaoTopico;
import com.br.alura.Challenge_ForumHub.dto.DadosCadastroTopico;
import jakarta.persistence.*; // Importações importantes!
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataDeCriacao;
    private Boolean estadoDoTopico;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;
    private String curso;

    public Topico(DadosCadastroTopico dados,Usuario autor) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.autor = autor;
        this.curso = dados.curso();
        this.dataDeCriacao = LocalDateTime.now();
        this.estadoDoTopico = true;
    }

    public void excluir() {
        this.estadoDoTopico = false;
    }
    public void atualizarInformacoes(DadosAtualizacaoTopico dados) {
        if (dados.titulo() != null) {
            this.titulo = dados.titulo();
        }
        if (dados.mensagem() != null) {
            this.mensagem = dados.mensagem();
        }
    }
}