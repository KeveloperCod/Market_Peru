package com.cibertec.controller;


import com.cibertec.dto.ProductCategoriaDTO;
import com.cibertec.dto.ProductRequest;
import com.cibertec.dto.ProductoCategoriaDTO;
import com.cibertec.model.Producto;
import com.cibertec.service.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

	@Autowired
	private ProductoService productoService;

	
	@GetMapping("/listar-con-categoria")
	public ResponseEntity<List<ProductCategoriaDTO>> listarProductosXCategoria() {
	    return ResponseEntity.ok(productoService.listarProductosXCategoria());
	}
	
    // Obtener todos los productos
    @GetMapping("/listar")
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(productoService.listarProductos());
    }
    
    //Procedimientoo, tomenlo de Guia
    /*
    @GetMapping("/listar-sp")
    public ResponseEntity<List<ProductoCategoriaDTO>> listarSP() {
        return ResponseEntity.ok(productoService.listarProductosDesdeSP());
    }
    */

    // Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable int id) {
        Producto producto = productoService.getProductoById(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un nuevo producto
    @PostMapping
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.saveProducto(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    
    @PostMapping("/registrar")
    public ResponseEntity<String> registrarProducto(@RequestBody ProductRequest producto) {
        productoService.registrarProducto(producto);  // Este es tu método que usa SP, no devuelve nada
        return ResponseEntity.ok("Producto registrado correctamente");
    }

    
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarProducto(@PathVariable Integer id, @RequestBody ProductRequest producto) {
        productoService.actualizarProducto(id, producto);
        return ResponseEntity.ok("Producto actualizado correctamente");
    }
    
    @DeleteMapping("/desactivar/{id}")
    public ResponseEntity<String> desactivarProducto(@PathVariable Integer id) {
        productoService.desactivarProducto(id);
        return ResponseEntity.ok("Producto desactivado correctamente");
    }
    
    
    // Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable int id, @RequestBody Producto producto) {
        Producto productoActualizado = productoService.updateProducto(id, producto);
        if (productoActualizado != null) {
            return ResponseEntity.ok(productoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable int id) {
        if (productoService.deleteProducto(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
