package pack.order.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pack.customer.CustomerEntity;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartid")
    private int cartId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customernum")
    private CustomerEntity customer;
    
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    public CartItem getCartItemByProductId(int productId) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().equals(productId)) {
                return cartItem;
            }
        }
        return null; // 해당 상품이 장바구니에 없을 경우 null을 반환
    }
  
}
