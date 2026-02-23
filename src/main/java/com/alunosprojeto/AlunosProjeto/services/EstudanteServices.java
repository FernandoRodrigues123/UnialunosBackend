package com.alunosprojeto.AlunosProjeto.services;

import com.alunosprojeto.AlunosProjeto.Api.dto.estudante.EstudanteDTO;
import com.alunosprojeto.AlunosProjeto.Api.dto.estudante.EstudanteDTODetalhes;
import com.alunosprojeto.AlunosProjeto.Api.dto.estudante.EstudanteDTOLeituraSemPublicacaoEUsuario;
import com.alunosprojeto.AlunosProjeto.Api.dto.estudante.UsuarioEstudanteDTO;
import com.alunosprojeto.AlunosProjeto.domain.models.Estudante;
import com.alunosprojeto.AlunosProjeto.domain.models.UsuarioEstudante;
import com.alunosprojeto.AlunosProjeto.domain.repository.EstudanteRepository;
import com.alunosprojeto.AlunosProjeto.domain.repository.PublicacaoRepository;
import com.alunosprojeto.AlunosProjeto.exception.DadosIncorretos;
import com.alunosprojeto.AlunosProjeto.exception.EmUsoException;

import com.alunosprojeto.AlunosProjeto.verificacoes.VerificacoesEstudante.VerificaEstudante;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudanteServices {

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Autowired
    private List<VerificaEstudante> verificacoes;
    @Autowired
    private PublicacaoRepository publicacaoRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Transactional
    public Estudante cadastrarEstudante(EstudanteDTO dados) {
        verificacoes.forEach(v -> v.verificar(dados, estudanteRepository));

        String senhaCript = encoder.encode(dados.usuarioEstudanteDTO().senha());

        Estudante estudante = new Estudante(dados);
        estudante.getUsuarioEstudante().setSenha(senhaCript);
        estudanteRepository.save(estudante);

        return estudante;
    }




    public Page<Estudante> buscarTodosEstudantes(Pageable paginacao) {
        return estudanteRepository.findAll(paginacao);
    }

    @Transactional
    public Estudante atualizarCadastroDeEstudante(EstudanteDTOLeituraSemPublicacaoEUsuario dados, String login) {
        Estudante estudante = estudanteRepository.getByUsuarioEstudanteLogin(login);

        boolean mesmoEmail = estudante.getEmail().equals(dados.email());

        if (mesmoEmail == false) {
            if (estudanteRepository.existsByEmail(dados.email())) throw new EmUsoException("email ja em uso");
        }

        estudante.atualizar(dados);


        return estudante;
    }

    @Transactional
    public void deletarCadastroEstudante(UsuarioEstudanteDTO usuarioEstudanteDTO) {

        Estudante estudante = estudanteRepository.getByUsuarioEstudanteLogin(usuarioEstudanteDTO.login());
        boolean validacao = encoder.matches(usuarioEstudanteDTO.senha(), estudante.getUsuarioEstudante().getSenha());

        if(validacao) {
            publicacaoRepository.deleteByEstudante(estudante.getId());
            estudanteRepository.deleteById(estudante.getId());
        }
        else throw new DadosIncorretos("login ou senha incorretos ");
    }

    public Estudante buscarEstudantePorEmail(String email) {
        return estudanteRepository.findByEmail(email);
    }

    public Page<Estudante> buscarEstudantePorNome(Pageable pageable, String nome) {
        return estudanteRepository.findAllByNome(pageable, nome);
    }

    public Estudante buscarEstudantePorLogin( String login) {
        Estudante es = estudanteRepository.getByUsuarioEstudanteLogin(login);
        return es;
    }

    public Estudante buscaPorId(Long id){
        return estudanteRepository.getReferenceById(id);
    }

}
