package com.cibertec.servicelmplement;

import com.cibertec.dto.DetalleVentaDTO;
import com.cibertec.model.DetalleVenta;
import com.cibertec.repository.DetalleVentaRepository;
import com.cibertec.service.DetalleVentaService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetalleVentaServiceImpl implements DetalleVentaService {

	
    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Override
    public DetalleVenta registrarDetalleVenta(DetalleVenta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }

    @Override
    public List<DetalleVenta> listarDetallesPorVenta(int idVenta) {
        return detalleVentaRepository.findAll()
                .stream()
                .filter(d -> d.getVenta().getIdVenta() == idVenta)
                .collect(Collectors.toList());
    }

	@Override
	public List<DetalleVentaDTO> listarDetalleDeVentas() {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ListarDetalleDeVentas");
        List<Object[]> result = query.getResultList();
        List<DetalleVentaDTO> lista = new ArrayList<>();
        for (Object[] row : result) {
            lista.add(new DetalleVentaDTO(row));
        }
        return lista;
	}
}
