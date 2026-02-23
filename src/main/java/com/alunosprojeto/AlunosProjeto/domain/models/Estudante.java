package com.alunosprojeto.AlunosProjeto.domain.models;

import com.alunosprojeto.AlunosProjeto.Api.dto.estudante.EstudanteDTO;
import com.alunosprojeto.AlunosProjeto.Api.dto.estudante.EstudanteDTODetalhes;
import com.alunosprojeto.AlunosProjeto.Api.dto.estudante.EstudanteDTOLeituraSemPublicacaoEUsuario;
import com.alunosprojeto.AlunosProjeto.Api.dto.estudante.UsuarioEstudanteDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Table(name = "estudantes")
@Entity(name = "Estudante")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Estudante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String urlImagem;

    private String email;

    @Column(name = "data_de_nascimento")
    private LocalDate dataDeNascimento;


    private String areaDeEstudo;

    @Embedded
    private UsuarioEstudante usuarioEstudante;

    @OneToMany(mappedBy = "estudante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Publicacao> publicacoes;

    @ManyToMany(mappedBy = "estudantesLikes")
    private List<Publicacao> publicacoesCurtidas;


    public Estudante(EstudanteDTO estudanteDTO) {
        this.nome = estudanteDTO.nome();
        this.urlImagem  = estudanteDTO.urlImagem();
        this.dataDeNascimento = estudanteDTO.dataDeNascimento();
        this.areaDeEstudo = estudanteDTO.areaDeEstudo();
        this.email = estudanteDTO.email();
        UsuarioEstudanteDTO usuarioEstudanteDTO = estudanteDTO.usuarioEstudanteDTO();
        this.usuarioEstudante = new UsuarioEstudante(usuarioEstudanteDTO.login(), usuarioEstudanteDTO.senha());
    }

    public void atualizar(EstudanteDTOLeituraSemPublicacaoEUsuario estudanteDTO) {
        if (estudanteDTO.nome() != null && estudanteDTO.nome().isBlank() != true) this.nome = estudanteDTO.nome();
        if (estudanteDTO.dataDeNascimento() != null )
            this.dataDeNascimento = estudanteDTO.dataDeNascimento();
        if (estudanteDTO.areaDeEstudo() != null && estudanteDTO.areaDeEstudo().isBlank() != true)
            this.areaDeEstudo = estudanteDTO.areaDeEstudo();
        if (estudanteDTO.email() != null && estudanteDTO.email().isBlank() != true) this.email = estudanteDTO.email();
        if(estudanteDTO.urlImagem() !=null && estudanteDTO.urlImagem().isBlank() != true){
            this.urlImagem = estudanteDTO.urlImagem();
        }
    }


}
