package com.alunosprojeto.AlunosProjeto.Api.controller;

import com.alunosprojeto.AlunosProjeto.Api.dto.publicacao.PublicacaoDTOAtualizar;
import com.alunosprojeto.AlunosProjeto.Api.dto.publicacao.PublicacaoDTOLeitura;
import com.alunosprojeto.AlunosProjeto.Api.dto.publicacao.PublicacaoDTOLeituraSemLike;
import com.alunosprojeto.AlunosProjeto.domain.models.Publicacao;
import com.alunosprojeto.AlunosProjeto.services.LikeService;
import com.alunosprojeto.AlunosProjeto.services.PublicacaoService;
import com.alunosprojeto.AlunosProjeto.services.UsuarioEstudanteServices;
import com.alunosprojeto.AlunosProjeto.util.RespostaDeServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publicacoes")
public class PublicacaoController {
    @Autowired
    private PublicacaoService service;

    @Autowired
    private LikeService likeService;

    @PostMapping("/{login}")
    public ResponseEntity<PublicacaoDTOLeituraSemLike> publicar(@RequestBody Publicacao publicacao, @PathVariable String login) {
        boolean validacao = UsuarioEstudanteServices.verificaUsuarioEstaTentandoAcessarProprioPerfilPeloLogin(login);
        if (validacao) {

            return ResponseEntity.ok(new PublicacaoDTOLeituraSemLike(service.publicar(publicacao, login)));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<PublicacaoDTOLeitura>> buscaTodos(@PageableDefault(size = 10, sort = {"titulo"}) Pageable paginacao) {
        Page<Publicacao> slaDificilEmTestaSaporraDepois = service.buscarTodasPublicacoes(paginacao).map(publicacao -> {
            publicacao.setEstudantesLikes(likeService.buscaEstudantesLikes(publicacao.getId()));
            publicacao.setQuantidadeLikes(Long.valueOf(publicacao.getEstudantesLikes().size()));
            Publicacao publicacao1 = publicacao;
            return publicacao1;
        });
        return ResponseEntity.ok(slaDificilEmTestaSaporraDepois.map(PublicacaoDTOLeitura::new));
    }


    @PutMapping("/{login}/{id}")
    public ResponseEntity<PublicacaoDTOLeitura> atualizarPublicacao(@PathVariable(name = "login") String login, @PathVariable(name = "id") Long id, @RequestBody PublicacaoDTOAtualizar dadosNovos) {
        boolean validacao = UsuarioEstudanteServices.verificaUsuarioEstaTentandoAcessarProprioPerfilPeloLogin(login);
        if (validacao) {
            System.out.println(dadosNovos);
            return ResponseEntity.ok(new PublicacaoDTOLeitura(service.atualizarPublicacao(id, dadosNovos)));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/{login}/{id}")
    public ResponseEntity deletarPublicacao(@PathVariable(name = "login") String login, @PathVariable(name = "id") Long id) {
        boolean validacao = UsuarioEstudanteServices.verificaUsuarioEstaTentandoAcessarProprioPerfilPeloLogin(login);
        if (validacao) {
            service.deletarPublicacao(id);
            return ResponseEntity.noContent().build();

        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/curtir/{id}")
    public ResponseEntity<HttpStatus> curtiPublicacao(@PathVariable(name = "id") Long idPublicacao, @RequestParam(name = "login") String login) {
        boolean validacao = UsuarioEstudanteServices.verificaUsuarioEstaTentandoAcessarProprioPerfilPeloLogin(login);
        if (validacao) {
            RespostaDeServico respostaDeServico = likeService.curtirPublicacao(idPublicacao, login);

            if (respostaDeServico == RespostaDeServico.CRIADO) {
                return ResponseEntity.ok(HttpStatus.CREATED);
            }else{
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }
    @PostMapping("/curtir/{id}/liked")
    public ResponseEntity<Boolean> isLiked(@PathVariable(name = "id") Long idPublicacao, @RequestParam(name = "login") String login) {
        boolean validacao = UsuarioEstudanteServices.verificaUsuarioEstaTentandoAcessarProprioPerfilPeloLogin(login);
        if (validacao) {
            Boolean liked = likeService.existeEsteLike(idPublicacao, login);
            return  ResponseEntity.ok(liked);

        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        }

    }


}
