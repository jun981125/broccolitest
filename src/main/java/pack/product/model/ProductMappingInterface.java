package pack.product.model;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.product.controller.ProductBean;

@Mapper
public interface ProductMappingInterface {
	// 전체 상품 읽기 - 메인페이지
	@Select("select * from products order by productid desc")
	List<ProductDto> selectMain();
	
	// 상품관리 (판매자) 
	@Select("SELECT * FROM products where customerid=#{customerid} ORDER BY productid DESC")
	List<ProductDto> selectAll(String nickname);

	// 해당 상품 상세 보기
	@Select("select * from products where productid=#{productid}")
	ProductDto selectOne(int product_id);
	
	// 카테고리 별 상품 보기(사용자)
	@Select("select * from products where category=#{category} and state='승인' ")
	List<ProductDto> selectCategory(String category);
	
	// 브랜드 별 상품 보기(사용자)
	@Select("select * from products where brand=#{brand} and state='승인'")
	List<ProductDto> selectBrand(String brand);
	
	// 메인페이지에 최신순으로 8개만 상품 보이게
	@Select("SELECT *\n"
			+ "FROM products\n"
			+ "ORDER BY productid DESC\n"
			+ "LIMIT 8;")
	List<ProductDto> selecteight();
	
	// 카테고리에서 브랜드 선택한 상품 목록
	@Select("select * from products where category=#{category} and brand=#{brand}")
	List<ProductDto> selectCateBran(@Param("category") String category, @Param("brand") String brand);
	
	// 상품 등록  
	@Insert("INSERT INTO products (customerid, category, brand, model, price, stockquantity,pimage, dimage)\r\n"
			+ "VALUES (#{customerid}, #{category}, #{brand}, #{model}, #{price}, #{stockquantity}, #{pimage},#{dimage})")
	int insertProduct(ProductBean bean);
	
	// 총 상품 수 구하기
	@Select("select count(*) from products")
	int totalallCnt();
	
	// 판매자별 총 상품 수 구하기
	@Select("select count(*) from products where customerid=#{customerid}")
	int totalsellerCnt(String customerid);
	
	// 카테고리별 총 상품 수 구하기
	@Select("select count(*) from products where category=#{category}")
	int totalcateCnt(String category);
	
	// 브랜드별 총 상품 수 구하기
	@Select("select count(*) from products where brand=#{brand}")
	int totalbrandCnt(String brand);
	
	// 카테고리별 브랜드별 총 상품 수 구하기
	@Select("select count(*) from products where category=#{category} and brand=#{brand} ")
	int totalcatebranCnt(String category, String brand);

	// 상품 찾기
	@Select("select * from products where ${searchName} like concat('%', #{searchValue}, '%')")
	List<ProductDto> searchList(ProductBean bean);

	// 상품 수정
	@Update("update products set category=#{category}, brand=#{brand}, model=#{model}, price=#{price}, stockquantity=#{stockquantity}, pimage=#{pimage}, dimage=#{dimage}  where productid=#{productid}")
	int updateProduct(ProductBean bean);

	// pimage(상품이미지) 삭제
	@Update("update products set pimage=null where productid = #{productid}")
	int pimagedelete(int productid);
	
	// pimage 이름 
	@Select("select pimage from products where productid=#{productid}")
	String pimagename (int productid);
	
	// dimage(상세이미지) 삭제
	@Update("update products set dimage=null where productid = #{productid}")
	int dimagedelete(int productid);
	
	// dimage 이름 
	@Select("select dimage from products where productid=#{productid}")
	String dimagename (int productid);
	
	// 상품 삭제
	@Delete("delete from products where productid=#{productid}")
	int deleteProduct(int productid);

	// 특정 상품에 관한 리뷰 전체 삭제
	@Delete("delete from reviews where productid = #{productid}")
	int deletereviewfromproduct(int productid);



	// 판매자 상품 상태 변경 - 관리자
	@Update("UPDATE products SET state = #{state} WHERE productid = #{productid}")
	int updateProductState(ProductBean bean);

	// 모든 상품을 페이지별로 가져오기 - 관리자
	@Select("SELECT * FROM products ORDER BY productid DESC LIMIT #{start}, #{size}")
	List<ProductDto> selectAllPagingList(@Param("start") int start, @Param("size") int size);

	// 모든 상품의 총 수를 반환 - 관리자
	@Select("SELECT COUNT(*) FROM products")
	int getTotalProductCount();

	// 승인 대기 중인 상품 목록을 페이지별로 가져오기 - 관리자
	@Select("SELECT * FROM products WHERE state = '승인대기중' ORDER BY productid DESC LIMIT #{start}, #{size}")
	List<ProductDto> selectWaitProductsPaging(@Param("start") int start, @Param("size") int size);

	// 승인 대기 중인 상품의 총 수를 반환 - 관리자
	@Select("SELECT COUNT(*) FROM products WHERE state = '승인대기중'")
	int getTotalWaitProductCount();

	//  판매자가 등록한 상품 목록을 페이지별로 가져오기 - 관리자
	@Select("SELECT * FROM products WHERE customerid = #{customerid} ORDER BY productid DESC LIMIT #{start}, #{size}")
	List<ProductDto> searchByIdPaging(@Param("customerid") String customerid, @Param("start") int start, @Param("size") int size);

	// 검색한 판매자가 등록한 상품 목록을 페이지별로 가져오기 - 관리자
	@Select("SELECT * FROM products WHERE customerid = #{seller} ORDER BY productid DESC LIMIT #{start, jdbcType=INTEGER}, #{size, jdbcType=INTEGER}")
	List<ProductDto> selectProductsBySellerPaging(@Param("seller") String seller, @Param("start") int start, @Param("size") int size);

	// 판매자가 등록한 상품의 총 수를 반환 - 관리자
	@Select("SELECT COUNT(*) FROM products WHERE customerid = #{seller}")
	int getTotalProductCountBySeller(@Param("seller") String seller);

	// 승인 대기 중인 판매자의 상품을 페이지별로 가져오기 - 관리자
	@Select("SELECT * FROM products WHERE state = '승인대기중' AND customerid = #{seller} ORDER BY productid DESC LIMIT #{start}, #{size}")
	List<ProductDto> selectWaitProductsBySellerPaging(@Param("seller") String seller, @Param("start") int start, @Param("size") int size);

	// 승인 대기 중인 판매자의 상품의 총 수를 반환 - 관리자
	@Select("SELECT COUNT(*) FROM products WHERE state = '승인대기중' AND customerid = #{seller}")
	int getTotalWaitProductCountBySeller(String seller);


	
}
