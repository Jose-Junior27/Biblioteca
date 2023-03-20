package com.example.demo.dto;

import com.example.demo.Enum.Situacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor

public class LivroDTO {
    @Getter
    private Long id;
    @Getter
    private String nome;
    @Getter
    private String editora;
    @Getter
    @Autowired
    private CategoriaDTO categoria;
    @Getter
    private Situacao situacao;
}
