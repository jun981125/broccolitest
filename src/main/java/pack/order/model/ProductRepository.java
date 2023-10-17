package pack.order.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pack.order.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // 제품 ID로 제품 조회
    Product findByProductId(int productId);

}
