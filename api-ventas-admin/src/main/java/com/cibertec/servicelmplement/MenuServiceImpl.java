package com.cibertec.servicelmplement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.model.Menu;
import com.cibertec.repository.MenuRolRepository;
import com.cibertec.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRolRepository menuRolRepository;

    @Override
    public List<Menu> getMenusByRol(int idRol) {
        return menuRolRepository.findByRol_IdRol(idRol)
                .stream()
                .map(menuRol -> menuRol.getMenu())
                .collect(Collectors.toList());
    }
}
