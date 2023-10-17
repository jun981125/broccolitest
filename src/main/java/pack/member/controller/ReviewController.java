//package pack.member.controller;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import pack.member.dto.ReviewBean;
//import pack.entity.Product;
//import pack.entity.ReviewEntity;
//import pack.member.service.MyProductService;
//import pack.member.service.ReviewService;
//
//@Controller
//@RequiredArgsConstructor
//public class ReviewController {
//	private final ReviewService reviewService;
//    private final MyProductService productService; 
//
//	@GetMapping("reviewinsert")
//	public String insert(@RequestParam("rproductid") int rproductid, Model model, HttpSession session) {
//	    model.addAttribute("rproductid", rproductid);
//	    
//	    // 현재 로그인한 사용자의 닉네임 정보를 가져와 모델에 추가
//	    String customernickname = (String) session.getAttribute("loginId");
//	    model.addAttribute("customernickname", customernickname);
//        return "/mypage/reviewinsert";
//    }
//    
//    
//
//    @PostMapping("reviewinsert")
//    public String insertProcess(@RequestParam("rproductid") int rproductid, ReviewBean bean, BindingResult result, Model model, HttpSession session) throws Exception {
//        // 현재 로그인한 사용자의 정보를 가져옴
//        String loginId = (String) session.getAttribute("loginId");
//        if (loginId == null) {
//            // 로그인되지 않은 경우 처리 (예: 로그인 페이지로 리다이렉트)
//            return "redirect:/login";
//        }
//
//        // 리뷰 작성자 정보 설정
//        bean.setNickname(loginId);
//    	bean.setReviewdate();
//        bean.setRproductid(rproductid);
//        InputStream inputStream = null;
//        OutputStream outputStream = null;
//
//        // 이름 중복을 방지하기 위해 난수 발생시킴
//        String uuid = UUID.randomUUID().toString();
//        MultipartFile file = bean.getRimagePath();
//        String filename = uuid + file.getOriginalFilename();
//
//        // 유효성 검사 결과 확인
//        if (result.hasErrors()) {
//            return "err";
//        }
//
//        try {
//            inputStream = file.getInputStream();
//            File newFile = new File("/Users/kim-yejin/Desktop/YEJIN/work/sprsou/shop_project_mem/src/main/resources/static/upload/" + filename);
//            if (!newFile.exists()) {
//                newFile.createNewFile();
//            }
//            outputStream = new FileOutputStream(newFile);
//            int read = 0;
//            byte[] bytes = new byte[1024]; // -1 끝을 의미
//            while ((read = inputStream.read(bytes)) != -1) {
//                outputStream.write(bytes, 0, read);
//            }
//            bean.setRproductid(rproductid);
//            bean.setRimage(filename);
//        } catch (Exception e) {
//            System.out.println("file submit err : " + e);
//        } finally {
//            try {
//                inputStream.close();
//                outputStream.close();
//            } catch (Exception e2) {
//                // 예외 처리 코드
//            }
//        }
//
//        // 리뷰 등록 작업을 수행하는 서비스 메서드를 호출하고, 그 결과에 따라 리다이렉션할 페이지 결정
//        boolean reviewInserted = reviewService.insertReview(bean);
//        if (reviewInserted) {
//            return "redirect:/reviewinsert?productId=" + rproductid; // 리뷰 등록 성공 시 상품 상세 페이지로 이동
//        } else {
//            return "redirect:/error"; // 리뷰 등록 실패 시 에러 페이지로 이동 (적절한 경로로 변경)
//        }
//    }
//    
//    @GetMapping("/myreview")
//    public String myReviews(Model model, HttpSession session) {
//        // 세션에서 로그인한 사용자의 customerid 가져오기
//        String customerid = (String) session.getAttribute("loginId");
//
//        if (customerid != null) {
//            // 사용자의 아이디를 기반으로 해당 사용자가 작성한 리뷰 조회
//            List<ReviewEntity> myReview = reviewService.getReviewsByCustomerId(customerid);
//            model.addAttribute("myReview", myReview);
//
//            // 사용자가 작성한 리뷰에 대한 상품 정보 가져오기
//            List<Product> products = reviewService.getProductsByReviewList(myReview);
//            model.addAttribute("products", products);
//
//            return "mypage/myreview";
//        }
//
//        // 로그인하지 않았거나 오류가 발생한 경우 로그인 페이지로 리다이렉트
//        return "redirect:/login";
//    }
//    
//    @GetMapping("/edit-review/{reviewId}")
//    public String editReview(@PathVariable("reviewId") int reviewId, Model model) {
//        // 리뷰 수정 페이지로 이동하는 로직 추가
//        ReviewEntity review = reviewService.getReviewById(reviewId);
//        model.addAttribute("review", review);
//        return "edit-review";
//    }
//
//    @PostMapping("/edit-review/{reviewId}")
//    public String editReviewProcess(@PathVariable("reviewId") int reviewId, ReviewBean updatedReview) {
//        // 리뷰 수정을 처리하는 로직 추가
//        ReviewEntity review = reviewService.getReviewById(reviewId);
//        if (review != null) {
//            // 수정할 리뷰를 찾은 경우
//            review.setTitle(updatedReview.getTitle());
//            review.setComment(updatedReview.getComment());
//            review.setRating(updatedReview.getRating());
//            reviewService.updateReview(review);
//        }
//        return "redirect:/myreview";
//    }
//    
//    @GetMapping("/delete-review/{reviewId}")
//    public String deleteReview(@PathVariable("reviewId") int reviewId) {
//        // 리뷰 삭제 로직 추가
//        reviewService.deleteReview(reviewId);
//        return "redirect:/myreview";
//    }
//
//
//}
