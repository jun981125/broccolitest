package pack.product.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductDto {
	private int productid, reviewid, customernum, stockquantity;
	private String category, brand, model, price, pimage, dimage, state, customerid;
// pimage, dimge는 이미지 파일명

 private MultipartFile pimagePath, dimagePath;
// 이미지 조회경로
}
