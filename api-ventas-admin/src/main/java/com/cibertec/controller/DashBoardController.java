package com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cibertec.service.DashBoardService;

import org.springframework.http.ResponseEntity;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashBoardController {

	@Autowired
	private DashBoardService dashBoardService;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getDashboardSummary() {
        Map<String, Object> summary = dashBoardService.getSummary();
        return ResponseEntity.ok(summary);
    }
}
