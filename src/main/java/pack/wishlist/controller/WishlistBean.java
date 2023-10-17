package pack.wishlist.controller;

import lombok.Data;

@Data
public class WishlistBean {
	private String brand, model, pimage, customerid;
	private int wishlistid, productid;
}
