package com.cibertec.controller;

import com.cibertec.dto.DetalleVentaDTO;
import com.cibertec.model.DetalleVenta;
import com.cibertec.service.DetalleVentaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import java.util.List;

@RestController
@RequestMapping("/api/detalleventa")
@CrossOrigin(origins = "http://localhost:4200") 
public class DetalleVentaController {

    @Autowired
    private DetalleVentaService detalleVentaService;

    // Registrar detalle de venta
    @PostMapping
    public DetalleVenta registrarDetalle(@RequestBody DetalleVenta detalleVenta) {
        return detalleVentaService.registrarDetalleVenta(detalleVenta);
    }

    // Listar detalles por ID de venta
    @GetMapping("/venta/{idVenta}")
    public List<DetalleVenta> obtenerDetallesPorVenta(@PathVariable int idVenta) {
        return detalleVentaService.listarDetallesPorVenta(idVenta);
    }
    
    // listar todos los detalles de venta (con join a producto y venta) usando el SP
    @GetMapping("/detalle-completo")
    public ResponseEntity<List<DetalleVentaDTO>> listarDetalleDeVentas() {
        return ResponseEntity.ok(detalleVentaService.listarDetalleDeVentas());
    }
    
    
}
