package com.nnk.springboot.services.trade;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService implements ITradeService{

    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public void add(Trade trade) {
        tradeRepository.save(trade);
    }

    @Override
    public void update(Trade tradeUpdate, Integer id) {
        var optionnalTrade = tradeRepository.findById(id);
        if (optionnalTrade.isPresent()){
            var trade = optionnalTrade.get();
            trade.setAccount(tradeUpdate.getAccount());
            trade.setType(tradeUpdate.getType());
            trade.setBuyQuantity(tradeUpdate.getBuyQuantity());

            tradeRepository.save(trade);
        }
    }

    @Override
    public void delete(Integer id) {
        tradeRepository.deleteById(id);
    }

    @Override
    public List<Trade> getAll() {
        return tradeRepository.findAll();
    }

    @Override
    public Trade getById(Integer id) {
        return tradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found: " + id));
    }
}
