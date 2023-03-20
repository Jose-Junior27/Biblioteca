package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class PessoaDTO {
    @Getter
    private Long id;
    @Getter
    private String CPF;
    @Getter
    private String nome;
    @Getter
    private String email;
    @Getter
    private String fone;
}
