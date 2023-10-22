package pack.order.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pack.order.model.CartItem;
import pack.order.model.CartItemRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
	public static final String REDIRECT_CART = "redirect:/cart";

	@Autowired
	private CartService cartService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private OrderItemService orderItemService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CartItemRepository cartItemRepository;

	// 장바구니에 상품 추가하는 요청 처리
	@PostMapping("/addToCartItem")
	public String addToCart(@RequestParam("productid") int productid, @RequestParam("quantity") int quantity,
							HttpSession session) {
		String loginid = (String) session.getAttribute("loginid");
		if(loginid!=null) {
			cartService.addToCart(productid, quantity, loginid);
			return REDIRECT_CART;
		}else {
			return "redirect:/login";
		}
	}

	// 장바구니 화면 표시
	@GetMapping("/cart")
	public String viewCart(Model model, HttpSession session) {
		String loginid = (String) session.getAttribute("loginid");
		List<CartItem> cartItems = cartService.getcartitem(loginid);
		model.addAttribute("cartItems", cartItems);
		return "order/cart";
	}

	// 장바구니 비우기
	@PostMapping("/clearCart")
	public String clearCart() {
		cartItemRepository.deleteAll();
		return REDIRECT_CART;
	}

	// 선택상품 삭제
	@Transactional
	@PostMapping("/removeCartItem")
	public String removeCartItem(@RequestParam("cartItemId") int cartItemId) {
		cartItemService.removeCartItem(cartItemId);
		return REDIRECT_CART;
	}
	
	@PostMapping(value = "/updateCartCount")
	public String updateCartCount(@RequestParam("orderCount") int updateCount,@RequestParam("cartItemId") int cartItemId) {
	    CartItem cartItem=cartItemRepository.findCartItemByCartItemId(cartItemId);
	    cartItem.setCartCount(updateCount);
		return REDIRECT_CART; // 다시 카트 페이지로 리다이렉트
	}

	// 장바구니에서 주문 페이지로 넘어가기
	@GetMapping("/createOrder")
	public String createOrder(Model model) {
		ArrayList<CartItem> cartItemlist = (ArrayList<CartItem>) cartService.getDataAll();
		model.addAttribute("cartItems", cartItemlist);
        double totalSubtotal = cartItemlist.stream().mapToDouble(CartItem::calculateSubtotal).sum();
        System.out.println("Total Subtotal: " + totalSubtotal); // 이 부분을 추가합니다.
		model.addAttribute("cartItems", cartItemlist);
		model.addAttribute("totalSubtotal", totalSubtotal);
		return "order/cartform"; // 주문 확인 페이지로 이동
	}

	// 상품정보에서 주문페이지로 넘어가기
	@PostMapping("/createOrderfromProduct")
	public String addToOrderfromProduct(@RequestParam("productid") int productid,
			@RequestParam("quantity") int quantity, Model model, HttpSession session) {
		String loginid = (String) session.getAttribute("loginid");
		cartService.addToCart(productid, quantity, loginid);
		ArrayList<CartItem> cartItemlist = (ArrayList<CartItem>) cartService.getDataAll();
        double totalSubtotal = cartItemlist.stream().mapToDouble(CartItem::calculateSubtotal).sum();
        System.out.println("Total Subtotal: " + totalSubtotal); // 이 부분을 추가합니다.
        session.getAttribute("loginid");
        
		model.addAttribute("cartItems", cartItemlist);
		model.addAttribute("totalSubtotal", totalSubtotal);
		return "order/cartform";
	}

}
