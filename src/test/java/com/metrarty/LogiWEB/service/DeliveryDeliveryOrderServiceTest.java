package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.DeliveryOrderDto;
import com.metrarty.LogiWEB.repository.DeliveryOrderRepository;
import com.metrarty.LogiWEB.repository.entity.DeliveryOrder;
import com.metrarty.LogiWEB.service.exception.DeliveryOrderNotFoundException;
import com.metrarty.LogiWEB.service.mapper.DeliveryOrderMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryDeliveryOrderServiceTest {

    @InjectMocks
    private DeliveryOrderService deliveryOrderService;

    @Mock
    private DeliveryOrderRepository deliveryOrderRepository;

    @Mock
    private DeliveryOrderMapper deliveryOrderMapper;

    private static final Instant NOW =Instant.now();

    @Test
    public void TestCreateDeliveryOrder() {
        //prepare
        DeliveryOrderDto testDeliveryOrderDto = new DeliveryOrderDto();
        testDeliveryOrderDto.setId(1L);
        DeliveryOrder testDeliveryOrder = deliveryOrderMapper.toEntityWithCreatedAt(testDeliveryOrderDto);
        //run
        deliveryOrderService.createDeliveryOrder(testDeliveryOrderDto);
        //test
        verify(deliveryOrderRepository, times(1)).save(testDeliveryOrder);
        verifyNoMoreInteractions(deliveryOrderRepository);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateDeliveryOrder_WhenInputIsNull() {
        deliveryOrderService.createDeliveryOrder(null);
    }

    @Test
    public void testFindAllDeliveryOrders() {
        //prepare
        DeliveryOrder deliveryOrder = new DeliveryOrder();
        List<DeliveryOrder> deliveryOrders = Collections.singletonList(deliveryOrder);
        when(deliveryOrderRepository.findAll()).thenReturn(deliveryOrders);
        DeliveryOrderDto dto = new DeliveryOrderDto();
        dto.setId(1L);
        when(deliveryOrderMapper.toDto(deliveryOrder)).thenReturn(dto);
        DeliveryOrderDto expectedDto = new DeliveryOrderDto();
        expectedDto.setId(1L);
        //run
        List<DeliveryOrderDto> actual = deliveryOrderService.findAllDeliveryOrders();
        //test
        Assert.assertEquals("Must be equal", 1, actual.size());
        Assert.assertEquals("Must be equal", expectedDto, actual.get(0));
        verify(deliveryOrderRepository, times(1)).findAll();
        verifyNoMoreInteractions(deliveryOrderRepository);
    }

    @Test
    public void testEditDeliveryOrder() {
        //prepare
        DeliveryOrderDto input = new DeliveryOrderDto();
        DeliveryOrder deliveryOrder = new DeliveryOrder();
        when(deliveryOrderMapper.toEntityWithChangedAt(input)).thenReturn(deliveryOrder);

        DeliveryOrder foundDeliveryOrder = new DeliveryOrder();
        foundDeliveryOrder.setId(1L);
        foundDeliveryOrder.setCreatedAt(NOW);
        when(deliveryOrderRepository.findById(1L)).thenReturn(java.util.Optional.of(foundDeliveryOrder));

        DeliveryOrder saved = new DeliveryOrder();
        saved.setId(1L);
        saved.setCreatedAt(NOW);
        when(deliveryOrderRepository.save(saved)).thenReturn(saved);

        //run
        deliveryOrderService.editDeliveryOrder(input, 1L);

        //test
        verify(deliveryOrderMapper, times(1)).toEntityWithChangedAt(input);
        verify(deliveryOrderRepository, times(1)).findById(1L);
        verify(deliveryOrderRepository, times(1)).save(saved);
        verify(deliveryOrderMapper, times(1)).toDto(saved);
        verifyNoMoreInteractions(deliveryOrderRepository, deliveryOrderMapper);
    }

    @Test(expected = DeliveryOrderNotFoundException.class)
    public void testEditDeliveryOrder_whenOriginalNotFound() {
        //prepare
        DeliveryOrderDto input = new DeliveryOrderDto();
        DeliveryOrder deliveryOrder = new DeliveryOrder();
        when(deliveryOrderMapper.toEntityWithChangedAt(input)).thenReturn(deliveryOrder);

        DeliveryOrder foundDeliveryOrder = new DeliveryOrder();
        foundDeliveryOrder.setId(1L);
        foundDeliveryOrder.setCreatedAt(NOW);
        when(deliveryOrderRepository.findById(1L)).thenReturn(Optional.empty());

        //run
        deliveryOrderService.editDeliveryOrder(input, 1L);
        verifyNoMoreInteractions(deliveryOrderRepository, deliveryOrderMapper);
    }

    @Test(expected = NullPointerException.class)
    public void testEditDeliveryOrderById_WhenInputIsNull() {
        deliveryOrderService.editDeliveryOrder(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testEditDeliveryOrderById_WhenOrderDtoIsNull() {
        deliveryOrderService.editDeliveryOrder(null, 1L);
    }

    @Test(expected = NullPointerException.class)
    public void testEditDeliveryOrderById_WhenOrderIdIsNull() {
        DeliveryOrderDto deliveryOrderDto = new DeliveryOrderDto();
        deliveryOrderService.editDeliveryOrder(deliveryOrderDto, null);
    }

    @Test
    public void testDeleteDeliveryOrderById() {
        //run
        deliveryOrderService.deleteDeliveryOrderById(1L);

        //test
        verify(deliveryOrderRepository, times(1)).deleteById(1L);
        verifyNoMoreInteractions(deliveryOrderRepository);
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteOrderById_WhenInputIsNull() {
        deliveryOrderService.deleteDeliveryOrderById(null);
    }
}
