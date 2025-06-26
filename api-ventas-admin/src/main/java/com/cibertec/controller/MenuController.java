package com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.cibertec.model.Menu;
import com.cibertec.service.MenuService;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;

    @GetMapping("/{idRol}")
    public ResponseEntity<List<Menu>> getMenusByRol(@PathVariable int idRol) {
        List<Menu> menus = menuService.getMenusByRol(idRol);
        return ResponseEntity.ok(menus);
    }
}
