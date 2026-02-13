package com.shrijal.e_commerce.service;

import com.shrijal.e_commerce.dto.request.OrderRequestDTO;
import com.shrijal.e_commerce.dto.response.OrderResponseDTO;
import com.shrijal.e_commerce.dto.response.OrderItemResponseDTO;
import com.shrijal.e_commerce.model.Cart;
import com.shrijal.e_commerce.model.CartItem;
import com.shrijal.e_commerce.model.Order;
import com.shrijal.e_commerce.model.OrderItem;
import com.shrijal.e_commerce.model.OrderStatus;
import com.shrijal.e_commerce.repository.CartRepository;
import com.shrijal.e_commerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RestTemplate restTemplate;

    //Place an order from user's cart and Converts
    // total amount from INR to target currency using Frankfurter API
    //Throws Exception if cart not found or empty
    @Transactional
    public OrderResponseDTO place(OrderRequestDTO dto) {
        //Fetch user's cart
        Cart cart = cartRepository.findByUserId(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Cart not found for user id: " + dto.getUserId()));

        // Check if cart is empty
        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cannot place order: Cart is empty");
        }

        //Calculate total amount in INR (base currency)
        double totalInr = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {
            totalInr += cartItem.getQuantity() * cartItem.getPriceAtTime();

            // Create order item from cart item
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtTime(cartItem.getPriceAtTime());
            orderItems.add(orderItem);
        }

        //Currency conversion using Frankfurter API
        Double convertedAmount = null;
        try {
            // Frankfurter API endpoint
            String url = String.format(
                    "https://api.frankfurter.app/latest?amount=%.2f&from=INR&to=%s",
                    totalInr,
                    dto.getCurrency().toUpperCase()
            );

            // Make external API call
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response != null && response.containsKey("rates")) {
                Map<String, Double> rates = (Map<String, Double>) response.get("rates");
                convertedAmount = rates.get(dto.getCurrency().toUpperCase());
            }

            if (convertedAmount == null) {
                throw new RuntimeException("Currency conversion failed - invalid currency code: " + dto.getCurrency());
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to convert currency: " + e.getMessage());
        }

        //Create Order entity
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setTotalAmount(totalInr);              // Amount in INR
        order.setConvertedAmount(convertedAmount);   // Amount in target currency
        order.setCurrency(dto.getCurrency().toUpperCase());
        // Status will be set to PENDING by @PrePersist

        // Associate order items with order
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
        }
        order.setOrderItems(orderItems);

        // Save order
        Order savedOrder = orderRepository.save(order);

        // Clear cart after successful order
        cart.getCartItems().clear();
        cartRepository.save(cart);

        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setOrderId(savedOrder.getId());
        responseDTO.setTotalAmount(savedOrder.getTotalAmount());
        responseDTO.setConvertedAmount(savedOrder.getConvertedAmount());
        responseDTO.setCurrency(savedOrder.getCurrency());
        responseDTO.setStatus(savedOrder.getStatus());

        // Add order items to response
        List<OrderItemResponseDTO> itemDTOs = new ArrayList<>();
        for (OrderItem item : savedOrder.getOrderItems()) {
            OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
            itemDTO.setOrderItemId(item.getId());
            itemDTO.setProductId(item.getProduct().getId());
            itemDTO.setProductName(item.getProduct().getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPriceAtTime(item.getPriceAtTime());
            itemDTOs.add(itemDTO);
        }
        responseDTO.setItems(itemDTOs);

        return responseDTO;
    }

    //Delete order, throw exception if order not found
    @Transactional
    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    //Update the order and throw exception if order not found
    @Transactional
    public OrderResponseDTO updateStatus(Long orderId, OrderStatus newStatus) {
        // Find the order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Update status
        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);

        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setOrderId(updatedOrder.getId());
        responseDTO.setTotalAmount(updatedOrder.getTotalAmount());
        responseDTO.setConvertedAmount(updatedOrder.getConvertedAmount());
        responseDTO.setCurrency(updatedOrder.getCurrency());
        responseDTO.setStatus(updatedOrder.getStatus());

        // Add order items to response
        List<OrderItemResponseDTO> itemDTOs = new ArrayList<>();
        for (OrderItem item : updatedOrder.getOrderItems()) {
            OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
            itemDTO.setOrderItemId(item.getId());
            itemDTO.setProductId(item.getProduct().getId());
            itemDTO.setProductName(item.getProduct().getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPriceAtTime(item.getPriceAtTime());
            itemDTOs.add(itemDTO);
        }
        responseDTO.setItems(itemDTOs);

        return responseDTO;
    }

    //get order by Id using jpa
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));


        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setOrderId(order.getId());
        responseDTO.setTotalAmount(order.getTotalAmount());
        responseDTO.setConvertedAmount(order.getConvertedAmount());
        responseDTO.setCurrency(order.getCurrency());
        responseDTO.setStatus(order.getStatus());

        // Add order items to response
        List<OrderItemResponseDTO> itemDTOs = new ArrayList<>();
        for (OrderItem item : order.getOrderItems()) {
            OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
            itemDTO.setOrderItemId(item.getId());
            itemDTO.setProductId(item.getProduct().getId());
            itemDTO.setProductName(item.getProduct().getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPriceAtTime(item.getPriceAtTime());
            itemDTOs.add(itemDTO);
        }
        responseDTO.setItems(itemDTOs);

        return responseDTO;
    }
}