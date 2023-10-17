package pack.payment.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDataProcess {
	
private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PaymentProductRepository repository;
	
	public ProductVo getProductData(int productid){
		ProductVo vo = repository.Product_info(productid);
		return vo;
	}
}
