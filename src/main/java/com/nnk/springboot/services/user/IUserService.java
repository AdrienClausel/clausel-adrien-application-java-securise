package com.nnk.springboot.services.user;

import com.nnk.springboot.domain.User;

import java.util.List;

public interface IUserService {
    void add(User user);
    void update(User user, Integer id);
    void delete(Integer id);
    List<User> getAll();
    User getById(Integer id);
}
