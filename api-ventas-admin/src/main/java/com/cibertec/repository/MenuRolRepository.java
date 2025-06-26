package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.model.MenuRol;

import java.util.List;

public interface MenuRolRepository extends JpaRepository<MenuRol, Integer> {

    List<MenuRol> findByRol_IdRol(int idRol);
}
