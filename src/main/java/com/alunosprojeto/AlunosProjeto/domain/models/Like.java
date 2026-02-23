package com.alunosprojeto.AlunosProjeto.domain.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "likes")
@Entity(name = "Like")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Like   {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "publicacao_id", nullable = false)
    private Publicacao publicacao;

    @ManyToOne
    @JoinColumn(name = "estudante_id", nullable = false)
    private Estudante estudante;

    public Like(Publicacao publicacao, Estudante estudante) {
        this.publicacao = publicacao;
        this.estudante = estudante;
    }

    @Override
    public String toString() {
        return "id : " + this.id + " estudante_id: " + estudante.getId();
    }
}
