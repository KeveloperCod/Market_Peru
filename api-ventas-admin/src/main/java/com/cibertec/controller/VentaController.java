package com.cibertec.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import com.cibertec.model.Venta;
import com.cibertec.service.NumeroDocumentoService;
import com.cibertec.service.VentaService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {
    private static final Logger logger = LoggerFactory.getLogger(VentaController.class);

    @Autowired
    private VentaService ventaService;
    
    @Autowired
    private NumeroDocumentoService numeroDocumentoService;
    
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPERVISOR') || hasRole('ROLE_USER')")
    @PostMapping("/registrar")
    public ResponseEntity<Venta> registrarVenta(@RequestBody Venta venta) {
        try {
            logger.info("Iniciando el registro de venta...");
            // Generamos el número de documento para la venta
            String numeroDocumento = numeroDocumentoService.generarNumeroDocumento();
            venta.setNumeroDocumento(numeroDocumento);
            logger.info("Número de documento generado: {}", numeroDocumento);
            
            // Verificamos los detalles de la venta
            if (venta.getDetalleVenta() == null || venta.getDetalleVenta().isEmpty()) {
                logger.error("Detalles de la venta no proporcionados.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            
            // Registramos la venta (esto implica registrar los detalles de venta también)
            Venta nuevaVenta = ventaService.registrarVenta(venta);
            
            if (nuevaVenta != null) {
                logger.info("Venta registrada con éxito. ID de venta: {}", nuevaVenta.getIdVenta());
                return ResponseEntity.ok(nuevaVenta);
            } else {
                logger.error("Error al registrar la venta.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            logger.error("Error durante el registro de la venta: {}", e.getMessage());
            Venta errorVenta = new Venta();
            errorVenta.setNumeroDocumento("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorVenta);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Venta>> getAllVentas() {
        List<Venta> ventas = ventaService.getAllVentas();
        logger.info("Obteniendo todas las ventas. Total de ventas: {}", ventas.size());
        return ResponseEntity.ok(ventas);
    }
}
