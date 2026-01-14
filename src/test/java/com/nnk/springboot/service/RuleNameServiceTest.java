package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.ruleName.RuleNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {

    @InjectMocks
    private RuleNameService ruleNameService;

    @Mock
    private RuleNameRepository ruleNameRepository;

    private RuleName ruleName;
    private RuleName ruleNameUpdate;

    @BeforeEach
    void setUp(){
        ruleName = new RuleName("règle 1","règle 1","{test:1}","test1","test1","test1");
        ruleName.setId(1);

        ruleNameUpdate = new RuleName("règle 2","règle 2","{test:2}","test2","test2","test2");
    }

    @Test
    void add_shouldSave(){
        ruleNameService.add(ruleName);

        ArgumentCaptor<RuleName> argumentCaptor = ArgumentCaptor.forClass(RuleName.class);
        verify(ruleNameRepository).save(argumentCaptor.capture());

        RuleName newRuleName = argumentCaptor.getValue();
        assertEquals(ruleName.getName(),newRuleName.getName());
        assertEquals(ruleName.getDescription(),newRuleName.getDescription());
        assertEquals(ruleName.getJson(),newRuleName.getJson());
        assertEquals(ruleName.getTemplate(),newRuleName.getTemplate());
        assertEquals(ruleName.getSqlStr(),newRuleName.getSqlStr());
        assertEquals(ruleName.getSqlPart(),newRuleName.getSqlPart());
    }

    @Test
    void update_shouldUpdate_whenIdExists(){
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        ruleNameService.update(ruleNameUpdate, 1);

        verify(ruleNameRepository).save(ruleName);
        assertEquals(ruleNameUpdate.getName(), ruleName.getName());
        assertEquals(ruleNameUpdate.getDescription(), ruleName.getDescription());
        assertEquals(ruleNameUpdate.getJson(), ruleName.getJson());
        assertEquals(ruleNameUpdate.getSqlStr(), ruleName.getSqlStr());
        assertEquals(ruleNameUpdate.getSqlPart(), ruleName.getSqlPart());
    }

    @Test
    void update_shouldUpdate_whenIdNotExists(){
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

        ruleNameService.update(ruleNameUpdate, 1);

        verify(ruleNameRepository,never()).save(any());
    }

    @Test
    void delete_shouldCallRepository() {
        ruleNameService.delete(1);

        verify(ruleNameRepository).deleteById(1);
    }

    @Test
    void getAll_shouldReturnList() {
        when(ruleNameRepository.findAll()).thenReturn(List.of(ruleName));

        List<RuleName> result = ruleNameService.getAll();

        assertEquals(1,result.size());
        verify(ruleNameRepository).findAll();
    }

    @Test
    void getById_shouldReturnList_whenIdExists() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        RuleName result = ruleNameService.getById(1);

        assertEquals(ruleName, result);
    }

    @Test
    void getById_shouldReturnList_whenIdNotExists() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> ruleNameService.getById(1));
    }
}
