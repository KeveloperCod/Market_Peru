package com.cibertec.servicelmplement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cibertec.model.Categoria;
import com.cibertec.repository.CategoriaRepository;
import com.cibertec.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Categoria registrarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria buscarCategoriaPorId(int id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarCategoriaPorId(int id) {
        categoriaRepository.deleteById(id);
    }

    @Override
    public Categoria actualizarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
}
