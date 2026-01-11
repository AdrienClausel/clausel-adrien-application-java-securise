package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.bidList.BidListService;
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
    private BidList bidListUpdate;

    @BeforeEach
    void setUp() {
        bidList = new BidList("compte1", "type1", 5.0);
        bidList.setBidListId(1);

        bidListUpdate = new BidList("compte2", "type2", 10.0);
    }

    @Test
    void add_shouldSave() {
        bidListService.add(bidList);

        ArgumentCaptor<BidList> argumentCaptor = ArgumentCaptor.forClass(BidList.class);
        verify(bidListRepository).save(argumentCaptor.capture());

        BidList newBidList = argumentCaptor.getValue();
        assertEquals("compte1", newBidList.getAccount());
        assertEquals("type1", newBidList.getType());
        assertEquals(5, newBidList.getBidQuantity());

    }

    @Test
    void update_shouldUpdate_whenIdExists(){
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));

        bidListService.update(bidListUpdate, 1);

        verify(bidListRepository).save(bidList);
        assertEquals(bidListUpdate.getAccount(),bidList.getAccount());
        assertEquals(bidListUpdate.getType(),bidList.getType());
        assertEquals(bidListUpdate.getBidQuantity(),bidList.getBidQuantity());
    }

    @Test
    void update_shouldUpdate_whenIdNotExists(){
        when(bidListRepository.findById(1)).thenReturn(Optional.empty());

        bidListService.update(bidListUpdate, 1);

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
    void getById_shouldReturnList_whenIdExists() {
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));

        BidList result = bidListService.getById(1);

        assertEquals(bidList, result);
    }

    @Test
    void getById_shouldReturnList_whenIdNotExists() {
        when(bidListRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> bidListService.getById(1));
    }
}
