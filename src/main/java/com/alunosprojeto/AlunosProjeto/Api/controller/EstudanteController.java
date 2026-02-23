package com.alunosprojeto.AlunosProjeto.Api.controller;

import com.alunosprojeto.AlunosProjeto.Api.dto.*;
import com.alunosprojeto.AlunosProjeto.Api.dto.estudante.*;
import com.alunosprojeto.AlunosProjeto.domain.models.Estudante;
import com.alunosprojeto.AlunosProjeto.domain.models.UsuarioEstudante;
import com.alunosprojeto.AlunosProjeto.security.TokenServices;
import com.alunosprojeto.AlunosProjeto.services.EstudanteServices;
import com.alunosprojeto.AlunosProjeto.services.UsuarioEstudanteServices;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/estudantes")
public class EstudanteController {

    @Autowired
    private EstudanteServices services;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenServices tokenServices;

    @PostMapping(value = "/cadastro", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<EstudanteDTODetalhes> cadastrarEstudante(
            @RequestPart("dados") @Valid EstudanteDTO dados,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem,
            UriComponentsBuilder uriBuilder) {

        Estudante estudante = services.cadastrarEstudante(dados);

        if (imagem != null && !imagem.isEmpty()) {
            System.out.println("Imagem recebida: " + imagem.getOriginalFilename());
            // depois vamos mandar pro S3 aqui
        }

        var uri = uriBuilder.path("/estudante/{id}")
                .buildAndExpand(estudante.getId())
                .toUri();

        return ResponseEntity.created(uri)
                .body(new EstudanteDTODetalhes(estudante));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UsuarioEstudanteDTO dto) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());
        Authentication authenticate = manager.authenticate(token);

        String tokenJWT = tokenServices.gerarToken((UsuarioEstudante) authenticate.getPrincipal());

        return ResponseEntity.ok(new TokenDTO(tokenJWT));
    }

    @GetMapping
    public ResponseEntity<Page<EstudanteDTOLeitura>> buscarTodosEstudantes(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return ResponseEntity.ok(services.buscarTodosEstudantes(paginacao).map(EstudanteDTOLeitura::new));
    }

    @GetMapping("/{login}")
    public ResponseEntity<EstudanteDTOLeitura> buscaEstudantePorLogin(@PathVariable String login) {

        boolean validacao = UsuarioEstudanteServices.verificaUsuarioEstaTentandoAcessarProprioPerfilPeloLogin(login);
        if (validacao) {
            return ResponseEntity.ok(new EstudanteDTOLeitura(services.buscarEstudantePorLogin(login)));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/s/{nome}")
    public ResponseEntity<Page<EstudanteDTOLeituraSemPublicacaoEUsuario>> buscaTodosPorNome(@PageableDefault(size = 10) Pageable paginacao, @PathVariable String nome) {

        Page<Estudante> estudantes = services.buscarEstudantePorNome(paginacao, nome);
        Page<EstudanteDTOLeituraSemPublicacaoEUsuario> dto = estudantes.map(EstudanteDTOLeituraSemPublicacaoEUsuario::new);
        return ResponseEntity.ok(dto);

    }

    @GetMapping("/perfil/{id}")
    public ResponseEntity<EstudanteDTOLeitura> buscaEstudantePorId(@PathVariable Long id) {
        Estudante estudante = services.buscaPorId(id);
        return ResponseEntity.ok(new EstudanteDTOLeitura(estudante));
    }

    @PutMapping("/{login}")
    @Transactional
    public ResponseEntity atualizarCadastroDeEstudante(@RequestBody @Valid EstudanteDTOLeituraSemPublicacaoEUsuario dadosNovos, @PathVariable String login) {
        boolean validacao = UsuarioEstudanteServices.verificaUsuarioEstaTentandoAcessarProprioPerfilPeloLogin(login);
        if (validacao) {
            return ResponseEntity.ok(services.atualizarCadastroDeEstudante(dadosNovos, login));
        }
        System.out.println("deu pau aq");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity deletarCadastroEstudante(@RequestBody @Valid UsuarioEstudanteDTO usuarioDados) {
        boolean validacao = UsuarioEstudanteServices.verificaUsuarioEstaTentandoAcessarProprioPerfilPeloLogin(usuarioDados.login());
        if (validacao) {
            services.deletarCadastroEstudante(usuarioDados);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}



