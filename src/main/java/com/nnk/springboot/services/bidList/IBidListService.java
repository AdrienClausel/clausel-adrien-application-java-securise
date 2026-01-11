package com.nnk.springboot.services.bidList;

import com.nnk.springboot.domain.BidList;

import java.util.List;

public interface IBidListService {
    void add(BidList bidList);
    void update(BidList bidList, Integer id);
    void delete(Integer id);
    List<BidList> getAll();
    BidList getById(Integer id);
}
