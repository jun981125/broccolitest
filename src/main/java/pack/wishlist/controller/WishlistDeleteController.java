package pack.wishlist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.wishlist.model.WishlistDao;

@Controller
public class WishlistDeleteController {
	@Autowired
	private WishlistDao wishlistDao;
	
	@GetMapping("deletewishlist")
	public String deletewishlist(@RequestParam("wishlistid") int wishlistid,
			@RequestParam("page") int page) {
		if(wishlistDao.deletewishlist(wishlistid))
			return "redirect:wishlist?page=" +page;
		else
			return "redirect:error";
	}
}
