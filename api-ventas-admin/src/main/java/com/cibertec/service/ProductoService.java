package com.cibertec.service;

import com.cibertec.dto.ProductCategoriaDTO;
import com.cibertec.dto.ProductRequest;
import com.cibertec.model.Producto;

import java.util.List;

public interface ProductoService {
	List<Producto> listarProductos();
    List<ProductCategoriaDTO> listarProductosXCategoria();
    void registrarProducto(ProductRequest producto);
    void actualizarProducto(Integer idProducto, ProductRequest producto);
    void desactivarProducto(Integer idProducto);

    Producto getProductoById(int id);
    Producto saveProducto(Producto producto);
    Producto updateProducto(int id, Producto producto);
    boolean deleteProducto(int id);


}
