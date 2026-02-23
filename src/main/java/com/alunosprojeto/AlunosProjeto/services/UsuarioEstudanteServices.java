package com.alunosprojeto.AlunosProjeto.services;


import com.alunosprojeto.AlunosProjeto.domain.models.UsuarioEstudante;
import com.alunosprojeto.AlunosProjeto.domain.repository.EstudanteRepository;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@EqualsAndHashCode()
public class UsuarioEstudanteServices implements UserDetailsService {
    @Autowired
    private EstudanteRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.getByUsuarioEstudanteLogin(username).getUsuarioEstudante();
    }

    public static boolean verificaUsuarioEstaTentandoAcessarProprioPerfilPeloLogin(String loginRecebido){
        String login = pegaUsuarioEstudante().getLogin();//pega os dados do usuario altenticado,  e  pega o id do mesmo

        if (loginRecebido.equals(login)) return true;
        else return false;
    }

    public static UsuarioEstudante pegaUsuarioEstudante(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

       return ((UsuarioEstudante) authentication.getPrincipal());

    }
}
