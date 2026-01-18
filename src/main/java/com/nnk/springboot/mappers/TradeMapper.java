package com.nnk.springboot.mappers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dtos.TradeDto;

import java.util.List;
import java.util.stream.Collectors;

public final class TradeMapper {
    private TradeMapper(){}

    public static Trade toEntity(TradeDto dto){
        Trade trade = new Trade();
        trade.setAccount(dto.account());
        trade.setType((dto.type()));
        trade.setBuyQuantity(dto.buyQuantity());
        return trade;
    }

    public static TradeDto toDto(Trade trade){
        return new TradeDto(
                trade.getId(),
                trade.getAccount(),
                trade.getType(),
                trade.getBuyQuantity()
        );
    }

    public static List<TradeDto> toDtoList(List<Trade> trades){
        return trades.stream()
                .map(TradeMapper::toDto)
                .collect(Collectors.toList());
    }
}
