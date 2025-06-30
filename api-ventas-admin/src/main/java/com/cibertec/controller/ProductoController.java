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
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/listar-con-categoria")
    public ResponseEntity<List<ProductCategoriaDTO>> listarConCategoria() {
        List<ProductCategoriaDTO> productos = productoService.listarProductosXCategoria();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Producto>> listarSimple() {
        return ResponseEntity.ok(productoService.listarProductos());
    }

    @PostMapping("/registrar")
    public ResponseEntity<Map<String, Object>> registrarProducto(@RequestBody ProductRequest producto) {
        try {
            productoService.registrarProducto(producto);
            return ResponseEntity.ok(Map.of(
                "status", true,
                "msg", "Producto registrado correctamente"
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "status", false,
                "msg", "Error al registrar producto"
            ));
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Map<String, Object>> actualizarProducto(@PathVariable Integer id, @RequestBody ProductRequest producto) {
        try {
            productoService.actualizarProducto(id, producto);
            return ResponseEntity.ok(Map.of(
                "status", true,
                "msg", "Producto actualizado correctamente"
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "status", false,
                "msg", "Error al actualizar producto"
            ));
        }
    }

    @DeleteMapping("/desactivar/{id}")
    public ResponseEntity<Map<String, Object>> desactivarProducto(@PathVariable Integer id) {
        try {
            productoService.desactivarProducto(id);
            return ResponseEntity.ok(Map.of(
                "status", true,
                "msg", "Producto eliminado correctamente"
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "status", false,
                "msg", "Error al eliminar producto"
            ));
        }
    }
}
