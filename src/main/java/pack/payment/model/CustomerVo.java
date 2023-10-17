package pack.payment.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="customers")
public class CustomerVo {

	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 	private int customernum;
	 	private Long customerid;

	    private String customername;
	    private String customernickname;
	    private String email;
	    private String passwordhash;
	    private String phonenumber;
	    private String zipcode;
	    private String detailaddress;
	    private String address;
	    private Date regdate;
	    private Boolean isseller;
}


