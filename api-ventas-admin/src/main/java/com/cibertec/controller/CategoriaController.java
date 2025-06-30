package com.cibertec.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.model.Categoria;
import com.cibertec.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarCategorias() {
        try {
            List<Categoria> categorias = categoriaService.listarCategorias();
            return ResponseEntity.ok(Map.of(
                "status", true,
                "value", categorias
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "status", false,
                "msg", "Error al listar categorías"
            ));
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<Categoria> registrarCategoria(@RequestBody Categoria categoria) {
        try {
            Categoria nuevaCategoria = categoriaService.registrarCategoria(categoria);
            return ResponseEntity.ok(nuevaCategoria);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}