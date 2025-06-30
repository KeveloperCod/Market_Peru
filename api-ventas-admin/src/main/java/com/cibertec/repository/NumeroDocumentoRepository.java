package com.cibertec.repository;

import com.cibertec.model.NumeroDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NumeroDocumentoRepository extends JpaRepository<NumeroDocumento, Integer> {
    
    // Método para obtener el último número de documento
    NumeroDocumento findFirstByOrderByIdNumeroDocumentoDesc();
}
