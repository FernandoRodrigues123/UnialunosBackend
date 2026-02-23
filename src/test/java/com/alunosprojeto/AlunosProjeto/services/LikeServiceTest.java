package com.alunosprojeto.AlunosProjeto.services;

import com.alunosprojeto.AlunosProjeto.domain.models.Like;
import com.alunosprojeto.AlunosProjeto.domain.repository.LikeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LikeServiceTest {
    @Autowired
    private LikeRepository likeRepository;
    @Test
    void buscaEstudantesLikes() {


        List<Like> likes = likeRepository.findAllByPublicacaoId(82l);
        likes.forEach(l -> System.out.println(l.toString()));
    }
}