package pack.review.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pack.product.model.ProductDto;
import pack.review.model.ReviewDao;
import pack.review.model.ReviewDto;
import pack.reviewreply.model.ReviewReplyDao;
import pack.reviewreply.model.ReviewReplyDto;

@Controller
public class ReviewListController {
	@Autowired
	ReviewDao reviewDao;
	@Autowired
	private ReviewReplyDao replyDao;
	
	private int tot; // 전체 레코드 수
	private int plist = 10; // 페이지 당 행 수
	private int pagesu; // 전체 페이지 수
	
	public ArrayList<ReviewDto> getListdata(ArrayList<ReviewDto> list, int page) {
		ArrayList<ReviewDto> result = new ArrayList<ReviewDto>();

		int start = (page - 1) * plist; // 0, 10, 20, ...

		int size = plist <= list.size() - start ? plist : list.size() - start;

		for (int i = 0; i < size; i++) {
			result.add(i, list.get(start + i));
		}
		return result;
	}

	// 총 페이지 수 얻기
	public int getPageSu(HttpSession session) {
		String rcustomerid = (String) session.getAttribute("loginid");
		tot = reviewDao.totalCnt(rcustomerid);
		pagesu = tot / plist;
		if (tot % plist > 0)
			pagesu += 1;
		return pagesu;
	}
	
	// 리뷰 리스트 보기 - 구매자
	@GetMapping("reviewlist")
	public String showReviewList(@RequestParam(name = "page", defaultValue = "1") int page,
			HttpSession session ,Model model) {
		
		String rcustomerid = (String) session.getAttribute("loginid");
		
	    // paging 처리
	    int spage = 0;
	    try {
	        spage = page;
	    } catch (Exception e) {
	        spage = 1;
	    }
	    if (page <= 0)
	        spage = 1;

	    ArrayList<ReviewDto> list = (ArrayList<ReviewDto>) reviewDao.selectAll(rcustomerid);
	    ArrayList<ReviewDto> result = getListdata(list, spage);

	    model.addAttribute("list", result); 
	    model.addAttribute("pagesu", getPageSu(session));
	    model.addAttribute("page", spage);
	    return "review/reviewlist";
	}
	
	private int stot; // 전체 레코드 수
	private int splist = 10; // 페이지 당 행 수
	private int spagesu; // 전체 페이지 수
	
	public ArrayList<ReviewDto> getSellerListdata(ArrayList<ReviewDto> list, int page) {
		ArrayList<ReviewDto> result = new ArrayList<ReviewDto>();

		int start = (page - 1) * splist; // 0, 10, 20, ...
		int size = splist <= list.size() - start ? splist : list.size() - start;
		for (int i = 0; i < size; i++) {
			result.add(i, list.get(start + i));
		}
		return result;
	}

	// 총 페이지 수 얻기
	public int getSellerPageSu(HttpSession session) {
		String customerid = (String) session.getAttribute("loginid");
		stot = reviewDao.totalsellerCnt(customerid);
		spagesu = stot / splist;
		if (stot % splist > 0)
			spagesu += 1;
		return spagesu;
	}
	
	// 리뷰 리스트 보기 - 판매자
	@GetMapping("reviewlist_seller")
	public String showSellerReviewList(@RequestParam(name = "page", defaultValue = "1") int page,
			HttpSession session ,Model model) {
		
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

	    ArrayList<ReviewDto> list = (ArrayList<ReviewDto>) reviewDao.selectSellerAll(customerid);
	    ArrayList<ReviewDto> result = getSellerListdata(list, spage);

	    model.addAttribute("list", result); 
	    model.addAttribute("pagesu", getSellerPageSu(session));
	    model.addAttribute("page", spage);
	    return "review/reviewlist_seller";
	}
	
	
	// 선택한 리뷰 자세히 보기
	@GetMapping("showreview")
	public String showreview(@RequestParam("reviewid") int reviewid, @RequestParam("page") int page, 
			HttpSession session ,Model model) {
		Boolean seller = (Boolean) session.getAttribute("seller");
		String customerid = (String) session.getAttribute("loginId");
		String nickname = (String) session.getAttribute("nickname");
		
		model.addAttribute("seller", seller);
		model.addAttribute("customerid", customerid);
		model.addAttribute("nickname", nickname);
		model.addAttribute("data", reviewDao.detail(reviewid));
		model.addAttribute("page", page);
		// 댓글 조회
		ArrayList<ReviewReplyDto> list = (ArrayList<ReviewReplyDto>) replyDao.selectAll(reviewid);
		model.addAttribute("list", list);
		
		
		return "review/reviewdetail";
	}
}
