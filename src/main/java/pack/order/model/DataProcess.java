package pack.order.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.order.model.ProductRepository;
import pack.order.model.Product;
@Repository
public class DataProcess {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private ProductRepository repository;
	
    // 전체 자료 읽기 
    public List<Product> getDataAll(){
        List<Product> list = repository.findAll();
        logger.info("datas " + list.size());
        return list;
    }
}
