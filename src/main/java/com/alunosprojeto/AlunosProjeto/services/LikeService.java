package com.alunosprojeto.AlunosProjeto.services;

import com.alunosprojeto.AlunosProjeto.domain.models.Estudante;
import com.alunosprojeto.AlunosProjeto.domain.models.Like;
import com.alunosprojeto.AlunosProjeto.domain.models.Publicacao;
import com.alunosprojeto.AlunosProjeto.domain.repository.EstudanteRepository;
import com.alunosprojeto.AlunosProjeto.domain.repository.LikeRepository;
import com.alunosprojeto.AlunosProjeto.domain.repository.PublicacaoRepository;
import com.alunosprojeto.AlunosProjeto.util.RespostaDeServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private EstudanteRepository estudanteRepository;
    @Autowired
    private PublicacaoRepository publicacaoRepository;

    public RespostaDeServico curtirPublicacao(Long idPublicacao, String login) {
        // Verifique se a publicação existe
        Publicacao pub = publicacaoRepository.findById(idPublicacao)
                .orElseThrow(() -> new RuntimeException("Publicação não encontrada"));

        // Obtenha o estudante
        Estudante estudante = estudanteRepository.getByUsuarioEstudanteLogin(login);

        // Verifique se o like já existe
        Like likeExistente = likeRepository.findByPublicacaoIdAndEstudanteId(idPublicacao, estudante.getId());

        if (likeExistente != null) {
            // Se o like já existe, remova-o
            likeRepository.delete(likeExistente);
            return RespostaDeServico.DELETADO;

        } else {
            // Caso contrário, crie um novo like
            Like like = new Like(pub, estudante);
            likeRepository.save(like);
            return RespostaDeServico.CRIADO;
        }


    }

    public List<Estudante> buscaEstudantesLikes(Long id) {

        List<Estudante> estudantes = new ArrayList<>();

        List<Like> likes = likeRepository.findAllByPublicacaoId(id);
        likes.stream().forEach(like -> {
            estudantes.add(like.getEstudante());
        });
        return estudantes;
    }

    public Boolean existeEsteLike(Long idPublicacao, String login) {
        Publicacao pub = publicacaoRepository.findById(idPublicacao)
                .orElseThrow(() -> new RuntimeException("Publicação não encontrada"));
        Estudante estudante = estudanteRepository.getByUsuarioEstudanteLogin(login);
        return likeRepository.existsByPublicacaoIdAndEstudanteId(idPublicacao,estudante.getId());

    }
}
