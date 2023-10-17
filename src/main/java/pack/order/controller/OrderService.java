package pack.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pack.order.model.Order;
import pack.order.model.OrderItem;
import pack.order.model.Product;
import pack.order.model.OrderItemRepository;
import pack.order.model.OrderRepository;
import pack.order.model.ProductRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private ProductRepository productRepository;

	@Transactional
	// 주문에 상품 추가
	public void addToOrder(int productId, int quantity,Order order) {
		Product product = productRepository.findById(productId).orElse(null);

		if (product != null) {
			if (product.getStockQuantity() >= quantity) {
				OrderItem orderItem = new OrderItem();
				orderItem.setProduct(product);
				orderItem.setOrder(order);
				orderItem.setOrderCount(quantity);
				orderItem.setSubtotal(product.getPrice()*quantity);
				product.setStockQuantity(product.getStockQuantity() - quantity);
				productRepository.save(product);
				orderItemRepository.save(orderItem);
			} else {
				throw new RuntimeException("재고가 부족합니다.");
			}
		}

	}
	
	// 주문 아이템을 OrderItemId의 역순으로 반환
	public List<OrderItem> getOrderItemsOrderByOrderItemIdDesc() {
	    return orderItemRepository.findAllByOrderByOrderItemIdDesc();
	}

	// Order 생성 및 반환
	public Order createOrder(Order order) {
        // 다른 로직을 수행하고 필요한 경우 데이터를 저장하거나 수정하는 등의 작업을 수행할 수 있습니다.
        return orderRepository.save(order); // 저장된 Order 객체를 반환합니다.
    }

	// 모든 주문 아이템 목록 반환
	public List<OrderItem> getOrderItems() {
		return orderItemRepository.findAll();
	}

	// 특정 상품의 주문 아이템 수량 반환
	public int getOrderItemQuantity(Order order, int productId) {
		OrderItem orderItem = order.getOrderItemByProductId(productId);
		return orderItem != null ? orderItem.getOrderCount() : 0;
	}

	// 특정 주문의 주문 아이템 목록 반환
	public List<OrderItem> getOrderItemsByOrderId(int orderId) {
		Order order = orderRepository.getOrderByOrderId(orderId);
		return orderRepository.findOrderItemsByOrderId(orderId);
	}
}
