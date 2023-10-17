package pack.payment.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentProductRepository  extends JpaRepository<ProductVo, Integer>{
	
	@Query(value = "select p from ProductVo as p where p.productid=?1")
	ProductVo Product_info(int productid);

	
	
}
