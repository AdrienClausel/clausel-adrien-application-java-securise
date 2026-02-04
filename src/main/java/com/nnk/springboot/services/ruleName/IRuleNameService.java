package com.nnk.springboot.services.ruleName;

import com.nnk.springboot.domain.RuleName;

import java.util.List;

public interface IRuleNameService {
    void add(RuleName ruleName);
    void update(RuleName ruleName, Integer id);
    void delete(Integer id);
    List<RuleName> getAll();
    RuleName getById(Integer id);
}
