package com.cibertec.controller;

import com.cibertec.dto.LoginUsuarioDTO;
import com.cibertec.dto.RegistroUsuarioDTO;
import com.cibertec.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginUsuarioDTO loginUserDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Revisar credenciales de logeo");
        }
        try {
            String jwt = authService.authenticate(loginUserDto.getCorreo(), loginUserDto.getClave());
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
            return ResponseEntity.internalServerError().body("Error al autenticar.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistroUsuarioDTO newUserDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Revisar credenciales de logeo");
        }

        try {
            authService.registerUser(newUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("registrado");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }

    @GetMapping("/check-auth")
    public ResponseEntity<String> testAuth(){
        return ResponseEntity.ok("User ok");
    }

}
