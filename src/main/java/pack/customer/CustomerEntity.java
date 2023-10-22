package pack.customer;



import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customers")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class CustomerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customernum;
    
    @Column(unique = true)
    private String customerid;

    private String customername;
    
    private String customernickname;
    
    private String email;
    
    private String passwordhash;
    
    private String customergen;
    
    private String phonenumber;

    private String zipcode;
    
    private String detailaddress;

    private String address;
    
    private boolean active;
    
    @CreatedDate
    @Column(name = "regdate")
    private LocalDateTime regdate;
    
    private boolean isseller; 
    
    public static CustomerEntity tocuCustomerEntity(CustomerDto customerDto) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerid(customerDto.getCustomerid());
        customerEntity.setCustomername(customerDto.getCustomername());
        customerEntity.setCustomernickname(customerDto.getCustomernickname());
        customerEntity.setEmail(customerDto.getEmail());
        customerEntity.setPasswordhash(customerDto.getPasswordhash());
        customerEntity.setCustomergen(customerDto.getCustomergen());
        customerEntity.setPhonenumber(customerDto.getPhonenumber());
        customerEntity.setZipcode(customerDto.getZipcode());
        customerEntity.setDetailaddress(customerDto.getDetailaddress());
        customerEntity.setAddress(customerDto.getAddress());
        customerEntity.setIsseller(customerDto.getIsseller());
        return customerEntity;
    }
    
    public static CustomerEntity toUpdateCustomerEntity(CustomerDto customerDto) {
    	CustomerEntity customerEntity = new CustomerEntity();
    	customerEntity.setCustomernum(customerDto.getCustomernum());
    	customerEntity.setCustomerid(customerDto.getCustomerid());
    	customerEntity.setCustomername(customerDto.getCustomername());
    	customerEntity.setCustomernickname(customerDto.getCustomernickname());
    	customerEntity.setEmail(customerDto.getEmail());
    	customerEntity.setPasswordhash(customerDto.getPasswordhash());
    	customerEntity.setCustomergen(customerDto.getCustomergen());
    	customerEntity.setPhonenumber(customerDto.getPhonenumber());
    	customerEntity.setZipcode(customerDto.getZipcode());
    	customerEntity.setDetailaddress(customerDto.getDetailaddress());
    	customerEntity.setAddress(customerDto.getAddress());
        return customerEntity;
    }
    
    public static CustomerEntity toCustomerEntity(CustomerDto customerDto) {
    	CustomerEntity customerEntity = new CustomerEntity();
    	customerEntity.setCustomernum(customerDto.getCustomernum());
    	customerEntity.setCustomerid(customerDto.getCustomerid());
    	customerEntity.setCustomername(customerDto.getCustomername());
    	customerEntity.setCustomernickname(customerDto.getCustomernickname());
    	customerEntity.setEmail(customerDto.getEmail());
    	customerEntity.setPasswordhash(customerDto.getPasswordhash());
    	customerEntity.setCustomergen(customerDto.getCustomergen());
    	customerEntity.setPhonenumber(customerDto.getPhonenumber());
    	customerEntity.setZipcode(customerDto.getZipcode());
    	customerEntity.setDetailaddress(customerDto.getDetailaddress());
    	customerEntity.setAddress(customerDto.getAddress());
        customerEntity.setIsseller(customerDto.getIsseller());
        return customerEntity;
    }
    
    
}