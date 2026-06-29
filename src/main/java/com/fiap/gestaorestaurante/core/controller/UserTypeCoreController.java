package com.fiap.gestaorestaurante.core.controller;

import com.fiap.gestaorestaurante.core.domain.UserType;
import com.fiap.gestaorestaurante.core.usecase.UserTypeUsecase;

import java.util.List;

public class UserTypeCoreController {
    private final UserTypeUsecase usecase;

    public UserTypeCoreController(UserTypeUsecase usecase) {
        this.usecase = usecase;
    }

    public UserType create(String name) { return usecase.create(name); }
    public List<UserType> findAll() { return usecase.findAll(); }
    public UserType findById(Long id) { return usecase.findById(id); }
    public UserType update(Long id, String name) { return usecase.update(id, name); }
    public void delete(Long id) { usecase.delete(id); }
}
