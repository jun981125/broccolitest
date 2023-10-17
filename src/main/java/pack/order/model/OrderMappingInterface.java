package pack.order.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMappingInterface {
	// 자신의 상품의 주문 내역 보기 - 판매자
	@Select("SELECT a.*, b.*, c.ordercount, c.subtotal\r\n"
			+ "FROM orders a\r\n"
			+ "LEFT OUTER JOIN products b ON a.productid = b.productid\r\n"
			+ "LEFT OUTER JOIN orderitem c ON a.orderid = c.orderid\r\n"
			+ "WHERE b.customerid = #{customerid}\r\n"
			+ "")
	List<OrderDto> selectorder(String customerid);
	
	// 총 주문 수 구하기 (페이징 처리를 위해)
	@Select("select count(*) from orders  a left outer join products b on a.productid = b.productid where b.customerid = #{customerid}")
	int totalCnt(String customerid);
	
	// 주문상태 수정
	@Update("update orders set orderstate = #{orderstate} where orderid = #{orderid}")
	int updateorderstate(@Param("orderid") int orderid, @Param("orderstate") String orderstate);


}
