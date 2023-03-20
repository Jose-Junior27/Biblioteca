package com.example.demo.controller;

import com.example.demo.Enum.Situacao;
import com.example.demo.dto.LivroDTO;
import com.example.demo.dto.LocacaoDTO;
import com.example.demo.repository.BaseConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
public class LocacaoController {


    @GetMapping("/locacoes")
    public ResponseEntity<List<LocacaoDTO>> locacoes(){
        return ResponseEntity.ok().header("List-Size",Integer.toString(BaseConfig.dbLocacao.size())).body(BaseConfig.dbLocacao);
    }

    @GetMapping("/locacao")
    public ResponseEntity<List<LocacaoDTO>> consultaLocacao(@RequestParam("id") int page){
        //retorna por indice
        List<LocacaoDTO> list = (page < 0 || page > BaseConfig.dbLocacao.size() - 1) ? BaseConfig.dbLocacao : Collections.singletonList(BaseConfig.dbLocacao.get(page));
        return ResponseEntity.ok().header("List-Size",Integer.toString(list.size())).body(list);
    }

    @PostMapping("/locacao")
    public ResponseEntity create(@RequestBody LocacaoDTO locacao){
        var id = BaseConfig.dbLocacao.size();
        locacao.setId((long) id);

        var idCliente = locacao.getPessoa().getId();
        var pessoa = BaseConfig.dbPessoa.stream().filter(f -> (f.getId() == idCliente)).findFirst();
        if (pessoa.isEmpty()) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente não encontrado!");
        }
        locacao.setPessoa(pessoa.get());
        if (locacao.getDataRetirada() == null){
            locacao.setDataRetirada(LocalDate.now());
        }
        if (locacao.getDataEntrega() == null){
            locacao.setDataEntrega(LocalDate.now().plusDays(7));
        }
        locacao.setEntregue(false);
        BaseConfig.dbLocacao.add(locacao);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/locacao/{id}/livro/")
    public ResponseEntity addLivro(@PathVariable(value = "id") Long id, @RequestBody LivroDTO livroDTO){
        var locacao = BaseConfig.dbLocacao.stream().filter(f -> (f.getId() == id)).findFirst();
        if (locacao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Locação não encontrada!");
        }

        var idLivro = livroDTO.getId();
        var livro = BaseConfig.dbLivro.stream().filter(f -> (f.getId() == idLivro)).findFirst();
        if (livro.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Livro não encontrado!");
        }

        LivroDTO liv = livro.get();
        if (liv.getSituacao() == Situacao.LOCADO || liv.getSituacao() == Situacao.RESERVADO ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Livro não disponível!");
        }
        LocacaoDTO loc = new LocacaoDTO();
        loc = locacao.get();
        try {
            loc.addLivro(liv);
            liv.setSituacao(Situacao.LOCADO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("erro ao adicionar livro ! " + e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/locacao")
    public ResponseEntity delete(@RequestParam("id") int index){
        BaseConfig.dbLocacao.remove(index);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/locacao/{id}/livro/")
    public ResponseEntity deleteLivro(@PathVariable(value = "id") Long id, @RequestBody LivroDTO livroDTO){
        var locacao = BaseConfig.dbLocacao.stream().filter(f -> (f.getId() == id)).findFirst();
        if (locacao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Locação não encontrada!");
        }

        var idLivro = livroDTO.getId();
        var livro = BaseConfig.dbLivro.stream().filter(f -> (f.getId() == idLivro)).findFirst();
        if (livro.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Livro não existe!");
        }

        LivroDTO liv = livro.get();
        LocacaoDTO loc = new LocacaoDTO();
        loc = locacao.get();
        try {
            loc.deleteLivro(liv);
            liv.setSituacao(Situacao.DISPONIVEL);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("erro ao excluir livro da locação! " + e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/locacao")
    public ResponseEntity update(@RequestParam("id") int index, @RequestBody LocacaoDTO locacao){
        BaseConfig.dbLocacao.set(index, locacao);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/locacao/{id}/devolucao")
    public ResponseEntity devolucaoLivro(@PathVariable(value = "id") Long id){
        var locacao = BaseConfig.dbLocacao.stream().filter(f -> (f.getId() == id)).findFirst();
        if (locacao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Locação não encontrada!");
        }

        try {
        LocacaoDTO loc = new LocacaoDTO();
        loc = locacao.get();
        loc.setDataEntrega(LocalDate.now());
        loc.setEntregue(true);
        loc.getLivros().stream().forEach(l -> l.setSituacao(Situacao.DISPONIVEL));
        loc.limparListaLivros();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("erro ao atualizar locacao ! " + e.getMessage());
        }

        return ResponseEntity.ok().build();
    }
}
