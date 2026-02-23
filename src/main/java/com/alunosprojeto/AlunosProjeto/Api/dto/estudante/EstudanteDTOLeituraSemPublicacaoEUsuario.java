package com.alunosprojeto.AlunosProjeto.Api.dto.estudante;

import com.alunosprojeto.AlunosProjeto.domain.models.Estudante;

import java.time.LocalDate;

public record EstudanteDTOLeituraSemPublicacaoEUsuario(
        Long id,
        String nome,
        String urlImagem,

        String email,

        LocalDate dataDeNascimento,
        String areaDeEstudo
) {
    public EstudanteDTOLeituraSemPublicacaoEUsuario(Estudante estudante){
        this(estudante.getId(), estudante.getNome(),estudante.getUrlImagem(),estudante.getEmail(), estudante.getDataDeNascimento(), estudante.getAreaDeEstudo());
    }
}
