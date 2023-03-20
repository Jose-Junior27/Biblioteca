package com.example.demo.controller;

import com.example.demo.dto.CategoriaDTO;
import com.example.demo.repository.BaseConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class CategoriaController {

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaDTO>> consultaCategoria(){
        return ResponseEntity.ok().header("List-Size",Integer.toString(BaseConfig.dbCategoria.size())).body(BaseConfig.dbCategoria);
    }

    @GetMapping("/categoria")
    public ResponseEntity<List<CategoriaDTO>> consultaCategoria(@RequestParam("id") int page){
        //retorna por indice
        List<CategoriaDTO> list = (page < 0 || page > BaseConfig.dbCategoria.size() - 1) ? BaseConfig.dbCategoria : Collections.singletonList(BaseConfig.dbCategoria.get(page));
        return ResponseEntity.ok().header("List-Size",Integer.toString(list.size())).body(list);
    }

    @PostMapping("/categoria")
    public ResponseEntity create(@RequestBody CategoriaDTO categoria){
        BaseConfig.dbCategoria.add(categoria);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/categoria")
    public ResponseEntity delete(@RequestParam("id") int index){
        BaseConfig.dbCategoria.remove(index);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/categoria")
    public ResponseEntity update(@RequestParam("id") int index, @RequestBody CategoriaDTO categoriaDTO){
        BaseConfig.dbCategoria.set(index, categoriaDTO);
        return ResponseEntity.ok().build();
    }


}
