package com.example.demo.controller;

import com.example.demo.dto.LivroDTO;
import com.example.demo.repository.BaseConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class LivroController {

    @GetMapping("/livros")
    public ResponseEntity<List<LivroDTO>> consultaLivros(){
        return ResponseEntity.ok().header("List-Size",Integer.toString(BaseConfig.dbLivro.size())).body(BaseConfig.dbLivro);
    }

    @GetMapping("/livro")
    public ResponseEntity<List<LivroDTO>> consultaLivro(@RequestParam("id") int page){
        //paginar
        //List<LivroDTO> list = page == 0 ? dbLivro : dbLivro.stream().skip(page).limit(10).collect(Collectors.toList());

        //retorna por indice
        List<LivroDTO> list = (page < 0 || page > BaseConfig.dbLivro.size() - 1) ? BaseConfig.dbLivro : Collections.singletonList(BaseConfig.dbLivro.get(page));
        return ResponseEntity.ok().header("List-Size",Integer.toString(list.size())).body(list);
    }

    @PostMapping("/livro")
    public ResponseEntity create(@RequestBody LivroDTO livro){
        var id = BaseConfig.dbLivro.size();
        livro.setId((long) id);
        BaseConfig.dbLivro.add(livro);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/livro")
    public ResponseEntity delete(@RequestParam("id") int index){
        BaseConfig.dbLivro.remove(index);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/livro")
    public ResponseEntity update(@RequestParam("id") int index, @RequestBody LivroDTO livro){
        BaseConfig.dbLivro.set(index, livro);
        return ResponseEntity.ok().build();
    }


}
