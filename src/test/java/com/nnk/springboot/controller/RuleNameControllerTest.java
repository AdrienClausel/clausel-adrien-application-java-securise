package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.ruleName.IRuleNameService;
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

@WebMvcTest(RuleNameController.class)
@WithMockUser
class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRuleNameService ruleNameService;

    @Test
    void home_shouldReturnRuleNameListView() throws Exception {
        when(ruleNameService.getAll()).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNamesDto"));
    }

    @Test
    void addRuleForm_shouldReturnAddView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    void validate_shouldRedirect_whenNoErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/validate")
                        .with(csrf())
                        .param("name", "Rule test")
                        .param("description", "Description test")
                        .param("json", "{}")
                        .param("template", "template")
                        .param("sqlStr", "SELECT *")
                        .param("sqlPart", "WHERE id = 1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).add(any());
    }

    @Test
    void validate_shouldReturnAddView_whenErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/validate")
                        .with(csrf())
                        .param("name", "")
                        .param("description", "")
                        .param("json", "")
                        .param("template", "")
                        .param("sqlStr", "")
                        .param("sqlPart", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));

        verify(ruleNameService, never()).add(any());
    }

    @Test
    void showUpdateForm_shouldReturnUpdateView() throws Exception {
        RuleName ruleName = new RuleName(
                "Rule 1",
                "Desc",
                "{}",
                "template",
                "SELECT *",
                "WHERE id = 1"
        );

        when(ruleNameService.getById(1)).thenReturn(ruleName);

        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleNameDto"));

        verify(ruleNameService, times(1)).getById(1);
    }

    @Test
    void updateRuleName_shouldRedirect_whenNoErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/1")
                        .with(csrf())
                        .param("name", "Rule updated")
                        .param("description", "Updated desc")
                        .param("json", "{}")
                        .param("template", "template")
                        .param("sqlStr", "SELECT *")
                        .param("sqlPart", "WHERE id = 2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).update(any(), eq(1));
    }

    @Test
    void deleteRuleName_shouldRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).delete(1);
    }


}
