package pack.order.controller;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import pack.order.model.CartItem;
import pack.order.model.Order;
import pack.order.model.OrderItem;
import pack.order.model.Product;
import pack.order.model.CartItemRepository;
import pack.order.controller.CartService;
import pack.order.controller.OrderService;

@Controller
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	private final CartItemRepository cartItemRepository;
	
	private final CartService cartService;
	
	@PostMapping("/processOrder")
	public String processOrder(Model model) {
		List<CartItem> cartItems = cartService.getDataAll();
		
		for (CartItem cartItem : cartItems) {
			Product product = cartItem.getProduct();
			Order order = new Order();
			order.setOrderDate(new Date());
			order.setProduct(product);
			order.setOrderStatus("결제완료");
			order = orderService.createOrder(order);
			try {
				orderService.addToOrder(product.getProductId(), cartItem.getCartCount(),order);
				cartItemRepository.deleteById(cartItem.getCartItemId());
			} catch (RuntimeException e) {
				String errorMessage = e.getMessage();
				model.addAttribute("errorMessage", errorMessage);
			}
		}
		return "redirect:/order"; // 적절한 뷰로 리턴
	}

	// 주문내역 화면 표시
	@GetMapping("/order")
	public String viewOrder(Model model) {
		List<OrderItem> orderItems = orderService.getOrderItemsOrderByOrderItemIdDesc();
		model.addAttribute("orderItems", orderItems);
		return "order/order";
	}

}
