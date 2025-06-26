package com.cibertec.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.model.Rol;
import com.cibertec.service.RolService;

@RestController
@RequestMapping("/api/roles")
public class RolController {

	private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Rol>> listarRoles() {
        try {
            List<Rol> roles = rolService.listarRoles();
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<Rol> registrarRol(@RequestBody Rol rol) {
        try {
            Rol nuevoRol = rolService.registrarRol(rol);
            return ResponseEntity.ok(nuevoRol);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
