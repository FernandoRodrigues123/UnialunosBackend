package com.alunosprojeto.AlunosProjeto.Api.dto.estudante;

import com.alunosprojeto.AlunosProjeto.domain.models.Estudante;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public record EstudanteDTODetalhes(


        Long id,
        String nome,

        String urlImagem,
        String email,

        LocalDate dataDeNascimento,
        String areaDeEstudo,

        @JsonProperty(value = "usuario")
        UsuarioEstudanteDTO usuarioEstudanteDTO

) {
    public EstudanteDTODetalhes(Estudante estudante){
        this(estudante.getId(),estudante.getUrlImagem(), estudante.getNome(),estudante.getEmail(), estudante.getDataDeNascimento(), estudante.getAreaDeEstudo(),new UsuarioEstudanteDTO(estudante.getUsuarioEstudante()));
    }


}
