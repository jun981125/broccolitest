package pack.wishlist.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pack.wishlist.model.WishlistDao;
import pack.wishlist.model.WishlistDto;

@Controller
public class WishlistListController {

	@Autowired
	private WishlistDao dao;
	
	private int tot; // 전체 레코드 수
	private int plist = 10; // 페이지 당 행 수
	private int pagesu; // 전체 페이지 수
	
	public ArrayList<WishlistDto> getListdata(ArrayList<WishlistDto> list, int page) {
		ArrayList<WishlistDto> result = new ArrayList<WishlistDto>();

		int start = (page - 1) * plist; // 0, 10, 20, ...

		int size = plist <= list.size() - start ? plist : list.size() - start;

		for (int i = 0; i < size; i++) {
			result.add(i, list.get(start + i));
		}
		return result;
	}
	
	// 총 페이지 수 얻기
		public int getPageSu() {
			tot = dao.totalCnt();  
			pagesu = tot / plist;
			if (tot % plist > 0)
				pagesu += 1;
			return pagesu;
		}
		
		// 찜목록 보기
		@GetMapping("wishlist")
		public String shoWishList(@RequestParam(name = "page", defaultValue = "1") int page, 
				HttpSession session ,Model model) {
		    // paging 처리
		    int spage = 0;
		    try {
		        spage = page;
		    } catch (Exception e) {
		        spage = 1;
		    }
		    if (page <= 0)
		        spage = 1;
		    
		    String customerid = (String) session.getAttribute("loginid");

		    ArrayList<WishlistDto> list = (ArrayList<WishlistDto>) dao.selectAll(customerid);
		    ArrayList<WishlistDto> result = getListdata(list, spage);

		    model.addAttribute("list", result); 
		    model.addAttribute("pagesu", getPageSu());
		    model.addAttribute("page", spage);
		    return "wishlist/wishlist";
		}
}
