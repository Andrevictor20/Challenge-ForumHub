package com.br.alura.Challenge_ForumHub.repository;

import com.br.alura.Challenge_ForumHub.model.Resposta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {

    @Query("""
            SELECT r FROM Resposta r
            JOIN FETCH r.autor
            WHERE r.topico.id = :idTopico AND r.ativo = true
            """)
    Page<Resposta> findAllByTopicoIdAndAtivoTrue(Long idTopico, Pageable paginacao);

    @Query("""
            SELECT r FROM Resposta r
            JOIN FETCH r.autor
            WHERE r.id = :id
            """)
    Optional<Resposta> findByIdWithAutor(Long id);
}