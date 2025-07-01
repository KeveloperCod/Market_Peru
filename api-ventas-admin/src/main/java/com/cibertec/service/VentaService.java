package com.cibertec.service;

import java.util.List;

import com.cibertec.model.Venta;

public interface VentaService {
    Venta registrarVenta(Venta venta);
    List<Venta> getAllVentas();
    List<Venta> obtenerVentasPorRangoFecha(String fechaInicio, String fechaFin);

    
}