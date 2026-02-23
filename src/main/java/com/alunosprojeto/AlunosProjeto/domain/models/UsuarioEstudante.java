package com.alunosprojeto.AlunosProjeto.domain.models;

import com.alunosprojeto.AlunosProjeto.Api.dto.estudante.UsuarioEstudanteDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"login", "senha"})
@Embeddable
public class UsuarioEstudante implements UserDetails {


    private String login;

    private String senha;

    public UsuarioEstudante(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public UsuarioEstudante(UsuarioEstudanteDTO usuarioDados) {
        this.login = usuarioDados.login();
        this.senha = usuarioDados.senha();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ESTUDANTE"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
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
        return true;
    }

    public void atualizar(UsuarioEstudanteDTO usuarioEstudanteDTO) {
        if (usuarioEstudanteDTO.login() != null) {
            this.login = usuarioEstudanteDTO.login();
 ;
        }
        if (usuarioEstudanteDTO.senha() != null) {
            this.senha = usuarioEstudanteDTO.senha();
        }
    }
}
