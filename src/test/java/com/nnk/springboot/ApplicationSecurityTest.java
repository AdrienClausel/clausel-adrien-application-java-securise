package com.nnk.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenNotAuthenticated_thenRedirectToLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/app/login"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void userWithoutAdminRole_cannotAccessAdminPages() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void adminCanAccessAdminPages() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andExpect(status().isOk());
    }

    @Test
    void publicResourcesAreAccessibleWithoutAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/login"))
                .andExpect(status().isOk());
    }

    @Test
    void loginSuccess_redirectsToHome() throws Exception {
        mockMvc.perform(formLogin("/app/login")
                        .user("user1")
                        .password("user1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    @WithMockUser
    void logoutShouldRedirect() throws Exception {
        mockMvc.perform(post("/app-logout").with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
