package com.cibertec.servicelmplement;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.repository.ProductoRepository;
import com.cibertec.repository.UsuarioRepository;
import com.cibertec.repository.VentaRepository;
import com.cibertec.service.DashBoardService;

@Service
public class DashBoardServiceImpl implements DashBoardService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public Map<String, Object> getSummary() {
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalProductos", productoRepository.count());
        summary.put("totalVentas", ventaRepository.count());
        return summary;
    }
}
