package pack.order.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.customer.CustomerEntity;
import pack.customer.CustomerRepository;
import pack.order.model.*;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

	private final ProductRepository productRepository;

	private final CartItemRepository cartItemRepository;

	private final CustomerRepository customerRepository;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	@Transactional
	// 장바구니에 상품 추가
	public void addToCart(int productId, int quantity, String loginid) {
		Product product = productRepository.findById(productId).orElse(null);
		Optional<CustomerEntity> customerOptional = customerRepository.findByCustomerid(loginid);


		if (product != null && customerOptional.isPresent()) {
			CartItem existingCartItem = getCartItemByProduct(product);

			if (existingCartItem != null) {
				existingCartItem.increaseQuantity(quantity);
			} else {
				CartItem cartItem = new CartItem();
				cartItem.setProduct(product);
				cartItem.setCartCount(quantity);
				CustomerEntity customerid = customerOptional.get();
				cartItem.setCustomers(customerid);
				cartItemRepository.save(cartItem);
			}
		}
	}

	// 특정 상품을 가진 CartItem 반환
	private CartItem getCartItemByProduct(Product product) {
		List<CartItem> cartItems = cartItemRepository.findByProduct(product);

		if (!cartItems.isEmpty()) {
			return cartItems.get(0);
		} else {
			return null;
		}
	}

	// CartItem을 CartItemId의 역순으로 반환
	public List<CartItem> getcartitem(String customerid) {
		return cartItemRepository.findByCustomersCustomeridOrderByCartItemIdDesc(customerid);
	}


	// 전체 자료 읽기
	public List<CartItem> getDataAll() {
		List<CartItem> cartItemlist = cartItemRepository.findAll();
		logger.info("cartItems " + cartItemlist.size());
		return cartItemlist;
	}

	public CartItem getCartItemById(int cartItemId) {
		Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
		return cartItemOptional.orElse(null);
	}

	public void saveCartItem(CartItem cartItem) {
		cartItemRepository.save(cartItem);
	}
}

