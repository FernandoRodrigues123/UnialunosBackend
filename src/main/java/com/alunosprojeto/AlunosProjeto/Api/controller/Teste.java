package com.alunosprojeto.AlunosProjeto.Api.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/teste")
@RestController
public class Teste {
    @GetMapping("/hello")
    public ResponseEntity<String> teste(){
        return ResponseEntity.ok("Hello");
    }
}
