package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
}
