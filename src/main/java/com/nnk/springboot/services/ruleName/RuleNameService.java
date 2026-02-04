package com.nnk.springboot.services.ruleName;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameService implements IRuleNameService{

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Override
    public void add(RuleName ruleName) {
        ruleNameRepository.save(ruleName);
    }

    @Override
    public void update(RuleName ruleNameUpdate, Integer id) {
        var optionnalRuleName = ruleNameRepository.findById(id);
        if (optionnalRuleName.isPresent()){
            var ruleName = optionnalRuleName.get();
            ruleName.setName(ruleNameUpdate.getName());
            ruleName.setDescription(ruleNameUpdate.getDescription());
            ruleName.setJson(ruleNameUpdate.getJson());
            ruleName.setTemplate(ruleNameUpdate.getTemplate());
            ruleName.setSqlStr(ruleNameUpdate.getSqlStr());
            ruleName.setSqlPart(ruleNameUpdate.getSqlPart());

            ruleNameRepository.save(ruleName);
        }
    }

    @Override
    public void delete(Integer id) {
        ruleNameRepository.deleteById(id);
    }

    @Override
    public List<RuleName> getAll() {
        return ruleNameRepository.findAll();
    }

    @Override
    public RuleName getById(Integer id) {
        return ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("RuleName not found: " + id));
    }
}
