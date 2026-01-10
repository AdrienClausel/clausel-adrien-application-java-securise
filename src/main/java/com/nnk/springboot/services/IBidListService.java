package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;

import java.util.List;

public interface IBidListService {
    void add(BidListDto bidListDto);
    void update(BidListDto bidListDto, Integer id);
    void delete(Integer id);
    List<BidList> getAll();
    BidList getById(Integer id);
}
