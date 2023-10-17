package pack.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pack.order.model.OrderItem;
import pack.order.model.OrderItemRepository;

@Service
public class OrderItemService {
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	// OrderItem 생성 및 반환
	public OrderItem createOrderItem(OrderItem orderItem) {
        // 다른 로직을 수행하고 필요한 경우 데이터를 저장하거나 수정하는 등의 작업을 수행할 수 있습니다.

        return orderItemRepository.save(orderItem); // 저장된 OrderItem 객체를 반환합니다.
    }
}
