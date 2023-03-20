package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocacaoDTO {
    @Getter
    private Long id;
    @Getter
    private PessoaDTO pessoa;
    @Getter
    private LocalDate dataRetirada;
    @Getter
    private LocalDate dataEntrega;
    @Getter
    private Boolean entregue;
    @Getter
    private Collection<LivroDTO> livros = new ArrayList<>();



    public void addLivro(LivroDTO livro) {
        if (livro != null) {
            this.livros.add(livro);
        }
    }

    public void deleteLivro(LivroDTO livro) {
        if (livro != null) {
            this.livros.remove(livro);
        }
    }

    public void limparListaLivros(){
        livros.clear();
    }
}
