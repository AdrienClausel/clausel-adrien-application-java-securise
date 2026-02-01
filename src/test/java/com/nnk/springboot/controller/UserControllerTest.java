package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.user.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@WithMockUser
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @Test
    void home_shouldReturnUserListView() throws Exception {
        when(userService.getAll()).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("usersDto"));

        verify(userService, times(1)).getAll();
    }

    @Test
    void addUser_shouldReturnAddView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    void validate_shouldRedirect_whenNoErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
                        .with(csrf())
                        .param("fullname", "John Doe")
                        .param("username", "jdoe")
                        .param("password", "Password123!")
                        .param("role", "USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userService, times(1)).add(any());
    }

    @Test
    void validate_shouldReturnAddView_whenErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
                        .with(csrf())
                        .param("fullname", "")
                        .param("username", "")
                        .param("password", "")
                        .param("role", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));

        verify(userService, never()).add(any());
    }

    @Test
    void showUpdateForm_shouldReturnUpdateView() throws Exception {
        User user = new User("John Doe", "jdoe", "encodedPwd", "USER");
        when(userService.getById(1)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("userDto"));

        verify(userService, times(1)).getById(1);
    }

    @Test
    void updateUser_shouldRedirect_whenNoErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/update/1")
                        .with(csrf())
                        .param("fullname", "John Updated")
                        .param("username", "jdoe")
                        .param("password", "NewPassword123!")
                        .param("role", "ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userService, times(1)).update(any(), eq(1));
    }

    @Test
    void deleteUser_shouldRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userService, times(1)).delete(1);
    }

}
