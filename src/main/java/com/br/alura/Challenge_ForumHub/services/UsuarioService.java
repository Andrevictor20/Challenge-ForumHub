package com.br.alura.Challenge_ForumHub.services;

import com.br.alura.Challenge_ForumHub.dto.*;
import com.br.alura.Challenge_ForumHub.infra.exception.ValidacaoException;
import com.br.alura.Challenge_ForumHub.model.Usuario;
import com.br.alura.Challenge_ForumHub.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario cadastrar(DadosCadastroUsuario dados) {
        if (repository.findByLogin(dados.login()) != null) {
            throw new ValidacaoException("Login já cadastrado.");
        }
        var senhaCriptografada = passwordEncoder.encode(dados.senha());
        var usuario = new Usuario(dados, senhaCriptografada);
        repository.save(usuario);
        return usuario;
    }

    public Page<DadosDetalhamentoUsuario> listar(Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(DadosDetalhamentoUsuario::new);
    }

    public Usuario detalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
    }

    @Transactional
    public Usuario atualizar(Long id, DadosAtualizacaoUsuario dados) {
        var usuario = detalhar(id);
        String novaSenha = null;
        if (dados.senha() != null) {
            novaSenha = passwordEncoder.encode(dados.senha());
        }
        usuario.atualizarInformacoes(dados, novaSenha);
        return usuario;
    }

    @Transactional
    public void deletar(Long id) {
        var usuario = detalhar(id);
        usuario.excluir();
    }
}