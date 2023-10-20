package pack.wishlist.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.wishlist.controller.WishlistBean;

@Repository
public class WishlistDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WishlistMappingInterface wishmappingInterface;
	
	// 찜 등록
	public boolean wishinsert(WishlistBean bean) {
		boolean b = false;
		int re = wishmappingInterface.insertWishlist(bean);
		if(re>0) b = true;
		return b;
	}
	
	// 찜 목록 보기
	public List<WishlistDto> selectAll(String customerid) {
		List<WishlistDto> list = wishmappingInterface.selectAll(customerid);
		logger.info("읽은 위시리스트 수 : " + list.size());
		return list;
	}
	
	public int totalCnt(String customerid) {
		return wishmappingInterface.totalCnt(customerid);
	}
	
	// 찜삭제
	public boolean deletewishlist(int wishlistid) {
		boolean b = false;
		int re = wishmappingInterface.deletewishlist(wishlistid);
		if (re > 0)
			b = true;
		return b;
	}
	
}
