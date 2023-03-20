package com.example.demo.repository;

import com.example.demo.Enum.Situacao;
import com.example.demo.dto.CategoriaDTO;
import com.example.demo.dto.LivroDTO;
import com.example.demo.dto.LocacaoDTO;
import com.example.demo.dto.PessoaDTO;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@AllArgsConstructor
public class BaseConfig {

    public static List<CategoriaDTO> dbCategoria = Collections.synchronizedList(
            new ArrayList<>() {{
                add( new CategoriaDTO("Tecnologia"));
                add( new CategoriaDTO("Romance"));
                add( new CategoriaDTO("Drama"));
            }}
    );

    public static List<LivroDTO> dbLivro = Collections.synchronizedList(
            new ArrayList<>() {{
                add( new LivroDTO(0L,"Domine o Java", "Atlas", dbCategoria.get(0), Situacao.LOCADO ));
                add( new LivroDTO(1L,"Clean Code", "Person", dbCategoria.get(0), Situacao.DISPONIVEL ));
                add( new LivroDTO(2L,"Design Pattern", "Person", dbCategoria.get(0), Situacao.DISPONIVEL ));
            }}
    );

    public static List<PessoaDTO> dbPessoa = Collections.synchronizedList(
            new ArrayList<>() {{
                add( new PessoaDTO(0L,"11111111111","Jose Junior", "jj@gmail.com","(11)8524-8521"));
                add( new PessoaDTO(1L,"22222222222","Helena Santos", "helena@gmail.com","(11)8585-8555"));
                add( new PessoaDTO(2L,"33333333333","Juliana Cardoso", "juliana@gmail.com","(11)8524-8741"));

            }}
    );

    private static List<LivroDTO> dbLivro1 = Collections.synchronizedList(
            new ArrayList<>() {{
                add( dbLivro.get(0));
            }}
    );

    public static List<LocacaoDTO> dbLocacao = Collections.synchronizedList(
            new ArrayList<>() {{
                add( new LocacaoDTO(0L, dbPessoa.get(0), LocalDate.now(), LocalDate.now().plusDays(7), false, dbLivro1));
            }});
}
