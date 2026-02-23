package com.alunosprojeto.AlunosProjeto.domain.repository;

import com.alunosprojeto.AlunosProjeto.domain.models.Publicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {
    @Modifying
    @Query("DELETE FROM Publicacao p WHERE p.estudante.id = :idEstudante")
    void deleteByEstudante(@Param("idEstudante") Long idEstudante);
}
