package pack.product.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pack.product.model.ProductDao;
import pack.review.model.ReviewDao;

@Controller
public class ProductDeleteController {
	@Autowired
	private ProductDao productDao;

	@PostMapping("/productdelete")
	public String delete(@RequestParam("productid") int productid) {
		
		// 기존 상품 이미지 파일명 가져오기
	    String pimageFilename = productDao.pimagename(productid); // productDao.getImageFilename 메소드는 상품의 이미지 파일명을 가져오는 메소드로 가정합니다.

	    // 파일 삭제 처리
	    if (pimageFilename != null) {
	        String uploadPath = "/Users/heojunho/work/shop/shop/broccoli/src/main/resources/static/upload/";
	        File pimageFile = new File(uploadPath + pimageFilename);

	        // 파일이 존재하면 삭제
	        if (pimageFile.exists()) {
	            if (pimageFile.delete()) {
	                System.out.println("파일 삭제 성공");
	            } else {
	                System.out.println("파일 삭제 실패");
	            }
	        }
	    }
	    
	    // 기존 상세 이미지 파일명 가져오기
	    String dimageFilename = productDao.dimagename(productid); 

	    // 파일 삭제 처리
	    if (dimageFilename != null) {
	        String uploadPath = "/Users/heojunho/work/shop/shop/broccoli/src/main/resources/static/upload/";
	        File dimageFile = new File(uploadPath + dimageFilename);

	        // 파일이 존재하면 삭제
	        if (dimageFile.exists()) {
	            if (dimageFile.delete()) {
	                System.out.println("파일 삭제 성공");
	            } else {
	                System.out.println("파일 삭제 실패");
	            }
	        }
	    }
		// product를 참고하는 다른 테이블있다면 거기서도 특정 productId의 데이터들을 삭제한 뒤 진행해야함
		productDao.deletereviewfromproduct(productid); // reviews가 products의 외래키를 참조하기 때문에 특정 productId에 해당하는 reviews을 먼저 지우고 products을 지워야함
		productDao.delete(productid);
		return "redirect:productlist";
	}

}
