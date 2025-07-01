package com.cibertec.controller;

import com.cibertec.dto.DetalleVentaDTO;
import com.cibertec.dto.VentaResponseDTO;
import com.cibertec.model.DetalleVenta;
import com.cibertec.model.Venta;
import com.cibertec.service.NumeroDocumentoService;
import com.cibertec.service.VentaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.sql.Timestamp;
import java.util.stream.Collectors;

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
    public ResponseEntity<VentaResponseDTO> registrarVenta(@RequestBody Venta venta) {
        try {
            logger.info("Iniciando el registro de venta...");

            String numeroDocumento = numeroDocumentoService.generarNumeroDocumento();
            venta.setNumeroDocumento(numeroDocumento);
            logger.info("Número de documento generado: {}", numeroDocumento);

            if (venta.getDetalleVenta() == null || venta.getDetalleVenta().isEmpty()) {
                logger.error("Detalles de la venta no proporcionados.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            Venta nuevaVenta = ventaService.registrarVenta(venta);
            if (nuevaVenta != null) {
                logger.info("Venta registrada con éxito. ID: {}", nuevaVenta.getIdVenta());

                VentaResponseDTO dto = new VentaResponseDTO();
                dto.setIdVenta(nuevaVenta.getIdVenta());
                dto.setNumeroDocumento(nuevaVenta.getNumeroDocumento());
                dto.setTipoPago(nuevaVenta.getTipoPago());
                dto.setTotal(nuevaVenta.getTotal());
                dto.setFechaRegistro(nuevaVenta.getFechaRegistro());

                // ✅ Convertir detalles a DetalleVentaDTO solo si están presentes
                if (nuevaVenta.getDetalleVenta() != null) {
                    List<DetalleVentaDTO> detalles = nuevaVenta.getDetalleVenta().stream()
                        .map(det -> new DetalleVentaDTO(new Object[]{
                            null,
                            nuevaVenta.getIdVenta(),
                            Timestamp.valueOf(nuevaVenta.getFechaRegistro()),
                            det.getProducto().getNombre(),
                            det.getCantidad(),
                            det.getPrecio(),
                            det.getPrecio().multiply(
                                java.math.BigDecimal.valueOf(det.getCantidad())
                            )
                        }))
                        .collect(Collectors.toList());

                    dto.setDetalleVenta(detalles);
                }

                return ResponseEntity.ok(dto);
            } else {
                logger.error("Error al registrar la venta.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            logger.error("Error durante el registro de la venta: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Venta>> getAllVentas() {
        List<Venta> ventas = ventaService.getAllVentas();
        logger.info("Obteniendo todas las ventas. Total: {}", ventas.size());
        return ResponseEntity.ok(ventas);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPERVISOR')")
    @GetMapping("/reporte")
    public ResponseEntity<List<Venta>> obtenerReportePorFechas(
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin) {
        try {
            List<Venta> ventas = ventaService.obtenerVentasPorRangoFecha(fechaInicio, fechaFin);
            return ResponseEntity.ok(ventas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
