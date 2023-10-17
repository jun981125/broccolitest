package pack.review.model;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.review.controller.ReviewBean;

@Mapper
public interface ReviewMappingInterface {
	// 리뷰 등록
	@Insert("INSERT INTO reviews (rcustomerid ,rnickname ,rproductid, title,rating, comment, reviewdate, rimage)\r\n"
			+ "VALUES (#{rcustomerid},#{rnickname},#{rproductid}, #{title},#{rating}, #{comment}, #{reviewdate} ,#{rimage})")
	int insertReview(ReviewBean bean);

	// 리뷰 목록 보기 - 구매자 별(자신이 남긴 리뷰)
	// products table의 model,brand를 가져오기 위해 join 사용
	@Select("select rproductid, reviewid, rnickname, title, pimage  ,model, brand, rating, comment, reviewdate from reviews left outer join products on rproductid = productid where customerid = #{customerid} order by reviewid desc")
	List<ReviewDto> selectAll(String customerid);
	
	// 판매자 별(자신의 상품에 대한 리뷰)
	@Select("select rproductid, reviewid, rnickname, title, pimage ,model, brand, rating, comment, reviewdate from reviews a left outer join products b on rproductid = productid where b.customerid = #{customerid} order by reviewid desc")
	List<ReviewDto> selectSellerAll(String customerid);

	// 해당 리뷰 자세히 보기
	@Select("select rproductid, reviewid, rnickname, title, pimage, model, brand, rating, comment, reviewdate, rimage from reviews left outer join products on rproductid = productid where reviewid = #{reviewid}")
	ReviewDto selectOne(int reviewid);
	
	// 상품별 리뷰 보기
	@Select("select rproductid, reviewid, rnickname, title, pimage, model, brand, rating, comment, reviewdate, rimage from reviews left outer join products on rproductid = productid where rproductid = #{rproductid} order by reviewid desc")
	List<ReviewDto> selectPart(int productid); 
	
	// 상품별 리뷰 평균 보기
	@Select("SELECT AVG(rating) AS average_rating\r\n"
			+ "FROM reviews\r\n"
			+ "WHERE rproductid = #{rpoductid};\r\n"
			+ "")
	int avgreviewstar(int rpoductid);
	
	// 총 리뷰 수 구하기 (페이징 처리를 위해)
	@Select("select count(*) from reviews")
	int totalCnt();

	// 리뷰 수정
	@Update("update reviews set rnickname=#{rnickname}, title=#{title}, rating=#{rating}, comment=#{comment}, reviewdate=#{reviewdate}, rimage=#{rimage} where reviewId=#{reviewid}")
	int updateReview(ReviewBean bean);

	// 리뷰 삭제
	@Delete("delete from reviews where reviewid=#{reviewid}")
	int deleteReview(int reviewid);

	// rimage 삭제(null 값으로 update 하기)
	@Update("update reviews set rimage=null where reviewid = #{reviewid}")
	int rimagedelete(int reviewid);
	
	// rimgae 이름
	@Select("select rimage from reviews where reviewid=#{reviewid}")
	String rimagename(int reviewid);
	
	
}
