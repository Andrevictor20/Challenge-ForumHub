package com.br.alura.Challenge_ForumHub.model;

import com.br.alura.Challenge_ForumHub.dto.DadosAtualizacaoUsuario;
import com.br.alura.Challenge_ForumHub.dto.DadosCadastroUsuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String login;
    private String senha;
    private Boolean ativo;
    @OneToMany(mappedBy = "autor")
    private List<Resposta> respostas = new ArrayList<>();

    public Usuario(DadosCadastroUsuario dados, String senhaCriptografada) {
        this.nome = dados.nome();
        this.login = dados.login();
        this.senha = senhaCriptografada;
        this.ativo = true;
    }

    public void atualizarInformacoes(DadosAtualizacaoUsuario dados, String novaSenhaCriptografada) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (novaSenhaCriptografada != null) {
            this.senha = novaSenhaCriptografada;
        }
    }

    public void excluir() {
        this.ativo = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.ativo;

    }

}
