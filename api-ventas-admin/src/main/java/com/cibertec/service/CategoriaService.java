package com.cibertec.service;

import java.util.List;

import com.cibertec.model.Categoria;

public interface CategoriaService {
    Categoria registrarCategoria(Categoria categoria);
    List<Categoria> listarCategorias();
    Categoria buscarCategoriaPorId(int id);
    void eliminarCategoriaPorId(int id);
    Categoria actualizarCategoria(Categoria categoria);
}
