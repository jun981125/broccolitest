package pack.order.model;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {
	
	@Autowired
	private OrderMappingInterface mappingInterface;
	
	// 자신의 상품의 주문 내역 보기 - 판매자
	public List<OrderDto> selectOrder (String customerid) {
		List<OrderDto> list = mappingInterface.selectorder(customerid);
		
		return list;
	}
	
	// 총 상품 수
	public int totalCnt(String customerid ) {
		return mappingInterface.totalCnt(customerid);
	}
	
	// 주문상태 수정
	public boolean updateorderstate(int orderid, String orderstate) {
		boolean b = false;
		int re = mappingInterface.updateorderstate(orderid, orderstate);
		if(re >0)
			b = true;
		return b;
	}
}
