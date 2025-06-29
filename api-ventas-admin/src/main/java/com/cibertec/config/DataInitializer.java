package com.cibertec.config;

import com.cibertec.model.Rol;
import com.cibertec.model.Usuario;
import com.cibertec.repository.RolRepository;
import com.cibertec.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDB(
            UsuarioRepository usuarioRepo,
            RolRepository rolRepo,
            PasswordEncoder encoder
    ) {
        return args -> {

            // Rol “ADMIN” (solo si no existe)
            Rol adminRol = rolRepo.findByNombre("ADMIN")
                    .orElseGet(() -> rolRepo.save(
                            Rol.builder().nombre("ADMIN").build()
                    ));

            // Usuario admin (solo si no existe)
            final String correo = "admin@example.com";
            if (usuarioRepo.findByCorreo(correo) == null) {
                usuarioRepo.save(
                        Usuario.builder()
                                .nombreCompleto("Administrador")
                                .correo(correo)
                                .clave(encoder.encode("admin123"))
                                .esActivo(true)
                                .rol(adminRol)
                                .build()
                );
                System.out.println("➜ Usuario admin creado: admin@example.com / admin123");
            } else {
                // opcional: resetear clave cada vez que levantas
                Usuario u = usuarioRepo.findByCorreo(correo);
                u.setClave(encoder.encode("admin123"));
                u.setEsActivo(true);
                u.setRol(adminRol);
                usuarioRepo.save(u);
                System.out.println("➜ Usuario admin actualizado");
            }
        };
    }
}
