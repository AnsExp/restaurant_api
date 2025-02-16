package com.restaurant.utils;

import com.restaurant.entities.Order;
import com.restaurant.entities.OrderItem;

public class OrderUtil {
    public static long calculateSubtotal(Order order) {
        long subtotal = 0;
        for (OrderItem item : order.getItems())
            subtotal += item.getQuantity() * item.getPrice();
        return subtotal;
    }
}
