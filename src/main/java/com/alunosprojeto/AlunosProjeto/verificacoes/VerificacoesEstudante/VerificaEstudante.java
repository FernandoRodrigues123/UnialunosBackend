package com.alunosprojeto.AlunosProjeto.verificacoes.VerificacoesEstudante;

import com.alunosprojeto.AlunosProjeto.Api.dto.estudante.EstudanteDTO;
import com.alunosprojeto.AlunosProjeto.Api.dto.estudante.UsuarioEstudanteDTO;
import com.alunosprojeto.AlunosProjeto.domain.models.Estudante;
import com.alunosprojeto.AlunosProjeto.domain.repository.EstudanteRepository;

public interface VerificaEstudante {
    void verificar(EstudanteDTO dados, EstudanteRepository estudanteRepository);
}
