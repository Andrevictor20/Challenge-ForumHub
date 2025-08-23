package com.br.alura.Challenge_ForumHub.repository;

import com.br.alura.Challenge_ForumHub.model.Topico;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface TopicoRepository extends JpaRepository<Topico,Long> {

    @Query("SELECT t FROM Topico t JOIN FETCH t.autor WHERE t.estadoDoTopico = true")
    Page<Topico> findAllByEstadoDoTopicoTrue(Pageable paginacao);

    Boolean existsByTituloAndMensagem(@NotBlank String titulo, @NotBlank String mensagem);

    Optional<Topico> findByIdAndEstadoDoTopicoTrue(Long id);

    @Query("""
            SELECT t FROM Topico t WHERE t.estadoDoTopico = true AND
            (LOWER(t.titulo) LIKE LOWER(CONCAT('%', :termo, '%'))
            OR LOWER(t.curso) LIKE LOWER(CONCAT('%', :termo, '%')))
            """)
    Page<Topico> findByTituloOrCursoContainingIgnoreCase(@Param("termo") String termo, Pageable paginacao);

    Page<Topico> findByAutorNomeIgnoreCaseAndEstadoDoTopicoTrue(String nomeAutor, Pageable paginacao);
}
