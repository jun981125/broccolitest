package pack.review.controller;

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

import pack.review.model.ReviewDao;
import pack.review.model.ReviewDto;

@Controller
public class ReviewUpdateController {
	@Autowired
	private ReviewDao reviewDao;
	
	@GetMapping("reviewupdate")
	public String edit(@RequestParam("reviewid") int reviewid, @RequestParam("page")String page, @RequestParam("rimage") String rimage, Model model) {
		// 수정 대상 자료 읽기
		ReviewDto dto = reviewDao.detail(reviewid);
		
		model.addAttribute("data", dto);
		model.addAttribute("page", page);
		model.addAttribute("rimage",rimage);
		
		return "reviewupdate";
	}
	// 사진을 입력하지 않으면 원래 사진 그대로 남아있게 함
	@PostMapping("reviewupdate")
	public String editProcess(@RequestParam("reviewid") int reviewid, @RequestParam("page")String page, 
			@RequestParam("rimage") String rimage, ReviewBean bean,BindingResult result,ReviewDto dto, Model model) {
			bean.setReviewdate();
			
			InputStream inputStream = null;
		    OutputStream outputStream = null;
		   
	        MultipartFile file = bean.getRimagePath();
	        String orifilename = file.getOriginalFilename();
	        
	        bean.setRimage(rimage);
	        // 파일이 null이 아니라면 이미지 업로드 진행
	        if (orifilename != "") { 
	        	// 이름중복을 방지하기 위해 난수 발생시킴
	        	String uuid=UUID.randomUUID().toString(); 
	   	        String filename = uuid+file.getOriginalFilename();
	      
	        
	        // 유효성 검사 결과 확인
		    if (result.hasErrors()) {
		        return "err";
		    }

		    try {
		    	
		        inputStream = file.getInputStream();
		        File newFile = new File("/Users/heojunho/work/shop/shop/broccoli/src/main/resources/static/upload/" + filename);
		        if (!newFile.exists()) {
		            newFile.createNewFile();
		        }
		        outputStream = new FileOutputStream(newFile);
		        int read = 0;
		        byte[] bytes = new byte[1024];  // -1 끝을 의미
		        while ((read = inputStream.read(bytes)) != -1) {
		            outputStream.write(bytes, 0, read);
		        }
		        bean.setReviewid(reviewid);
		        bean.setRimage(filename);
		    } catch (Exception e) {
		        System.out.println("file submit err : " + e);
		    } finally {
		        try {
		            inputStream.close();
		            outputStream.close();
		        } catch (Exception e2) {
		            // 예외 처리 코드
		        }
		    }
	        }
			boolean b = reviewDao.update(bean);
			if(b) {
				return "redirect:reviewlist";
			}else {
				return "redirect:error";
			}
	}
	
	// 사진 삭제 (따로)
	@GetMapping("rimagedelete")
	public String rimagedelete(@RequestParam("reviewid") int reviewid, Model model) {
	    // 리뷰 이미지 파일명 가져오기
	    String rimageFilename = reviewDao.rimagename(reviewid); // reviewDao.getReviewImageFilename 메서드는 리뷰 이미지 파일명을 가져오는 메서드로 가정합니다.

	    // 파일 삭제 처리
	    if (rimageFilename != null) {
	        String uploadPath = "C:\\Users\\윤정혜\\git\\team_project\\team_pro\\src\\main\\resources\\static\\upload\\";
	        File rimageFile = new File(uploadPath + rimageFilename);

	        // 파일이 존재하면 삭제
	        if (rimageFile.exists()) {
	            if (rimageFile.delete()) {
	                System.out.println("파일 삭제 성공");
	            } else {
	                System.out.println("파일 삭제 실패");
	            }
	        }
	    }

	    // 데이터베이스에서 리뷰 이미지 정보 삭제
	    reviewDao.rimagedelete(reviewid);

	    return "redirect:reviewlist";
	}

}
