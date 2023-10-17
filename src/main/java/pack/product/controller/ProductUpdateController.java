package pack.product.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import pack.product.model.ProductDao;
import pack.product.model.ProductDto;

@Controller
public class ProductUpdateController {
	@Autowired
	private ProductDao productDao;

	@GetMapping("productupdate")
	public String edit(@RequestParam("productid") int productid, @RequestParam("page") String page,
			@RequestParam("pimage") String pimage, @RequestParam("dimage") String dimage, Model model) {
		// 수정 대상 자료 읽기
		ProductDto dto = productDao.detail(productid);

		model.addAttribute("data", dto);
		model.addAttribute("page", page);
		model.addAttribute("dimage", dimage);
		model.addAttribute("pimage", pimage);

		return "product/productupdate";
	}

	@PostMapping("productupdate")
	public String editProcess(@RequestParam("productid") int productid, @RequestParam("page") String page,
			@RequestParam("pimage") String pimage, @RequestParam("dimage") String dimage, ProductBean bean,
			BindingResult result, Model model) {

		InputStream inputStream1 = null;
		InputStream inputStream2 = null;
		OutputStream outputStream1 = null;
		OutputStream outputStream2 = null;

		MultipartFile file1 = bean.getPimagePath();
		MultipartFile file2 = bean.getDimagePath();
		String orifilename1 = file1.getOriginalFilename();
		String orifilename2 = file2.getOriginalFilename();
		if (orifilename1 != "") {
			String uuid1 = UUID.randomUUID().toString();
			String filename1 = uuid1 + file1.getOriginalFilename();
			if (result.hasErrors()) {
				return "err";
			}
			try {
				inputStream1 = file1.getInputStream();
				File newFile1 = new File(
						"/Users/heojunho/work/shop/shop/broccoli/src/main/resources/static/upload/" + filename1); // 절대경로로
																															// 찍기
				if (!newFile1.exists()) {
					newFile1.createNewFile();
				}
				outputStream1 = new FileOutputStream(newFile1);
				int read = 0;
				byte[] bytes = new byte[1024]; // -1 끝을 의미
				while ((read = inputStream1.read(bytes)) != -1) {
					outputStream1.write(bytes, 0, read);
				}
				bean.setPimage(filename1);
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				try {
					inputStream1.close();
					outputStream1.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		if (orifilename2 != "") {
			String uuid2 = UUID.randomUUID().toString();
			String filename2 = uuid2 + file2.getOriginalFilename();
			if (result.hasErrors()) {
				return "err";
			}

			try {
				inputStream2 = file2.getInputStream();
				File newFile2 = new File(
						"/Users/heojunho/work/shop/shop/broccoli/src/main/resources/static/upload/" + filename2);
				if (!newFile2.exists()) {
					newFile2.createNewFile();
				}
				outputStream2 = new FileOutputStream(newFile2);
				int read = 0;
				byte[] bytes = new byte[1024]; // -1 끝을 의미

				while ((read = inputStream2.read(bytes)) != -1) {
					outputStream2.write(bytes, 0, read);
				}

				bean.setDimage(filename2);
			} catch (Exception e) {
				System.out.println("file submit err : " + e);
			} finally {
				try {
					inputStream2.close();
					outputStream2.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		boolean b = productDao.update(bean);
		if (b) {
			return "redirect:productlist";
		} else {
			return "redirect:error";
		}
	}
	
	// 상품이미지 삭제(따로)
	@GetMapping("pimagedelete")
	public String pimagedelete(@RequestParam("productid") int productid, Model model) {
	    // 기존 이미지 파일명 가져오기
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

	    // 데이터베이스에서 이미지 정보 삭제
	    productDao.pimagedelete(productid);

	    return "redirect:productlist_seller";
	}
	
	// 상세이미지 삭제(따로)
	@GetMapping("dimagedelete")
	public String dimagedelete(@RequestParam("productid") int productid, Model model) {
	    // 기존 이미지 파일명 가져오기
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

	    // 데이터베이스에서 이미지 정보 삭제
	    productDao.dimagedelete(productid);

	    return "redirect:productlist_seller";
	}
	

	// 판매자 전체 상품 상태 변경 - 관리자
	    @PostMapping("/all_productlist")
	    public String AllUpdateState(@RequestParam("productid") int productId, @RequestParam("state") String state, Model model) {
	        ProductBean productBean = new ProductBean();
	        productBean.setProductid(productId);
	        productBean.setState(state);
	        
	        boolean isSuccess = productDao.updateProductState(productBean);
	        return "redirect:/all_productlist";
	    }
	    

	    // 승인대기중 상품 상태 변경 - 관리자
	    @PostMapping("/waiting_productlist")
	    public String WaitState(@RequestParam("productid") int productId, @RequestParam("state") String state, Model model) {
	        ProductBean productBean = new ProductBean();
	        productBean.setProductid(productId);
	        productBean.setState(state);
	        boolean isSuccess = productDao.updateProductState(productBean);
	        return "redirect:/waiting_productlist";
	    }


	
	
	
}
