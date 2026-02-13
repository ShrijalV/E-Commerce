package com.shrijal.e_commerce.controller;

import com.shrijal.e_commerce.dto.request.CartRequestDTO;
import com.shrijal.e_commerce.dto.response.CartResponseDTO;
import com.shrijal.e_commerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    //add product to cart
    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> add(@RequestBody CartRequestDTO dto) {
        CartResponseDTO response = cartService.add(dto);
        return ResponseEntity.ok(response);
    }

    //get cart
    @GetMapping("/{userId}")
    public ResponseEntity<CartResponseDTO> view(@PathVariable Long userId) {
        CartResponseDTO response = cartService.viewCart(userId);
        return ResponseEntity.ok(response);
    }

    //Remove cartItem from cart
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<String> remove(@PathVariable Long cartItemId) {
        cartService.removeItem(cartItemId);
        return ResponseEntity.ok("Item removed successfully");
    }
}
