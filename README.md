# **LogiWEB**

Logistics Restful CRUD application.  
Uses Spring Boot, Maven, JPA and Hibernate for setup and developing.

Application after running creates in-memory tables with Cargos, Cities, Distances, Orders and Trucks.
Using REST APIs new records in these tables can be created, edited, loaded or deleted.
Application allows ro create orders, that include cities from where pick up cargo, and where to deliver,
assign truck for delivery, calculate days for delivery according distances, trucks etc.
Statuses for orders and trucks can be changed.

The app defines following APIs:

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

**Truck selection:**    
A suitable truck can be found through REST: truck/choose/{cityOrderId}/{orderSize}/

Call parameters: city ID where truck should receive cargo and order size. 
Found truck will be selected following the next rules:
 - Truck have "FREE" status;
 - Truck is suitable by capacity;
If suitable truck already in Order's city, it will be chosen. Otherwise, truck in the nearest city will be chosen.

**Truck assigning to order:**  
A suitable truck is assigned to an order through REST: /order/{orderId}/assign/truck/{truckId}

Call parameters: order ID and ID of suitable truck.
Request changes Truck status to "ASSIGNED".
Assigned trucks are not available in truck selection function.
Previously assigned truck gets "FREE" status and will be able in truck selection function.
Status of assigned truck will be changed from FREE to ASSIGNED.

The following use cases are covered:
 - new order is created with assigned truck;
 - a truck is already assigned to an order.

**Order status "Om the way:"**  
Orders status is changed by REST: /order/{orderId}/set/status/ontheway

Call parameter: order ID.
Only orders with assigned trucks can get status "On the way". Otherwise, exception will be thrown.

**Order cancellation::**  
Order status is changed by REST: /order/{orderId}/set/status/cancelled

Call parameter: order ID.
Request changes order status to "CANCELLED".
Cancelled order cannot be changed via PUT request, otherwise exception will be thrown.
Truck which was assigned to order gets FREE status and reaches target destination (gets new location)
if this is quicker than go back. 
Otherwise, it gets FREE status and returns to source destination.

The status cannot be changed for an order that has already been canceled.

**Order completion:**  
Order status is changed by REST: /order/{orderId}/set/status/completed

Call parameter: order ID.
Request changes order status to "COMPLETED".
Completed order cannot be changed via PUT/PATCH.
Truck status will be changed to FREE.

The status cannot be changed for an order that has already been completed.

**Calculation of approximate delivery date:**  
Calculation logic is based on distance between truck and cities of cargo location and final destination, and also on 
truck's parameter _distancePerDay_.
Integrated in order creation or editing, but only if truck assigned.
Result is recalculated if another truck is assigned to order or order target/destination is changed.
