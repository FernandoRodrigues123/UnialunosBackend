package com.alunosprojeto.AlunosProjeto.verificacoes.VerificacoesEstudante;

import com.alunosprojeto.AlunosProjeto.Api.dto.estudante.EstudanteDTO;
import com.alunosprojeto.AlunosProjeto.domain.repository.EstudanteRepository;
import com.alunosprojeto.AlunosProjeto.exception.EmUsoException;
import org.springframework.stereotype.Component;

@Component
public class VerificaDuplicidadeLogin implements VerificaEstudante {
    @Override
    public void verificar(EstudanteDTO dados, EstudanteRepository estudanteRepository) {
        if (estudanteRepository.existsByUsuarioEstudanteLogin(dados.usuarioEstudanteDTO().login())) {
            throw new EmUsoException("login ja em uso");
        }
    }
}
