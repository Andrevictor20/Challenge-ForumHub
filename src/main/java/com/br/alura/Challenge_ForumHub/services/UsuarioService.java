package com.br.alura.Challenge_ForumHub.services;

import com.br.alura.Challenge_ForumHub.dto.*;
import com.br.alura.Challenge_ForumHub.infra.exception.ValidacaoException;
import com.br.alura.Challenge_ForumHub.model.Usuario;
import com.br.alura.Challenge_ForumHub.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @CacheEvict(value = {"usuarios", "usuarios-lista"}, allEntries = true)
    public Usuario cadastrar(DadosCadastroUsuario dados) {
        if (repository.findByLogin(dados.login()) != null) {
            throw new ValidacaoException("Login já cadastrado.");
        }
        var senhaCriptografada = passwordEncoder.encode(dados.senha());
        var usuario = new Usuario(dados, senhaCriptografada);
        repository.save(usuario);
        return usuario;
    }

    @Cacheable(value = "usuarios-lista", key = "#paginacao.pageNumber + '-' + #paginacao.pageSize")
    public Page<DadosDetalhamentoUsuario> listar(Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(DadosDetalhamentoUsuario::new);
    }

    @Cacheable(value = "usuarios", key = "#id")
    public Usuario detalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
    }

    @Cacheable(value = "usuarios-login", key = "#login")
    public Usuario detalharPorLogin(String login) {
        return repository.getReferenceByLogin(login);
    }

    @Transactional
    @CacheEvict(value = {"usuarios", "usuarios-login", "usuarios-lista"}, allEntries = true)
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
    @CacheEvict(value = {"usuarios", "usuarios-login", "usuarios-lista"}, allEntries = true)
    public void deletar(Long id) {
        var usuario = detalhar(id);
        usuario.excluir();
    }
}