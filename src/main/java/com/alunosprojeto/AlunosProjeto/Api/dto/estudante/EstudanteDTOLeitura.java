package com.alunosprojeto.AlunosProjeto.Api.dto.estudante;

import com.alunosprojeto.AlunosProjeto.Api.dto.publicacao.PublicacaoDTOLeituraSemEstudante;
import com.alunosprojeto.AlunosProjeto.domain.models.Estudante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.util.stream.Collectors;

public record EstudanteDTOLeitura(
        Long id,
        String nome,
        String urlImagem,

        String email,

        LocalDate dataDeNascimento,
        String areaDeEstudo,

        Page<PublicacaoDTOLeituraSemEstudante> publicacoes

) {
    public EstudanteDTOLeitura(Estudante estudante){
        this(estudante.getId(), estudante.getNome(), estudante.getUrlImagem(), estudante.getEmail(), estudante.getDataDeNascimento(), estudante.getAreaDeEstudo(),new PageImpl<>(estudante.getPublicacoes().stream().map(PublicacaoDTOLeituraSemEstudante::new).collect(Collectors.toList())));
    }
}
