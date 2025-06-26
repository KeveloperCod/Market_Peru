package com.cibertec.repository;

import com.cibertec.dto.ProductoCategoriaDTO;
import com.cibertec.model.Producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query(value = "CALL ListarProductosXCategoria()", nativeQuery = true)
    List<ProductoCategoriaDTO> listarProductosXCategoria();
}
