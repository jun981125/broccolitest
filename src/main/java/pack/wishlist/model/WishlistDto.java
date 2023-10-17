package pack.wishlist.model;

import lombok.Data;

@Data
public class WishlistDto {
	private String brand, model, pimage, customerid;
	private int wishlistid, productid;
}
