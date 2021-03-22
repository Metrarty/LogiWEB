package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.OrderDto;
import com.metrarty.LogiWEB.repository.OrderRepository;
import com.metrarty.LogiWEB.repository.entity.Order;
import com.metrarty.LogiWEB.service.exception.OrderNotFoundException;
import com.metrarty.LogiWEB.service.mapper.OrderMapper;
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
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    private static final Instant NOW =Instant.now();

    @Test
    public void TestCreateOrder() {
        //prepare
        OrderDto testOrderDto = new OrderDto();
        testOrderDto.setId(1L);
        Order testOrder = orderMapper.toEntityWithCreatedAt(testOrderDto);
        //run
        orderService.createOrder(testOrderDto);
        //test
        verify(orderRepository, times(1)).save(testOrder);
        verifyNoMoreInteractions(orderRepository);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateOrder_WhenInputIsNull() {
        orderService.createOrder(null);
    }

    @Test
    public void testFindAllOrders() {
        //prepare
        Order order = new Order();
        List<Order> orders = Collections.singletonList(order);
        when(orderRepository.findAll()).thenReturn(orders);
        OrderDto dto = new OrderDto();
        dto.setId(1L);
        when(orderMapper.toDto(order)).thenReturn(dto);
        OrderDto expectedDto = new OrderDto();
        expectedDto.setId(1L);
        //run
        List<OrderDto> actual = orderService.findAllOrders();
        //test
        Assert.assertEquals("Must be equal", 1, actual.size());
        Assert.assertEquals("Must be equal", expectedDto, actual.get(0));
        verify(orderRepository, times(1)).findAll();
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    public void testEditOrder() {
        //prepare
        OrderDto input = new OrderDto();
        Order order = new Order();
        when(orderMapper.toEntityWithChangedAt(input)).thenReturn(order);

        Order foundOrder = new Order();
        foundOrder.setId(1L);
        foundOrder.setCreatedAt(NOW);
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(foundOrder));

        Order saved = new Order();
        saved.setId(1L);
        saved.setCreatedAt(NOW);
        when(orderRepository.save(saved)).thenReturn(saved);

        //run
        orderService.editOrder(input, 1L);

        //test
        verify(orderMapper, times(1)).toEntityWithChangedAt(input);
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(saved);
        verify(orderMapper, times(1)).toDto(saved);
        verifyNoMoreInteractions(orderRepository, orderMapper);
    }

    @Test(expected = OrderNotFoundException.class)
    public void testEditOrder_whenOriginalNotFound() {
        //prepare
        OrderDto input = new OrderDto();
        Order order = new Order();
        when(orderMapper.toEntityWithChangedAt(input)).thenReturn(order);

        Order foundOrder = new Order();
        foundOrder.setId(1L);
        foundOrder.setCreatedAt(NOW);
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        //run
        orderService.editOrder(input, 1L);
        verifyNoMoreInteractions(orderRepository, orderMapper);
    }

    @Test(expected = NullPointerException.class)
    public void testEditOrderById_WhenInputIsNull() {
        orderService.editOrder(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testEditOrderById_WhenOrderDtoIsNull() {
        orderService.editOrder(null, 1L);
    }

    @Test(expected = NullPointerException.class)
    public void testEditOrderById_WhenOrderIdIsNull() {
        OrderDto orderDto = new OrderDto();
        orderService.editOrder(orderDto, null);
    }

    @Test
    public void testDeleteOrderById() {
        //run
        orderService.deleteOrderById(1L);

        //test
        verify(orderRepository, times(1)).deleteById(1L);
        verifyNoMoreInteractions(orderRepository);
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteOrderById_WhenInputIsNull() {
        orderService.deleteOrderById(null);
    }
}
