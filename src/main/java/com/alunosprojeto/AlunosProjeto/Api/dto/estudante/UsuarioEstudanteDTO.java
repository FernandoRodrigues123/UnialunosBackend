package com.alunosprojeto.AlunosProjeto.Api.dto.estudante;

import com.alunosprojeto.AlunosProjeto.domain.models.UsuarioEstudante;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioEstudanteDTO(

        @NotBlank
        @NotNull

        String login,
        @NotBlank
        @NotNull
        String senha
) {
        public UsuarioEstudanteDTO(UsuarioEstudante usuarioEstudante) {
                this(usuarioEstudante.getLogin(), usuarioEstudante.getSenha());
        }

}
