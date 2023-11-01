package pack.product.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import pack.product.controller.ProductBean;

@Repository
public class ProductDao {
	// log로 확인
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductMappingInterface mappingInterface;
	
	// 상품 전체 보기
	public List<ProductDto> selectMain() {
		List<ProductDto> list = mappingInterface.selectMain();
		logger.info("읽은 상품 수 : " + list.size());
		return list;
	}

	// 상품 관리 - 판매자
	public List<ProductDto> selectAll(String customerid) {
		List<ProductDto> list = mappingInterface.selectAll(customerid);
		logger.info("읽은 상품 수 : " + list.size());
		return list;
	}

	// 해당 상품 상세 보기
	public ProductDto detail(int productid) {
		ProductDto dto = mappingInterface.selectOne(productid);
		return dto;
	}
	
	// 카테고리별 상품 보기
	public List<ProductDto> selectCatogory(String category) {
		List<ProductDto> list = mappingInterface.selectCategory(category);
		logger.info("읽은 카테고리 별 상품 수 : " + list.size());
		return list;
	}
	
	// 브랜드별 상품 보기
	public List<ProductDto> selectBrnad(String brand) {
		List<ProductDto> list = mappingInterface.selectBrand(brand);
		logger.info("읽은 브랜드 별 상품 수 : " +list.size());
		return list;
	}
	
	// 메인페이지에 상품 
	public List<ProductDto> selecteight() {
		List<ProductDto> list = mappingInterface.selecteight();
		return list;
	}
	
	// 카테고리 & 브랜드 상품 목록
	public List<ProductDto> selectCateBran(String category, String brand) {
	    List<ProductDto> list = mappingInterface.selectCateBran(category, brand); // 메서드 이름 수정
	    logger.info("읽은 카테고리 별 브랜드 별 상품 수 : " + list.size());
	    return list;
	}

	
	public int totalallCnt() {
		return mappingInterface.totalallCnt();
	}
	
	public int totalsellerCnt(String customerid) {
		return mappingInterface.totalsellerCnt(customerid);
	}
	
	public int totalcateCnt(String category) {
		return mappingInterface.totalcateCnt(category);
	}
	
	public int totalbrandCnt(String brand) {
		return mappingInterface.totalbrandCnt(brand);
	}
	
	public int totalcatebranCnt(String category, String brand) {
		return mappingInterface.totalcatebranCnt(category, brand);
	}

	// 상품 찾기
	public List<ProductDto> search(ProductBean bean) {
		List<ProductDto> list = mappingInterface.searchList(bean);
		logger.info("검색 반환된 레코드 수 : " + list.size());
		return list;
	}

	// 상품 등록
	public boolean insert(ProductBean bean) {
		boolean b = false;
	    int re = mappingInterface.insertProduct(bean);
	    if(re>0) b = true;
		return b;
	}

	// 상품 수정
	public boolean update(ProductBean bean) {
		boolean b = false;
		int re = mappingInterface.updateProduct(bean);
		if (re > 0)
			b = true;
		return b;
	}
	
	// 상품이미지 삭제
	public boolean pimagedelete(int productid) {
		boolean b = false;
		int re = mappingInterface.pimagedelete(productid);
		if (re > 0)
			b = true;
		return b;
	}
	
	// 상품이미지 이름
	public String pimagename(int productid) {
		String b = mappingInterface.pimagename(productid);
		return b;
	}
	
	// 상세이미지 삭제
	public boolean dimagedelete(int productid) {
		boolean b = false;
		int re = mappingInterface.dimagedelete(productid);
		if (re > 0)
			b = true;
		return b;
	}
	
	// 상세이미지 이름
	public String dimagename(int productid) {
		String b = mappingInterface.dimagename(productid);
		return b;
	}
	
	// 상품 삭제
	public boolean delete(int productid) {
		boolean b = false;
		int re = mappingInterface.deleteProduct(productid);
		if (re > 0)
			b = true;
		return b;
	}
	// 특정 상품에 관한 리뷰 전체 삭제
	public boolean deletereviewfromproduct(int productid) {
		boolean b = false;
		int re = mappingInterface.deletereviewfromproduct(productid);
		if (re > 0)
			b = true;
		return b;
	}
	
	// 전체 판매자의 전체 상품 리스트
		public List<ProductDto> selectAllProducts() {
			return mappingInterface.selectAllProducts();
		}

	// ProductDao



	public List<ProductDto> selectMainProducts(int pagenum) {
		List<ProductDto> a = mappingInterface.selectMainProducts(pagenum);
		return a;
	}




	// 전체 판매자의 전체 상품 리스트
		public List<ProductDto> selectWaitProducts() {
			return mappingInterface.selectWaitProducts();
		}
		
		// 판매자 상품 상태 변경 - 관리자
	    public boolean updateProductState(ProductBean bean) {
	        boolean b = false;
	        int re = mappingInterface.updateProductState(bean);
	        if (re > 0) b = true;
	        return b;
	    }
}
