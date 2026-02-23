package com.alunosprojeto.AlunosProjeto.Api.dto.publicacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PublicacaoDTOAtualizar(

        String titulo,


        String corpo,
        String referencia
        ) {
}
