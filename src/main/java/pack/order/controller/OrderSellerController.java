package pack.order.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pack.order.model.OrderDao;
import pack.order.model.OrderDto;

@Controller
public class OrderSellerController {
	@Autowired
	OrderDao orderDao;
	
	
	private int tot; // 전체 레코드 수
	private int plist = 10; // 페이지 당 행 수
	private int pagesu; // 전체 페이지 수
	
	public ArrayList<OrderDto> getListdata(ArrayList<OrderDto> list, int page) {
		ArrayList<OrderDto> result = new ArrayList<OrderDto>();

		int start = (page - 1) * plist; // 0, 10, 20, ...

		int size = plist <= list.size() - start ? plist : list.size() - start;

		for (int i = 0; i < size; i++) {
			result.add(i, list.get(start + i));
		}
		return result;
	}

	// 총 페이지 수 얻기
	public int getPageSu(HttpSession session) {
		String customerid = (String) session.getAttribute("loginid");
		tot = orderDao.totalCnt(customerid);
		pagesu = tot / plist;
		if (tot % plist > 0)
			pagesu += 1;
		return pagesu;
	}
	
	// 판매자별 주문 보기
	@GetMapping("orderlist_seller")
	public String showOrderlist(@RequestParam(name = "page", defaultValue = "1")int page,  
	HttpSession session, Model model) {
		String customerid = (String) session.getAttribute("loginid");
		// paging 처리
	    int spage = 0;
	    try {
	        spage = page;
	    } catch (Exception e) {
	        spage = 1;
	    }
	    if (page <= 0)
	        spage = 1;
	    ArrayList<OrderDto> list = (ArrayList<OrderDto>) orderDao.selectOrder(customerid);
	    ArrayList<OrderDto> result = getListdata(list, spage);

	    model.addAttribute("list", result); 
	    model.addAttribute("pagesu", getPageSu(session));
	    model.addAttribute("page", spage);
	    
	    return "order/orderlist_seller";
	}
	
	// 주문 상태 수정
	@PostMapping("orderstateupdate")
	public String orderstateupdate(@RequestParam("orderid") int orderid, @RequestParam("orderstate") String orderstate,
			OrderBean bean) {
		 boolean updated = orderDao.updateorderstate(orderid, orderstate);
		
		if(updated) {
			return "redirect:orderlist_seller";
		} else {
			return "redirect:/error";
		}
	}
	
}
