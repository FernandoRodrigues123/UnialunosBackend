package com.alunosprojeto.AlunosProjeto.verificacoes.VerificacoesEstudante;

import com.alunosprojeto.AlunosProjeto.Api.dto.estudante.EstudanteDTO;
import com.alunosprojeto.AlunosProjeto.domain.repository.EstudanteRepository;
import com.alunosprojeto.AlunosProjeto.exception.DadosIncorretos;
import org.springframework.stereotype.Component;

@Component
public class VerificaSeLoginNaoENulo implements VerificaEstudante {
    @Override
    public void verificar(EstudanteDTO dados, EstudanteRepository estudanteRepository) {
        if(dados.usuarioEstudanteDTO().login() == null){
            throw new DadosIncorretos("login nulo");
        }
    }
}
