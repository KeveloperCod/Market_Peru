package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
}
