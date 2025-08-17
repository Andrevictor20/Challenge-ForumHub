package com.br.alura.Challenge_ForumHub.repository;

import com.br.alura.Challenge_ForumHub.model.Topico;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TopicoRepository extends JpaRepository<Topico,Long> {
    Page<Topico> findAllByEstadoDoTopicoTrue(Pageable paginacao);

    boolean existsByTituloAndMensagem(@NotBlank String titulo, @NotBlank String mensagem);
}
