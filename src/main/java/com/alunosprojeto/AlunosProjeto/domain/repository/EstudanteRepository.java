package com.alunosprojeto.AlunosProjeto.domain.repository;

import com.alunosprojeto.AlunosProjeto.domain.models.Estudante;
import com.alunosprojeto.AlunosProjeto.domain.models.UsuarioEstudante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudanteRepository extends JpaRepository<Estudante,Long> {
    Estudante findByEmail(String email);

    boolean existsByEmail(String email);
    @Query("select e from Estudante e where e.nome like %:nome%")
    Page<Estudante> findAllByNome(Pageable pageable,@Param("nome") String nome);

    void deleteByUsuarioEstudante(UsuarioEstudante usuarioEstudante);


    boolean existsByUsuarioEstudanteLogin(String login);

    Estudante getByUsuarioEstudanteLogin(String login );

    @Query("SELECT e FROM Estudante e WHERE e.usuarioEstudante.login = :login AND e.usuarioEstudante.senha = :senha")
    Estudante getByUsuarioEstudanteLoginAndSenha(@Param("login") String login, @Param("senha")String senha);


}
