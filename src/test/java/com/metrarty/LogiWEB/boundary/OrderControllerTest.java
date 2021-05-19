package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.boundary.model.OrderDto;
import com.metrarty.LogiWEB.boundary.model.TruckDto;
import com.metrarty.LogiWEB.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Test
    public void TestCreateOrder() {
        OrderDto orderDto = new OrderDto();
        orderController.createOrder(orderDto);
        verify(orderService, times(1)).createOrder(orderDto);
        verifyNoMoreInteractions(orderService);
    }

    @Test
    public void testFindAllOrders() {
        orderController.findAll();
        verify(orderService, times(1)).findAllOrders();
        verifyNoMoreInteractions(orderService);
    }

    @Test
    public void testEditOrder() {
        OrderDto orderDto = new OrderDto();
        orderController.editOrder(orderDto, 1L);
        verify(orderService, times(1)).editOrder(orderDto, 1L);
        verifyNoMoreInteractions(orderService);
    }

    @Test
    public void testDeleteOrderById() {
        orderController.deleteOrderById(1L);
        verify(orderService, times(1)).deleteOrderById(1L);
        verifyNoMoreInteractions(orderService);
    }

    @Test
    public void testAssignTruckOrder() {
        orderController.assignTruckToOrder(1L, 2L);
        verify(orderService, times(1)).assignTruckToOrder(1L, 2L);
        verifyNoMoreInteractions(orderService);
    }

    @Test
    public void testSetStatusOnTheWay() {
        orderController.setStatusOnTheWay(1L);
        verify(orderService, times(1)).setStatusOnTheWay(1L);
        verifyNoMoreInteractions(orderService);
    }

    @Test
    public void testSetStatusCompleted() {
        orderController.setStatusCompleted(1L);
        verify(orderService, times(1)).setStatusCompleted(1L);
        verifyNoMoreInteractions(orderService);
    }

    @Test
    public void testSetStatusCancelled() {
        orderController.setStatusCancelled(1L);
        verify(orderService, times(1)).setStatusCancelled(1L);
        verifyNoMoreInteractions(orderService);
    }
}
