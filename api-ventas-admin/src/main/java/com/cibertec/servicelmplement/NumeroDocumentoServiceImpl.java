package com.cibertec.servicelmplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.model.NumeroDocumento;
import com.cibertec.repository.NumeroDocumentoRepository;
import com.cibertec.service.NumeroDocumentoService;

@Service
public class NumeroDocumentoServiceImpl implements NumeroDocumentoService {

    @Autowired
    private NumeroDocumentoRepository numeroDocumentoRepository;

    // Implementación del método para generar el próximo número de documento
    @Override
    public String generarNumeroDocumento() {
        // Obtener el último número de documento
        NumeroDocumento ultimoNumero = numeroDocumentoRepository.findFirstByOrderByIdNumeroDocumentoDesc();

        if (ultimoNumero == null) {
            // Si no existe un número previo, iniciar en 1
            ultimoNumero = new NumeroDocumento();
            ultimoNumero.setUltimoNumero(1);
            numeroDocumentoRepository.save(ultimoNumero);
        } else {
            // Incrementar el número del último documento
            ultimoNumero.setUltimoNumero(ultimoNumero.getUltimoNumero() + 1);
            numeroDocumentoRepository.save(ultimoNumero);
        }

        // Retornar el número de documento generado
        return String.format("%04d", ultimoNumero.getUltimoNumero());
    }
}