package com.cibertec.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.cibertec.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, Integer> {
	
    @Procedure(procedureName = "ListarDetalleDeVentas")
    List<Object[]> ListarDetalleDeVentas();
    List<Venta> findByFechaRegistroBetween(LocalDateTime inicio, LocalDateTime fin);

}
