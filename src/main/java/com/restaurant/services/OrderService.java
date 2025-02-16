package com.restaurant.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.restaurant.entities.Dish;
import com.restaurant.entities.Order;
import com.restaurant.entities.OrderItem;
import com.restaurant.repositories.DishRepository;
import com.restaurant.repositories.OrderItemRepository;
import com.restaurant.repositories.OrderRepository;
import com.restaurant.requests.OrderRequest;
import com.restaurant.responses.OrderResponse;
import com.restaurant.utils.Formatter;

@Service
public class OrderService {

    @Value("${constants.iva}")
    private double iva;
    @Value("${constants.pagination.size}")
    private int pageSize;

    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(DishRepository dishRepository, OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository) {
        this.dishRepository = dishRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public OrderResponse save(OrderRequest orderRequest) {
        Order order = convertRequestToEntity(orderRequest);
        Order orderSaved = orderRepository.save(order);
        List<OrderItem> items = order.getItems();
        items.forEach(item -> item.setOrder(Order.builder().id(orderSaved.getId()).build()));
        orderItemRepository.saveAll(items);
        return convertEntityToResponse(orderSaved);
    }

    public OrderResponse modify(long id, OrderRequest orderRequest) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return null;
        order.setTable(orderRequest.table());
        order.setMessage(orderRequest.message());
        order.getItems().forEach(item -> orderItemRepository.deleteById(item.getId()));
        order.setItems(new ArrayList<>());
        for (OrderRequest.OrderItemRequest orderItem : orderRequest.dishes()) {
            Optional<Dish> dish = dishRepository.findById(orderItem.dish());
            dish.ifPresent(value -> {
                var x = orderItemRepository.save(OrderItem
                        .builder()
                        .order(order)
                        .item(value.getName())
                        .price(value.getPrice())
                        .quantity(orderItem.quantity())
                        .build());
                order.getItems().add(x);
            });
        }
        return convertEntityToResponse(orderRepository.save(order));
    }

    public boolean exits(long id) {
        return orderRepository.existsById(id);
    }

    public OrderResponse find(long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(this::convertEntityToResponse).orElse(null);
    }

    public List<OrderResponse> list() {
        return orderRepository.findAll().stream().map(this::convertEntityToResponse).toList();
    }

    public List<OrderResponse> list(String field) {
        return orderRepository.findAll(Sort.by(field)).stream().map(this::convertEntityToResponse).toList();
    }

    public List<OrderResponse> list(int page) {
        return orderRepository.findAll(PageRequest.of(page, pageSize)).map(this::convertEntityToResponse).toList();
    }

    public List<OrderResponse> list(int page, String field) {
        return orderRepository.findAll(PageRequest.of(page, pageSize, Sort.by(field))).map(this::convertEntityToResponse).toList();
    }

    private Order convertRequestToEntity(OrderRequest orderRequest) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderRequest.OrderItemRequest item : orderRequest.dishes()) {
            Optional<Dish> dishFound = dishRepository.findById(item.dish());
            dishFound.ifPresent(dish -> orderItems.add(OrderItem
                    .builder()
                    .item(dish.getName())
                    .price(dish.getPrice())
                    .quantity(item.quantity())
                    .build()));
        }
        return Order
                .builder()
                .items(orderItems)
                .table(orderRequest.table())
                .message(orderRequest.message())
                .dateCreation(LocalDateTime.now())
                .build();
    }

    private OrderResponse convertEntityToResponse(Order order) {
        List<OrderResponse.OrderItemResponse> items = new ArrayList<>();
        long subtotal = 0;
        for (OrderItem item : order.getItems()) {
            long price = item.getPrice() * item.getQuantity();
            subtotal += price;
            items.add(OrderResponse.OrderItemResponse
                    .builder()
                    .name(item.getItem())
                    .quantity(item.getQuantity())
                    .price(Formatter.prettyCurrency(item.getPrice()))
                    .subtotal(Formatter.prettyCurrency(price))
                    .build());
        }
        long total = (long) (subtotal * (iva + 1));
        return OrderResponse
                .builder()
                .items(items)
                .id(order.getId())
                .table(order.getTable())
                .message(order.getMessage())
                .iva(Formatter.prettyPercent(iva))
                .total(Formatter.prettyCurrency(total))
                .subtotal(Formatter.prettyCurrency(subtotal))
                .dateTime(Formatter.prettyDate(order.getDateCreation()))
                .build();
    }
}
