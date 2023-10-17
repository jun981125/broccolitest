package pack.order.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pack.order.model.Order;
import pack.order.model.OrderItem;
import pack.order.model.Product;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    // 특정 주문에 속한 주문 아이템들 조회
    List<OrderItem> findByOrder(Order order);

    // 특정 제품을 가지고 있는 주문 아이템들 조회
    List<OrderItem> findByProduct(Product product);

    // 주문 아이템 ID로 주문 아이템 조회
    OrderItem getOrderItemByOrderItemId(int orderItemId);

    // 제품 ID로 주문 아이템 조회
    OrderItem findByProductProductId(int productId);

    // 주문 아이템을 OrderItemId 역순으로 조회
    List<OrderItem> findAllByOrderByOrderItemIdDesc();
}
