package pack.order.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pack.customer.CustomerEntity;
import pack.order.model.Product;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderid")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "customernum")
    private CustomerEntity customer;
    
    @ManyToOne
    @JoinColumn(name = "productid")
    private Product product;
    
    @Column(name = "orderdate")
    private Date orderDate;

    @Column(name = "orderstatus")
    private String orderStatus;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
    
    // 주문 항목에서 특정 상품을 찾아 반환하는 메서드
    public OrderItem getOrderItemByProductId(int productId) {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getProductId() == productId) {
                return orderItem;
            }
        }
        return null; // 해당 상품이 주문에 없을 경우 null을 반환
    }
    
    // 주문 날짜 설정 메서드
    public void setOrderDate(Date date) {
        this.orderDate = date;
    }

}
