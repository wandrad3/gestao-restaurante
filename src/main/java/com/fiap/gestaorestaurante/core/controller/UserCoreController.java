package com.fiap.gestaorestaurante.core.controller;

import com.fiap.gestaorestaurante.core.domain.User;
import com.fiap.gestaorestaurante.core.dto.UserInputDto;
import com.fiap.gestaorestaurante.core.dto.UserUpdateInputDto;
import com.fiap.gestaorestaurante.core.usecase.UserUsecase;

import java.util.List;

public class UserCoreController {
    private final UserUsecase usecase;

    public UserCoreController(UserUsecase usecase) {
        this.usecase = usecase;
    }

    public User create(UserInputDto input) { return usecase.create(input); }
    public List<User> findAll() { return usecase.findAll(); }
    public User findById(Long id) { return usecase.findById(id); }
    public List<User> searchByName(String name) { return usecase.searchByName(name); }
    public User update(Long id, UserUpdateInputDto input) { return usecase.update(id, input); }
    public void changePassword(Long id, String password) { usecase.changePassword(id, password); }
    public void delete(Long id) { usecase.delete(id); }
}
