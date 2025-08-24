package com.br.alura.Challenge_ForumHub.model;

import com.br.alura.Challenge_ForumHub.dto.DadosAtualizacaoTopico;
import com.br.alura.Challenge_ForumHub.dto.DadosCadastroTopico;
import com.br.alura.Challenge_ForumHub.infra.exception.ValidacaoException;
import jakarta.persistence.*; // Importações importantes!
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Cacheable
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
    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resposta> respostas = new ArrayList<>();

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

    public void marcarRespostaComoSolucao(Resposta respostaSolucao) {
        if (!this.respostas.contains(respostaSolucao)) {
            throw new ValidacaoException("A resposta não pertence a este tópico.");
        }

        this.respostas.forEach(Resposta::desmarcarComoSolucao);

        respostaSolucao.marcarComoSolucao();
    }
}