package com.metrarty.LogiWEB.boundary;

import com.metrarty.LogiWEB.boundary.model.DeliveryOrderDto;
import com.metrarty.LogiWEB.service.DeliveryOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryDeliveryOrderControllerTest {

    @InjectMocks
    private DeliveryOrderController deliveryOrderController;

    @Mock
    private DeliveryOrderService deliveryOrderService;

    @Test
    public void TestCreateDeliveryOrder() {
        DeliveryOrderDto deliveryOrderDto = new DeliveryOrderDto();
        deliveryOrderController.createDeliveryOrder(deliveryOrderDto);
        verify(deliveryOrderService, times(1)).createDeliveryOrder(deliveryOrderDto);
        verifyNoMoreInteractions(deliveryOrderService);
    }

    @Test
    public void testFindAllDeliveryOrders() {
        deliveryOrderController.findAll();
        verify(deliveryOrderService, times(1)).findAllDeliveryOrders();
        verifyNoMoreInteractions(deliveryOrderService);
    }

    @Test
    public void testEditDeliveryOrder() {
        DeliveryOrderDto deliveryOrderDto = new DeliveryOrderDto();
        deliveryOrderController.editDeliveryOrder(deliveryOrderDto, 1L);
        verify(deliveryOrderService, times(1)).editDeliveryOrder(deliveryOrderDto, 1L);
        verifyNoMoreInteractions(deliveryOrderService);
    }

    @Test
    public void testDeleteDeliveryOrderById() {
        deliveryOrderController.deleteDeliveryOrderById(1L);
        verify(deliveryOrderService, times(1)).deleteDeliveryOrderById(1L);
        verifyNoMoreInteractions(deliveryOrderService);
    }
}
