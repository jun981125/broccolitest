package pack.order.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pack.order.model.Cart;
import pack.order.model.CartItem;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    // 특정 카트에 속한 카트 아이템들 조회
    List<CartItem> findByCart(Cart cart);

    // 특정 제품을 가지고 있는 카트 아이템들 조회
    List<CartItem> findByProduct(Product product);

    // 카트 아이템을 CartItemId 역순으로 조회
    List<CartItem> findAllByOrderByCartItemIdDesc();
    
    CartItem findCartItemByCartItemId(int cartItemId);

}
