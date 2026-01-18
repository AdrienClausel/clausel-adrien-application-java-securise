package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;
    private User userUpdate;

    @BeforeEach
    void setUp(){
        user = new User("utilisateur1", "motdepasse1","nomprenom1", "Admin");
        user.setId(1);

        userUpdate = new User("utilisateur2", "motdepasse2","nomprenom2", "Admin");
    }

    @Test
    void add_shouldSave(){
        userService.add(user);

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argumentCaptor.capture());

        User newUser = argumentCaptor.getValue();
        assertEquals(user.getUsername(),newUser.getUsername());
        assertEquals(user.getPassword(),newUser.getPassword());
        assertEquals(user.getFullname(),newUser.getFullname());
    }

    @Test
    void update_shouldUpdate_whenIdExists(){
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        userService.update(userUpdate, 1);

        verify(userRepository).save(user);
        assertEquals(userUpdate.getUsername(), user.getUsername());
        assertTrue(encoder.matches(userUpdate.getPassword(),user.getPassword()));
        assertEquals(userUpdate.getFullname(), user.getFullname());
    }

    @Test
    void update_shouldUpdate_whenIdNotExists(){
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        userService.update(userUpdate, 1);

        verify(userRepository,never()).save(any());
    }

    @Test
    void delete_shouldCallRepository() {
        userService.delete(1);

        verify(userRepository).deleteById(1);
    }

    @Test
    void getAll_shouldReturnList() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> result = userService.getAll();

        assertEquals(1,result.size());
        verify(userRepository).findAll();
    }

    @Test
    void getById_shouldReturnList_whenIdExists() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User result = userService.getById(1);

        assertEquals(user, result);
    }

    @Test
    void getById_shouldReturnList_whenIdNotExists() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.getById(1));
    }
}

