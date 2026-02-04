package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.trade.TradeService;
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
public class TradeServiceTest {

    @InjectMocks
    private TradeService tradeService;

    @Mock
    private TradeRepository tradeRepository;

    private Trade trade;
    private Trade tradeUpdate;

    @BeforeEach
    void setUp(){
        trade = new Trade("compte1", "type1");
        trade.setId(1);

        tradeUpdate = new Trade("compte2", "type2");
    }

    @Test
    void add_shouldSave(){
        tradeService.add(trade);

        ArgumentCaptor<Trade> argumentCaptor = ArgumentCaptor.forClass(Trade.class);
        verify(tradeRepository).save(argumentCaptor.capture());

        Trade newTrade = argumentCaptor.getValue();
        assertEquals(trade.getAccount(),newTrade.getAccount());
        assertEquals(trade.getType(),newTrade.getType());
        assertEquals(trade.getBuyQuantity(),newTrade.getBuyQuantity());
    }

    @Test
    void update_shouldUpdate_whenIdExists(){
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        tradeService.update(tradeUpdate, 1);

        verify(tradeRepository).save(trade);
        assertEquals(tradeUpdate.getAccount(), trade.getAccount());
        assertEquals(tradeUpdate.getType(), trade.getType());
        assertEquals(tradeUpdate.getBuyQuantity(), trade.getBuyQuantity());
    }

    @Test
    void update_shouldUpdate_whenIdNotExists(){
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        tradeService.update(tradeUpdate, 1);

        verify(tradeRepository,never()).save(any());
    }

    @Test
    void delete_shouldCallRepository() {
        tradeService.delete(1);

        verify(tradeRepository).deleteById(1);
    }

    @Test
    void getAll_shouldReturnList() {
        when(tradeRepository.findAll()).thenReturn(List.of(trade));

        List<Trade> result = tradeService.getAll();

        assertEquals(1,result.size());
        verify(tradeRepository).findAll();
    }

    @Test
    void getById_shouldReturnList_whenIdExists() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        Trade result = tradeService.getById(1);

        assertEquals(trade, result);
    }

    @Test
    void getById_shouldReturnList_whenIdNotExists() {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> tradeService.getById(1));
    }
}
