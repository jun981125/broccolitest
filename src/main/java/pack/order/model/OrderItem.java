package pack.order.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orderitem")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderitemid")
    private int orderItemId;

    @ManyToOne
    @JoinColumn(name = "orderid")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productid")
    private Product product;

    @Column(name = "ordercount")
    private int orderCount;

    @Column(name = "subtotal")
    private Integer subtotal;

    // 주문 항목의 소계 계산 메서드
    public int calculateSubtotal() {
        return product.getPrice() * orderCount;
    }
}
