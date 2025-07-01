package com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cibertec.service.DashBoardService;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashBoardController {

    @Autowired
    private DashBoardService dashBoardService;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getDashboardSummary() {
        Map<String, Object> summary = dashBoardService.getSummary();
        
        // Crear un objeto de respuesta que envíe 'value' con los datos
        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("msg", "Datos cargados correctamente");
        response.put("value", summary);  // Los datos van dentro de 'value'

        return ResponseEntity.ok(response);
    }
}
