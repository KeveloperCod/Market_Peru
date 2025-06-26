package com.cibertec.servicelmplement;

import com.cibertec.model.Venta;
import com.cibertec.repository.VentaRepository;
import com.cibertec.service.VentaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public Venta registrarVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

    @Override
    public List<Venta> getAllVentas() {
        return ventaRepository.findAll();
    }
}
