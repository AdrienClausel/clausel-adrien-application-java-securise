package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {

    @InjectMocks
    private BidListService bidListService;

    @Mock
    private BidListRepository bidListRepository;

    private BidList bidList;
    private BidListDto bidListDto;

    @BeforeEach
    void setUp() {
        bidList = new BidList("compte1", "type1", 5.0);
        bidList.setBidListId(1);

        bidListDto = new BidListDto(null,"compte2", "type2", 10.0);
    }

    @Test
    void add_shouldSaveBidList() {
        bidListService.add(bidListDto);

        ArgumentCaptor<BidList> argumentCaptor = ArgumentCaptor.forClass(BidList.class);
        verify(bidListRepository).save(argumentCaptor.capture());

        BidList newBidList = argumentCaptor.getValue();
        assertEquals("compte2", newBidList.getAccount());
        assertEquals("type2", newBidList.getType());
        assertEquals(10, newBidList.getBidQuantity());

    }

    @Test
    void update_shouldUpdateBidList_whenIdExists(){
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));

        bidListService.update(bidListDto, 1);

        verify(bidListRepository).save(bidList);
        assertEquals(bidListDto.account(),bidList.getAccount());
        assertEquals(bidListDto.type(),bidList.getType());
        assertEquals(bidListDto.bidQuantity(),bidList.getBidQuantity());
    }

    @Test
    void update_shouldUpdateBidList_whenIdNotExists(){
        when(bidListRepository.findById(1)).thenReturn(Optional.empty());

        bidListService.update(bidListDto, 1);

        verify(bidListRepository,never()).save(any());
    }

    @Test
    void delete_shouldCallRepository() {
        bidListService.delete(1);

        verify(bidListRepository).deleteById(1);
    }

    @Test
    void getAll_shouldReturnList() {
        when(bidListRepository.findAll()).thenReturn(List.of(bidList));

        List<BidList> result = bidListService.getAll();

        assertEquals(1,result.size());
        verify(bidListRepository).findAll();
    }

    @Test
    void getById_shouldReturnBidList_whenIdExists() {
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));

        BidList result = bidListService.getById(1);

        assertEquals(bidList, result);
    }

    @Test
    void getById_shouldReturnBidList_whenIdNotExists() {
        when(bidListRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> bidListService.getById(1));
    }
}
