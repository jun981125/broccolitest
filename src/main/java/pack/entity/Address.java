package pack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import pack.customer.CustomerEntity;

@Entity
@Data
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressid;

    @ManyToOne
    @JoinColumn(name = "customernum")
    private CustomerEntity customer;

    private String zipcode;
    private String address;
    private String detailaddress;
    private String reference;
    private boolean isdefault;

}
