package pack.order.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pack.order.model.Product;
import pack.order.model.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductRepository repository;

    // 전체 자료 읽기
    public List<Product> getDataAll() {
        List<Product> productlist = repository.findAll();
        logger.info("datas " + productlist.size());
        return productlist;
    }

    // 상품 ID로 상품 조회
    public Product getProductById(int productId) {
        return repository.findById(productId).orElse(null);
    }

    @Transactional
    // 재고 수량 업데이트
    public void updateStockQuantity(int productId, int quantity) {
        Product product = repository.findById(productId).orElse(null);
        if (product != null) {
            int updatedStockQuantity = product.getStockQuantity() - quantity;
            product.setStockQuantity(updatedStockQuantity);
            repository.save(product);
        }
    }
}
