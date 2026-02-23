package com.alunosprojeto.AlunosProjeto.domain.repository;

import com.alunosprojeto.AlunosProjeto.domain.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {

    List<Like> findAllByPublicacaoId(Long publicacaoId);
    boolean existsByPublicacaoIdAndEstudanteId(Long publicacaoId, Long estudanteId);
    Like findByPublicacaoIdAndEstudanteId(Long publicacaoId, Long estudanteId);
}
