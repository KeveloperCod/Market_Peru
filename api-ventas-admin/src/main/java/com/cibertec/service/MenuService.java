package com.cibertec.service;

import java.util.List;

import com.cibertec.model.Menu;

public interface MenuService {
    List<Menu> getMenusByRol(int idRol);
}
