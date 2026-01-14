package com.nnk.springboot.services.user;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User userUpdate, Integer id) {
        var optionnalUser = userRepository.findById(id);
        if (optionnalUser.isPresent()){
            var user = optionnalUser.get();
            user.setFullname(userUpdate.getFullname());
            user.setUsername(userUpdate.getUsername());
            user.setPassword(userUpdate.getPassword());

            userRepository.save(user);
        }
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("user not found: " + id));
    }
}
