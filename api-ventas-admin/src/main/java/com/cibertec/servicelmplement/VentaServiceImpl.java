package com.cibertec.servicelmplement;

import com.cibertec.model.DetalleVenta;
import com.cibertec.model.Venta;
import com.cibertec.repository.ProductoRepository;
import com.cibertec.repository.VentaRepository;
import com.cibertec.service.VentaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Venta registrarVenta(Venta venta) {
        if (venta.getDetalleVenta() != null) {
            for (DetalleVenta detalle : venta.getDetalleVenta()) {
                detalle.setVenta(venta);
                // Recuperar el producto por ID (si solo viene el ID desde Angular)
                if (detalle.getProducto() != null && detalle.getProducto().getIdProducto() > 0) {
                    detalle.setProducto(productoRepository.findById(detalle.getProducto().getIdProducto()).orElse(null));
                }
            }
        }

        return ventaRepository.save(venta);
    }


    @Override
    public List<Venta> getAllVentas() {
        return ventaRepository.findAll();
    }
    
    
}
