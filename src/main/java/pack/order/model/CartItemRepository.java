package pack.order.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    // 특정 제품을 가지고 있는 카트 아이템들 조회
    List<CartItem> findByProduct(Product product);

    // 카트 아이템을 CartItemId 역순으로 조회
    List<CartItem> findByCustomersCustomeridOrderByCartItemIdDesc(String customerid);


    CartItem findCartItemByCartItemId(int cartItemId);

}
