package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListService implements IBidListService {

    @Autowired
    private BidListRepository bidListRepository;

    @Override
    public void add(BidListDto bidListDto) {
        var bidList = new BidList(bidListDto.account(), bidListDto.type(), bidListDto.bidQuantity());
        bidListRepository.save(bidList);
    }

    @Override
    public void update(BidListDto bidListDto, Integer id) {
        var optionnalBidList = bidListRepository.findById(id);
        if (optionnalBidList.isPresent()){
            var bidList = optionnalBidList.get();
            bidList.setAccount(bidListDto.account());
            bidList.setType(bidListDto.type());
            bidList.setBidQuantity(bidListDto.bidQuantity());

            bidListRepository.save(bidList);
        }
    }

    @Override
    public void delete(Integer id) {
        bidListRepository.deleteById(id);
    }

    @Override
    public List<BidList> getAll() {
        return bidListRepository.findAll();
    }

    @Override
    public BidList getById(Integer id) {
        return bidListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BidList not found: " + id));
    }
}
