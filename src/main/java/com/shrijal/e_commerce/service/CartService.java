package com.shrijal.e_commerce.service;

import com.shrijal.e_commerce.dto.request.CartRequestDTO;
import com.shrijal.e_commerce.dto.response.CartItemResponseDTO;
import com.shrijal.e_commerce.dto.response.CartResponseDTO;
import com.shrijal.e_commerce.model.Cart;
import com.shrijal.e_commerce.model.CartItem;
import com.shrijal.e_commerce.model.Product;
import com.shrijal.e_commerce.repository.CartItemRepository;
import com.shrijal.e_commerce.repository.CartRepository;
import com.shrijal.e_commerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for Cart operations
 * Handles business logic for shopping cart management
 */
@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    //Adding item to cart
    @Transactional
    public CartResponseDTO add(CartRequestDTO dto) {
        // Find user's cart
        Cart cart = cartRepository.findByUserId(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Cart not found for user id: " + dto.getUserId()));

        // Find product
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + dto.getProductId()));

        // Check if product already in cart
        boolean productExists = false;
        for (CartItem existingItem : cart.getCartItems()) {
            if (existingItem.getProduct().getId().equals(dto.getProductId())) {
                // Update quantity if product already in cart
                existingItem.setQuantity(existingItem.getQuantity() + dto.getQuantity());
                cartItemRepository.save(existingItem);
                productExists = true;
                break;
            }
        }

        // If product not in cart, create new cart item
        if (!productExists) {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(dto.getQuantity());
            item.setPriceAtTime(product.getPrice());
            cartItemRepository.save(item);
        }

        // Return updated cart
        return viewCart(dto.getUserId());
    }

    /**
     * View cart contents
     * @param userId - user ID
     * @return CartResponseDTO with all items and total
     * @throws RuntimeException if cart not found
     */
    public CartResponseDTO viewCart(Long userId) {
        // Find user's cart
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user id: " + userId));

        // Build response DTO
        List<CartItemResponseDTO> itemDTOList = new ArrayList<>();
        double total = 0.0;

        for (CartItem item : cart.getCartItems()) {
            CartItemResponseDTO itemDTO = new CartItemResponseDTO();
            itemDTO.setId(item.getId());
            itemDTO.setProductId(item.getProduct().getId());
            itemDTO.setProductName(item.getProduct().getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPriceAtTime(item.getPriceAtTime());

            total += item.getQuantity() * item.getPriceAtTime();
            itemDTOList.add(itemDTO);
        }

        CartResponseDTO response = new CartResponseDTO();
        response.setCartId(cart.getId());
        response.setItems(itemDTOList);
        response.setTotalAmount(total);

        return response;
    }

    /**
     * Remove item from cart
     * @param cartItemId - cart item ID to remove
     * @throws RuntimeException if cart item not found
     */
    @Transactional
    public void removeItem(Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new RuntimeException("Cart item not found with id: " + cartItemId);
        }
        cartItemRepository.deleteById(cartItemId);
    }
}
