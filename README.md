# **LogiWEB**

Logistics Restful CRUD application.
Uses Spring Boot, Maven, JPA and Hibernate for setup and developing.

Application after running creates in-memory tables with Cargos, Cities, Distances, Orders and Trucks.
Using REST APIs new records in these tables can be created, edited, loaded or deleted.

The app defines following CRUD APIs:

## Rest APIs for Cargos

    POST /cargo/create/
    GET /cargo/all/
    PUT /cargo/editbyid/{id}
    DELETE /cargo/deletebyid/{id}

## Rest APIs for Cities

    POST /city/create/
    GET /city/all/
    PUT /city/editbyid/{id}
    DELETE /city/deletebyid/{id}

## Rest APIs for Distances

    POST /distance/create/
    GET /distance/all/
    PUT /distance/editbyid/{id}
    DELETE /distance/deletebyid/{id}

## Rest APIs for Orders

    POST /order/create/
    GET /order/all/
    PUT /order/editbyid/{id}
    DELETE /order/deletebyid/{id}
    PATCH /order/{orderId}/assign/truck/{truckId}
    PATCH /order/{orderId}/set/status/ontheway
    PATCH /order/{orderId}/set/status/completed
    PATCH /order/{orderId}/set/status/cancelled

## Rest APIs for Trucks

    POST /truck/create/
    GET /truck/all/
    PUT /truck/editbyid/{id}
    DELETE /truck/deletebyid/{id}
    GET truck/choose/{cityOrderId}/{orderSize}/

## Application's features:

###Truck selection:
A truck can be found through the method call in TruckService.
Call parameter: FROM city, Size.
Found truck will be fixed perfectly for the following criterias:

- is located nearest to FROM city;
- can hold related SIZE.

###Truck assigning to order:
Extend REST API (PATCH) to assign a truck to an order (Order API)
Change Truck status to "ASSIGNED"
Assigned trucks are not available in auto truck selection function
Previously assigned truck will have FREE status and will be able in auto select function
Truck status will be changed from FREE to ASSIGNED when order is created.

The following use cases are covered:

 - new order is created with truck;
 - a truck is assigned to an order.

###Order cancellation
Set order status to CANCELLED.
REST endpoint to cancel Order (PATCH HTTP method).
Cancelled order cannot be changed via PUT request > throws new exception.
Truck which was assigned to order reaches target destination and gets FREE status if it is quicker than goes back. 
Otherwise, returns to source destination and gets FREE status.

###Order completion
When order is completed, the field completedAt is set.
REST endpoint to complete order (PATCH).
Completed order cannot be changed via PUT/PATCH.
deliveredAt will be set in Cargo.
Truck status will be changed to FREE.

###logic to calculate approximate delivery date
Calculation logic based on distance and truck.
Integrated in order creation setting/calculation.
DeliveryWorkingDays should be recalculated if another truck is assigned to order or order target/destination is changed