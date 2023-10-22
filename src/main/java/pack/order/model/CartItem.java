package pack.order.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pack.customer.CustomerEntity;

@Entity
@Table(name = "cartitem")
@Getter
@Setter
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cartitemid")
	private int cartItemId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "productid")
	private Product product;

	@Column(name = "cartcount")
	private int cartCount;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customerid", referencedColumnName = "customerid")
	private CustomerEntity customers;


	public void increaseQuantity(int amount) {
		this.cartCount += amount;
	}

	public int calculateSubtotal() {
		return product.getPrice() * cartCount;
	}
}