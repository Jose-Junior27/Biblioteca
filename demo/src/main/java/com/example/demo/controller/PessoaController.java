package com.example.demo.controller;

import com.example.demo.dto.PessoaDTO;
import com.example.demo.repository.BaseConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class PessoaController {

    @GetMapping("/pessoas")
    public ResponseEntity<List<PessoaDTO>> consultaPessoas(){
        return ResponseEntity.ok().header("List-Size",Integer.toString(BaseConfig.dbPessoa.size())).body(BaseConfig.dbPessoa);

    }

    @GetMapping("/pessoa")
    public ResponseEntity<List<PessoaDTO>> consultaLivro(@RequestParam("id") int page){
        //retorna por indice
        List<PessoaDTO> list = (page < 0 || page > BaseConfig.dbPessoa.size() - 1) ? BaseConfig.dbPessoa : Collections.singletonList(BaseConfig.dbPessoa.get(page));
        return ResponseEntity.ok().header("List-Size",Integer.toString(list.size())).body(list);
    }

    @PostMapping("/pessoa")
    public ResponseEntity create(@RequestBody PessoaDTO pessoa){
        var id = BaseConfig.dbPessoa.size();
        pessoa.setId((long) id);
        BaseConfig.dbPessoa.add(pessoa);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/pessoa")
    public ResponseEntity delete(@RequestParam("id") int index){
        BaseConfig.dbPessoa.remove(index);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/pessoa")
    public ResponseEntity update(@RequestParam("id") int index, @RequestBody PessoaDTO pessoa){
        BaseConfig.dbPessoa.set(index, pessoa);
        return ResponseEntity.ok().build();
    }
}
