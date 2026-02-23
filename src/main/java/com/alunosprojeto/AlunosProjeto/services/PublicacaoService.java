package com.alunosprojeto.AlunosProjeto.services;

import com.alunosprojeto.AlunosProjeto.Api.dto.publicacao.PublicacaoDTOAtualizar;
import com.alunosprojeto.AlunosProjeto.domain.models.Estudante;
import com.alunosprojeto.AlunosProjeto.domain.models.Publicacao;
import com.alunosprojeto.AlunosProjeto.domain.repository.PublicacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicacaoService {
    @Autowired
    private PublicacaoRepository repository;
    @Autowired
    private EstudanteServices estudanteServices;

    @Autowired
    private LikeService likeService;
    @Transactional
    public Publicacao publicar(Publicacao publicacao, String login){
        Estudante estudante = estudanteServices.buscarEstudantePorLogin(login);
        publicacao.setEstudante(estudante);
        return repository.save(publicacao);
    }

    public Page<Publicacao> buscarTodasPublicacoes(Pageable paginacao) {


        return repository.findAll(paginacao);
    }

    @Transactional
    public Publicacao atualizarPublicacao(Long id, PublicacaoDTOAtualizar dadosNovos) {
        Publicacao publicacao = repository.getReferenceById(id);
        publicacao.atualizar(dadosNovos);

        return publicacao;
    }

    public void deletarPublicacao(Long id) {
        repository.deleteById(id);
    }

    public Publicacao buscaEstudantesEAdcionaAPublicacao(Publicacao publicacao){
        List<Estudante> estudantes = likeService.buscaEstudantesLikes(publicacao.getId());
        publicacao.setEstudantesLikes(estudantes);
        publicacao.setQuantidadeLikes(Long.valueOf(estudantes.size()));
        return publicacao;
    }

}
