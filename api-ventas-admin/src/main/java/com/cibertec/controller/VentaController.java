package com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.cibertec.model.Venta;
import com.cibertec.service.VentaService;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping("/registrar")
    public ResponseEntity<Venta> registrarVenta(@RequestBody Venta venta) {
        Venta nuevaVenta = ventaService.registrarVenta(venta);
        return ResponseEntity.ok(nuevaVenta);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Venta>> getAllVentas() {
        List<Venta> ventas = ventaService.getAllVentas();
        return ResponseEntity.ok(ventas);
    }
}
