package com.alunosprojeto.AlunosProjeto.verificacoes.VerificacoesEstudante;

import com.alunosprojeto.AlunosProjeto.Api.dto.estudante.EstudanteDTO;
import com.alunosprojeto.AlunosProjeto.domain.repository.EstudanteRepository;
import com.alunosprojeto.AlunosProjeto.exception.EmUsoException;
import org.springframework.stereotype.Component;

@Component
public class VerificaDuplicidadeDeEmail implements VerificaEstudante{
    @Override
    public void verificar(EstudanteDTO dados, EstudanteRepository estudanteRepository) {
        if (estudanteRepository.existsByEmail(dados.email())) {
            throw new EmUsoException("email ja em uso");
        }
    }
}
