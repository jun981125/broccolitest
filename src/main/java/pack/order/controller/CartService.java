package pack.order.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pack.order.model.Cart;
import pack.order.model.CartItem;
import pack.order.model.Product;
import pack.order.model.CartItemRepository;
import pack.order.model.CartRepository;
import pack.order.model.ProductRepository;

@Service
@RequiredArgsConstructor
public class CartService {


	private final CartItemRepository cartItemRepository;


	private final ProductRepository productRepository;


	private final CartRepository cartRepository;
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


	@Transactional
	// 장바구니에 상품 추가
	public void addToCart(int productId, int quantity) {
		Product product = productRepository.findById(productId).orElse(null);

		if (product != null) {
			CartItem existingCartItem = getCartItemByProduct(product);

			if (existingCartItem != null) {
				existingCartItem.increaseQuantity(quantity);
			} else {
				CartItem cartItem = new CartItem();
				cartItem.setProduct(product);
				cartItem.setCartCount(quantity);
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
	public List<CartItem> getCartItemsCartByCartItemIdDesc() {
		return cartItemRepository.findAllByOrderByCartItemIdDesc();
	}

	// Cart 생성 및 반환
	public Cart createCart(Cart cart) {
		return cartRepository.save(cart);
	}

	// 전체 자료 읽기
	public List<CartItem> getDataAll() {
		List<CartItem> cartItemlist = cartItemRepository.findAll();
		logger.info("cartItems " + cartItemlist.size());
		return cartItemlist;
	}

}
