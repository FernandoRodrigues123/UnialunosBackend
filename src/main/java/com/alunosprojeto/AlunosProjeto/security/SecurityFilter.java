package com.alunosprojeto.AlunosProjeto.security;

import com.alunosprojeto.AlunosProjeto.domain.models.Estudante;
import com.alunosprojeto.AlunosProjeto.domain.repository.EstudanteRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenServices tokenServices;

    @Autowired
    private EstudanteRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = getToken(request);

        if (tokenJWT != null) {

            String subject = tokenServices.getSubject(tokenJWT);
            Estudante estudante = repository.getByUsuarioEstudanteLogin(subject);
            UserDetails usuarioEstudanteDetails = estudante.getUsuarioEstudante();
            var auticacao = new UsernamePasswordAuthenticationToken(usuarioEstudanteDetails, null, usuarioEstudanteDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auticacao);
        }


        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
                return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }
}
