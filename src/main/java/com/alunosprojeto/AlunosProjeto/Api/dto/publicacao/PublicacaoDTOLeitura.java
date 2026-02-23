package com.alunosprojeto.AlunosProjeto.Api.dto.publicacao;

import com.alunosprojeto.AlunosProjeto.Api.dto.estudante.EstudanteDTOLeituraSemPublicacaoEUsuario;
import com.alunosprojeto.AlunosProjeto.domain.models.Like;
import com.alunosprojeto.AlunosProjeto.domain.models.Publicacao;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public record PublicacaoDTOLeitura(
        Long id,


        String titulo,

        String corpo,
        String referencia,
        @JsonProperty(value = "estudante")
        EstudanteDTOLeituraSemPublicacaoEUsuario estudanteDTOLeitura,
        List<EstudanteDTOLeituraSemPublicacaoEUsuario> estudantesLike,

        Long quantidadeLikes
   ) {
   public PublicacaoDTOLeitura(Publicacao publicacao){
      this(publicacao.getId(), publicacao.getTitulo(),publicacao.getCorpo(), publicacao.getReferencias(),new EstudanteDTOLeituraSemPublicacaoEUsuario( publicacao.getEstudante()),new ArrayList<>(publicacao.getEstudantesLikes().stream().map(EstudanteDTOLeituraSemPublicacaoEUsuario::new).toList()), publicacao.getQuantidadeLikes());
   }
}
