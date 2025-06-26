package com.cibertec.service;

import com.cibertec.dto.DetalleVentaDTO;
import com.cibertec.model.DetalleVenta;

import java.util.List;

public interface DetalleVentaService {
    DetalleVenta registrarDetalleVenta(DetalleVenta detalleVenta);
    List<DetalleVenta> listarDetallesPorVenta(int idVenta);
    
    // Método para el procedimiento almacenado:
    List<DetalleVentaDTO> listarDetalleDeVentas();
}
