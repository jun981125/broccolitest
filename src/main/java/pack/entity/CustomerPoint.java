package pack.entity;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
@Table(name = "customerpoint")
@Getter
@Setter
public class CustomerPoint {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pointid;

    @ManyToOne(cascade = CascadeType.PERSIST) // CascadeType 설정
    @JoinColumn(name = "customernum")
    private CustomerEntity customer;

    private LocalDate orderdate;
    private int orderamount;
}
