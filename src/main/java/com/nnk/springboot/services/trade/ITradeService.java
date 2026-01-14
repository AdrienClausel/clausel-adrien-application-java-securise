package com.nnk.springboot.services.trade;

import com.nnk.springboot.domain.Trade;

import java.util.List;

public interface ITradeService {
    void add(Trade trade);
    void update(Trade trade, Integer id);
    void delete(Integer id);
    List<Trade> getAll();
    Trade getById(Integer id);
}
