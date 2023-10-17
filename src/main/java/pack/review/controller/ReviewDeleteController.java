package pack.review.controller;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pack.review.model.ReviewDao;
import pack.reviewreply.model.ReviewReplyDao;



@Controller
public class ReviewDeleteController {
	 private Logger logger = LoggerFactory.getLogger(this.getClass());

	    @Autowired
	    private ReviewDao reviewDao;
	    @Autowired
	    private ReviewReplyDao replyDao;
	    
	    @PostMapping("/reviewdelete")
	    public String delete(@RequestParam("reviewid") int reviewid) {
	    	
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
			// review를 참고하는 다른 테이블있다면 거기서도 특정 reveiwid의 데이터들을 삭제한 뒤 진행해야함
	    	replyDao.deletepart(reviewid);
	    	reviewDao.delete(reviewid);
			return "redirect:reviewlist";
		}
}
