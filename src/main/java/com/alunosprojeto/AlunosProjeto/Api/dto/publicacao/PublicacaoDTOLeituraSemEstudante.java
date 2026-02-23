package com.alunosprojeto.AlunosProjeto.Api.dto.publicacao;

import com.alunosprojeto.AlunosProjeto.Api.dto.estudante.EstudanteDTOLeitura;
import com.alunosprojeto.AlunosProjeto.domain.models.Publicacao;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PublicacaoDTOLeituraSemEstudante (
    Long id,
    String titulo,

    String corpo,
    String referencia
){
    public PublicacaoDTOLeituraSemEstudante(Publicacao publicacao){
        this(publicacao.getId(), publicacao.getTitulo(),publicacao.getCorpo(), publicacao.getReferencias());
    }
}
