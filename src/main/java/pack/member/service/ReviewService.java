//package pack.member.service;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import lombok.RequiredArgsConstructor;
//import pack.member.dto.ReviewBean;
//import pack.entity.Product;
//import pack.entity.ReviewEntity;
//import pack.member.repository.MyProductRepository;
//import pack.member.repository.ReviewRepository;
//
//@Service
//@RequiredArgsConstructor
//public class ReviewService {
//	private final ReviewRepository reviewRepository;
//	private final MyProductRepository productRepository;
//	private final MyProductService productService; 
//
//	public List<ReviewEntity> getReviewsByProductId(int productId) {
//		return reviewRepository.findByRproductid(productId);
//	}
//
//	public void saveReview(ReviewEntity review) {
//		reviewRepository.save(review);
//	}
//
//	public boolean insertReview(ReviewBean reviewBean) {
//		try {
//			// ReviewBean을 ReviewEntity로 변환
//			ReviewEntity reviewEntity = new ReviewEntity();
//			reviewEntity.setNickname(reviewBean.getNickname());
//
//			// rproductid를 사용하여 해당 상품(ProductEntity)을 찾아 설정
//			int rproductid = reviewBean.getRproductid();
//			Product product = productRepository.findById(rproductid).orElse(null);
//			if (product != null) {
//				reviewEntity.setRproductid(product);
//			} else {
//				// 상품을 찾지 못한 경우 처리 방법을 정의
//				return false; // 실패
//			}
//
//			MultipartFile file = reviewBean.getRimagePath();
//			if (file != null && !file.isEmpty()) {
//				String uuid = UUID.randomUUID().toString();
//				String filename = uuid + file.getOriginalFilename();
//				String uploadPath = "/Users/kim-yejin/Desktop/YEJIN/work/sprsou/shop_project_mem/src/main/resources/static/upload/";
//
//				try (InputStream inputStream = file.getInputStream();
//						OutputStream outputStream = new FileOutputStream(new File(uploadPath + filename))) {
//					int read;
//					byte[] bytes = new byte[1024];
//					while ((read = inputStream.read(bytes)) != -1) {
//						outputStream.write(bytes, 0, read);
//					}
//					reviewEntity.setRimage(filename);
//				} catch (Exception e) {
//					e.printStackTrace();
//					return false; // 파일 업로드 실패 시 저장하지 않고 실패 반환
//				}
//			}
//
//			reviewEntity.setRating(reviewBean.getRating());
//			reviewEntity.setTitle(reviewBean.getTitle());
//			reviewEntity.setComment(reviewBean.getComment());
//			reviewEntity.setReviewDate(LocalDateTime.now());
//
//			// ReviewEntity를 데이터베이스에 저장
//			reviewRepository.save(reviewEntity);
//
//			// 상품에 리뷰를 저장하는 작업을 추가
//			//product.addReview(reviewEntity);
//			productRepository.save(product);
//
//			return true; // 성공적으로 저장한 경우
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false; // 저장에 실패한 경우
//		}
//	}
//
//	public List<ReviewEntity> getReviewsByCustomerId(String customerid) {
//		return reviewRepository.findByNickname(customerid);
//	}
//	
//	public List<Product> getProductsByReviewList(List<ReviewEntity> reviews) {
//	    List<Product> products = new ArrayList<>();
//	    for (ReviewEntity review : reviews) {
//	        //ProductEntity product = productService.getProductById(review.getRproductid().getProductid());
//	        //products.add(product);
//	    }
//	    return products;
//	}
//	
//	public ReviewEntity getReviewById(int reviewId) {
//        return reviewRepository.findById(reviewId).orElse(null);
//    }
//
//    public void updateReview(ReviewEntity review) {
//        reviewRepository.save(review);
//    }
//
//    public void deleteReview(int reviewId) {
//        reviewRepository.deleteById(reviewId);
//    }
//    
//}