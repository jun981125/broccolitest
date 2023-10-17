package pack.order.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pack.order.model.Cart;
import pack.order.model.CartItem;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    // CartId로 카트 조회
    Cart getCartByCartId(int CartId);

    // CartId에 해당하는 카트 아이템들 조회
    List<CartItem> findCartItemsByCartId(int cartId);
}
