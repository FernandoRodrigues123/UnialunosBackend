package com.alunosprojeto.AlunosProjeto.Api.dto.publicacao;

import com.alunosprojeto.AlunosProjeto.Api.dto.estudante.EstudanteDTOLeituraSemPublicacaoEUsuario;
import com.alunosprojeto.AlunosProjeto.domain.models.Publicacao;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public record PublicacaoDTOLeituraSemLike (
        Long id,


        String titulo,

        String corpo,
        String referencia,
        @JsonProperty(value = "estudante")
        EstudanteDTOLeituraSemPublicacaoEUsuario estudanteDTOLeitura
){
    public PublicacaoDTOLeituraSemLike(Publicacao publicacao){
        this(publicacao.getId(), publicacao.getTitulo(),publicacao.getCorpo(), publicacao.getReferencias(),new EstudanteDTOLeituraSemPublicacaoEUsuario( publicacao.getEstudante()));
    }

}
