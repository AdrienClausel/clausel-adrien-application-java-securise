package com.nnk.springboot.mappers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dtos.BidListDto;

import java.util.List;
import java.util.stream.Collectors;

public final class BidListMapper {

    private BidListMapper(){}

    public static BidList toEntity(BidListDto dto) {
        BidList bidList = new BidList();
        bidList.setBidListId(dto.bidListId());
        bidList.setAccount(dto.account());
        bidList.setType(dto.type());
        return bidList;
    }

    public static BidListDto toDto(BidList bidList){
        return new BidListDto(
                bidList.getBidListId(),
                bidList.getAccount(),
                bidList.getType(),
                bidList.getBidQuantity()
        );
    }

    public static List<BidListDto> toDtoList(List<BidList> bidLists){
        return bidLists.stream()
                .map(BidListMapper::toDto)
                .collect(Collectors.toList()
        );
    }
}
