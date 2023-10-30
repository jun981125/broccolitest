package pack.product.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
@Component
public class ProductBean {
	private int productid, reviewid, customernum,stockquantity;
	private String category, brand, model, price, pimage, dimage, state, customerid;
	// pimage, dimge는 이미지 파일명
	

	private String searchName, searchValue;
	

	private MultipartFile pimagePath, dimagePath;
	// 이미지 조회경로

}
