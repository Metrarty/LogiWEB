package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.OrderDto;
import com.metrarty.LogiWEB.boundary.model.TruckDto;
import com.metrarty.LogiWEB.repository.OrderRepository;
import com.metrarty.LogiWEB.repository.entity.*;
import com.metrarty.LogiWEB.service.exception.EntityNotFoundException;
import com.metrarty.LogiWEB.service.mapper.OrderMapper;
import com.metrarty.LogiWEB.service.validator.OrderValidator;
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

    @Mock
    private DeliveryWorkingDaysCalculationService deliveryWorkingDaysCalculationService;

    @Mock
    private TruckService truckService;

    @Mock
    private OrderValidator orderValidator;

    @Mock
    private CargoService cargoService;

    @Mock
    private DistanceService distanceService;

    private static final Instant NOW =Instant.now();

    @Test
    public void TestCreateOrder() {
        //prepare
        Truck assignedTruck = new Truck();
        TruckDto assignedTruckDto = new TruckDto();
        OrderDto testOrderDto = new OrderDto();
        Order testOrder = new Order();
        testOrder.setAssignedTruck(assignedTruck);
        when(orderMapper.toEntityWithCreatedAt(testOrderDto)).thenReturn(testOrder);
        when(deliveryWorkingDaysCalculationService.calculateDeliveryWorkingDays(testOrder)).thenReturn(10);
        when(truckService.changeTruckStatus(null, String.valueOf(TruckStatus.ASSIGNED))).thenReturn(assignedTruckDto);
        //run
        orderService.createOrder(testOrderDto);
        //test
        verify(orderRepository, times(1)).save(testOrder);
        verify(orderMapper, times(1)).toEntityWithCreatedAt(testOrderDto);
        verify(orderMapper, times(1)).toDto(testOrder);
        verify(deliveryWorkingDaysCalculationService, times(1)).calculateDeliveryWorkingDays(testOrder);
        verify(truckService, times(1)).changeTruckStatus(null, String.valueOf(TruckStatus.ASSIGNED));
        verifyNoMoreInteractions(orderRepository, orderMapper, deliveryWorkingDaysCalculationService, truckService);
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
        OrderDto inputDto = new OrderDto();
        Order editedOrder = new Order();
        when(orderMapper.toEntityWithChangedAt(inputDto)).thenReturn(editedOrder);

        Order originalOrder = new Order();
        originalOrder.setId(1L);
        originalOrder.setCreatedAt(NOW);
        originalOrder.setOrderStatus("CREATED");
        Truck assignedTruck = new Truck();
        originalOrder.setAssignedTruck(assignedTruck);
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(originalOrder));

        Order saved = new Order();
        when(orderRepository.save(editedOrder)).thenReturn(saved);

        //run
        orderService.editOrder(inputDto, 1L);

        //test
        verify(orderMapper, times(1)).toEntityWithChangedAt(inputDto);
        verify(orderRepository, times(1)).findById(1L);
        verify(orderValidator, times(1)).checkOrderStatus("CREATED");
        verify(orderRepository, times(1)).save(editedOrder);
        verify(orderMapper, times(1)).toDto(saved);
        verifyNoMoreInteractions(orderRepository, orderMapper, orderValidator);
    }

    @Test(expected = EntityNotFoundException.class)
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

    @Test
    public void assignTruckToOrderTest() {
        //prepare
        Truck inputTruck = new Truck();
        inputTruck.setId(1L);

        Order inputOrder = new Order();
        inputOrder.setId(2L);
        inputOrder.setCreatedAt(NOW);
        inputOrder.setOrderStatus("CREATED");
        Truck assignedTruck = new Truck();
        assignedTruck.setId(3L);
        inputOrder.setAssignedTruck(assignedTruck);

        when(orderRepository.findById(2L)).thenReturn(Optional.of(inputOrder));

        TruckDto changedAssignedTruck = new TruckDto();
        changedAssignedTruck.setTruckStatus(TruckStatus.valueOf("FREE"));
        when(truckService.changeTruckStatus(3L, "FREE")).thenReturn(changedAssignedTruck);

        when(truckService.findOneTruckById(1L)).thenReturn(inputTruck);


        Order saved = new Order();

        when(orderRepository.save(inputOrder)).thenReturn(saved);

        //run
        orderService.assignTruckToOrder(1L, 2L);

        //test
        verify(truckService, times(1)).changeTruckStatus(3L, "FREE");
        verify(deliveryWorkingDaysCalculationService, times(1)).calculateDeliveryWorkingDays(inputOrder);
        verify(orderRepository, times(1)).findById(2L);
        verify(truckService, times(1)).findOneTruckById(1L);
        verify(truckService, times(1)).changeTruckStatus(1L, "ASSIGNED");
        verify(orderRepository, times(1)).save(inputOrder);
        verify(orderMapper, times(1)).toDto(saved);
        verifyNoMoreInteractions(truckService, deliveryWorkingDaysCalculationService, orderRepository, orderMapper);
    }

    @Test
    public void setStatusOnTheWayTest() {
        //prepare
        Order order = new Order();
        order.setId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order saved = new Order();
        saved.setId(1L);
        saved.setOrderStatus("ON_THE_WAY");
        saved.setChangedAt(NOW);
        when(orderRepository.save(order)).thenReturn(saved);
        //run
        orderService.setStatusOnTheWay(1L);
        //test
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(order);
        verify(orderMapper, times(1)).toDto(saved);
        verifyNoMoreInteractions(orderRepository, orderMapper);
    }

    @Test
    public void setStatusCompleted() {
        //prepare

        Truck assignedTruck = new Truck();
        assignedTruck.setId(2L);

        Cargo cargo = new Cargo();
        cargo.setId(3L);

        Order order = new Order();
        order.setId(1L);
        order.setCargo(cargo);
        order.setAssignedTruck(assignedTruck);
        order.setOrderStatus("CREATED");
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order saved = new Order();
        saved.setId(1L);
        saved.setOrderStatus("COMPLETED");
        saved.setCompletedAt(NOW);
        saved.setAssignedTruck(assignedTruck);
        when(orderRepository.save(order)).thenReturn(saved);
        //run
        orderService.setStatusCompleted(1L);
        //test
        verify(orderRepository, times(1)).findById(1L);
        verify(orderValidator, times(1)).checkOrderStatus("CREATED");
        verify(truckService, times(1)).changeTruckStatus(2L,"FREE");
        verify(cargoService, times(1)).setCargoDeliveredAt(3L);
        verify(orderRepository, times(1)).save(order);
        verify(orderMapper, times(1)).toDto(saved);
        verifyNoMoreInteractions(orderRepository, orderMapper);
    }

    @Test
    public void setStatusCancelledTest() {
        //prepare
        Truck assignedTruck = new Truck();
        assignedTruck.setId(1L);
        assignedTruck.setTruckStatus("ASSIGNED");
        City truckLocation = new City();
        truckLocation.setId(3L);
        assignedTruck.setLocation(truckLocation);

        City orderDestination  = new City();
        orderDestination.setId(2L);
        City orderSource = new City();
        orderSource.setId(4L);

        Order order = new Order();
        order.setId(1L);
        order.setSourceCity(orderSource);
        order.setDestination(orderDestination);
        order.setOrderStatus("CREATED");
        order.setAssignedTruck(assignedTruck);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Long distanceFromTruckToDestination = 1000L;
        Long distanceFromTruckToSourceCity = 100L;
        when(distanceService.distanceBetweenCities(truckLocation, orderDestination)).thenReturn(distanceFromTruckToDestination);
        when(distanceService.distanceBetweenCities(truckLocation, orderSource)).thenReturn(distanceFromTruckToSourceCity);

        Order saved = new Order();
        saved.setId(2L);
        order.setOrderStatus("CANCELLED");
        order.setCancelledAt(NOW);
        when(orderRepository.save(order)).thenReturn(saved);
        //run
        orderService.setStatusCancelled(1L);
        //test
        verify(orderRepository, times(1)).findById(1L);
        verify(orderValidator, times(1)).checkOrderStatus("CANCELLED");
        verify(truckService, times(1)).changeTruckLocation(1L, orderDestination);
        verify(truckService, times(1)).changeTruckLocation(1L, orderSource);
        verify(truckService, times(1)).changeTruckStatus(1l, "FREE");
        verify(orderRepository, times(1)).save(order);
        verify(orderMapper, times(1)).toDto(saved);
        verifyNoMoreInteractions(orderValidator, truckService, orderRepository, orderMapper);
    }
}
