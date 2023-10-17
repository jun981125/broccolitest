package pack.payment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="products")
public class ProductVo {

	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 	private int productid;
	 	private String productbrand;

	    private String productmodel;
	    private int productprice;
	    private int stockquantity;
	    }
