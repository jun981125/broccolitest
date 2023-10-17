package pack.order.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pack.order.model.Order;
import pack.order.model.OrderItem;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    // 주문 ID로 주문 조회
    Order getOrderByOrderId(int orderId);

    // 주문 ID에 해당하는 주문 아이템들 조회
    List<OrderItem> findOrderItemsByOrderId(int orderId);
}
