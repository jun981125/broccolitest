package pack.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pack.order.model.CartItem;
import pack.order.model.CartItemRepository;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    // 카트 아이템 ID로 카트 아이템 조회
    public CartItem getCartItemById(int cartItemId) {
        return cartItemRepository.findById(cartItemId).orElse(null);
    }

    
    // 카트 아이템 삭제
    public void removeCartItem(int cartItemId) {
        // cartItemId를 사용하여 해당 카트 아이템을 찾아서 삭제합니다.
        cartItemRepository.deleteById(cartItemId);
    }
    
    
}
