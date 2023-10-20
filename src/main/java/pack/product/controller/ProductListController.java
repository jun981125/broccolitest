package pack.product.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pack.product.model.ProductDao;
import pack.product.model.ProductDto;
import pack.review.model.ReviewDao;
import pack.review.model.ReviewDto;

@Controller
public class ProductListController {
	@Autowired
	ProductDao productDao;
	@Autowired
	ReviewDao reviewDao;
	
	// 전체 상품 - 메인페이지
	
	private int atot; // 전체 레코드 수
	private int aplist = 16; // 페이지 당 행 수
	private int apagesu; // 전체 페이지 수

	public ArrayList<ProductDto> getListAlldata(ArrayList<ProductDto> list, int page) {
		ArrayList<ProductDto> result = new ArrayList<ProductDto>();

		int start = (page - 1) * aplist; 

		int size = aplist <= list.size() - start ? aplist : list.size() - start;

		for (int i = 0; i < size; i++) {
			result.add(i, list.get(start + i));
		}
		return result;
	}

	// 총 페이지 수 얻기
	public int getAllPageSu() {
		atot = productDao.totalallCnt(); 
		apagesu = atot / aplist;
	    if (atot % aplist > 0)
	    	apagesu += 1;
	    return apagesu;
	}
	
	@GetMapping("productlist_all")
	public String showallproduct (@RequestParam(name = "page", defaultValue = "1") int page,Model model) {
	
	// paging 처리
	int spage = 0;
		try {
	        spage = page;
	    } catch (Exception e) {
	        spage = 1;
	    }
	    if (page <= 0)
	        spage = 1;

	    ArrayList<ProductDto> list = (ArrayList<ProductDto>) productDao.selectMain();
	    ArrayList<ProductDto> result = getListAlldata(list, spage);

	    model.addAttribute("list", result); 
	    model.addAttribute("pagesu", getAllPageSu());
	    model.addAttribute("page", spage);
	
	
	return "product/productlist_all";
	}
	
	// 메인페이지에 상품 8개
	@GetMapping("index")
	public String showeight(Model model) {
		ArrayList<ProductDto> list = (ArrayList<ProductDto>) productDao.selecteight();
		
		model.addAttribute("list",list);
		
		return "index";
	}
	
	// 판매자 페이지

	private int tot; // 전체 레코드 수
	private int plist = 10; // 페이지 당 행 수
	private int pagesu; // 전체 페이지 수

	public ArrayList<ProductDto> getListdata(ArrayList<ProductDto> list, int page) {
		ArrayList<ProductDto> result = new ArrayList<ProductDto>();

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
		tot = productDao.totalsellerCnt(customerid);
		pagesu = tot / plist;
		if (tot % plist > 0)
			pagesu += 1;
		return pagesu;
	}

	// 상품 리스트보기(판매자)
	@GetMapping("productlist_seller")
	public String showProductList(@RequestParam(name = "page", defaultValue = "1") int page, 
			HttpSession session,ProductBean bean,Model model) {
		 // 세션을 통해 판매자 id
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

	    ArrayList<ProductDto> list = (ArrayList<ProductDto>) productDao.selectAll(customerid);
	    ArrayList<ProductDto> result = getListdata(list, spage);

	    model.addAttribute("list", result); 
	    model.addAttribute("pagesu", getPageSu(session));
	    model.addAttribute("page", spage);
	    
	    return "product/productlist_seller";

	}
	// 카테고리 별 페이지
	
	private int stot; // 전체 레코드 수
	private int splist = 16; // 페이지 당 행 수
	private int spagesu; // 전체 페이지 수

	public ArrayList<ProductDto> getListCatedata(ArrayList<ProductDto> list, int page) {
		ArrayList<ProductDto> result = new ArrayList<ProductDto>();

		int start = (page - 1) * splist; 

		int size = splist <= list.size() - start ? splist : list.size() - start;

		for (int i = 0; i < size; i++) {
			result.add(i, list.get(start + i));
		}
		return result;
	}

	// 총 페이지 수 얻기
	public int getCatePageSu(@RequestParam("category") String category) {
	    stot = productDao.totalcateCnt(category); 
	    spagesu = stot / splist;
	    if (stot % splist > 0)
	        spagesu += 1;
	    return spagesu;
	}


	// 카테고리별 상품 보기(사용자)
		@GetMapping("productcategorylist")
		public String showProductCategoryList(@RequestParam(name = "page", defaultValue = "1") int page,
				@RequestParam("category") String category,Model model) {
		    // paging 처리
		    int spage = 0;
		    try {
		        spage = page;
		    } catch (Exception e) {
		        spage = 1;
		    }
		    if (page <= 0)
		        spage = 1;

		    ArrayList<ProductDto> list = (ArrayList<ProductDto>) productDao.selectCatogory(category);
		    ArrayList<ProductDto> result = getListCatedata(list, spage);

		    model.addAttribute("list", result); 
		    model.addAttribute("pagesu", getCatePageSu(category));
		    model.addAttribute("page", spage);
		    return "product/productlist";
		}
	
	// 브랜드별 페이지
		private int btot; // 전체 레코드 수
		private int bplist = 16; // 페이지 당 행 수
		private int bpagesu; // 전체 페이지 수

		public ArrayList<ProductDto> getbrandListdata(ArrayList<ProductDto> list, int page) {
			ArrayList<ProductDto> result = new ArrayList<ProductDto>();

			int start = (page - 1) * bplist; // 0, 10, 20, ...

			int size = bplist <= list.size() - start ? bplist : list.size() - start;
			for (int i = 0; i < size; i++) {
				result.add(i, list.get(start + i));
			}
			return result;
		}

		// 총 페이지 수 얻기
		public int getBrandPageSu(@RequestParam("brand") String brand ) {
			btot = productDao.totalbrandCnt(brand);
			bpagesu = btot / bplist;
			if (btot % bplist > 0)
				bpagesu += 1;
			return bpagesu;
		}
	
		
		// 브랜드별 상품 보기(사용자)
		@GetMapping("productbrandlist")
		public String showProductBrandList(@RequestParam(name = "page", defaultValue = "1") int page,
		        @RequestParam("brand") String brand, Model model) {
		    // paging 처리
		    int bpage = 0;
		    try {
		    	bpage = page;
		    } catch (Exception e) {
		    	bpage = 1;
		    }
		    if (bpage <= 0)
		    	bpage = 1;

		    ArrayList<ProductDto> list = (ArrayList<ProductDto>) productDao.selectBrnad(brand);
		    ArrayList<ProductDto> result = getbrandListdata(list, bpage);
		    
		    model.addAttribute("brand",brand);
		    model.addAttribute("list", result);
		    model.addAttribute("pagesu", getBrandPageSu(brand)); 
		    model.addAttribute("page", bpage);
		    return "product/productlist_brandshop";
		}


	// 선택한 상품 자세히 보기
	@GetMapping("showproduct")
	public String showproduct(@RequestParam("productid") int productid, @RequestParam("page") String page, 
			HttpSession session ,Model model) {
		
		Boolean seller = (Boolean) session.getAttribute("seller");
		String customerid = (String) session.getAttribute("loginid");
		model.addAttribute("seller",seller);
		model.addAttribute("customerid",customerid);
		model.addAttribute("data", productDao.detail(productid));
		model.addAttribute("page", page);
		
		// 상품별 리뷰 보기
		ArrayList<ReviewDto> list = (ArrayList<ReviewDto>) reviewDao.selectPart(productid);
		
		
		 // 리뷰가 없을 경우 메시지 추가
	    if (list.isEmpty()) {
	        model.addAttribute("noReviews", "리뷰가 없습니다.");
	        model.addAttribute("list", list);
	    } else {
	    	model.addAttribute("list", list); 
	        // 상품별 리뷰 별점 평균
	        int avgstar = reviewDao.avgreviewstar(productid);
	        model.addAttribute("avgstar", avgstar);
	    }
		return "product/productdetail";
	}
	
	// 상품 검색하기 - 판매자
	@GetMapping("productsearch")
	public String searchProcess(ProductBean bean, Model model, HttpSession session) {
	    System.out.println(bean.getSearchName() + " " + bean.getSearchValue()); // 검색 파라미터 확인용
	    
	    ArrayList<ProductDto> list = (ArrayList<ProductDto>) productDao.search(bean);
	    
	    model.addAttribute("list", list);
	    model.addAttribute("pagesu", getPageSu(session));
	    model.addAttribute("page", "1");
	    return "product/productlist_seller";
	}
	
	// 상품 검색하기 - 구매자
		@GetMapping("buyproductsearch")
		public String searchbuyProcess(ProductBean bean, Model model, HttpSession session) {
		    System.out.println(bean.getSearchName() + " " + bean.getSearchValue()); // 검색 파라미터 확인용
		    
		    ArrayList<ProductDto> list = (ArrayList<ProductDto>) productDao.search(bean);
		    
		    model.addAttribute("list", list);
		    model.addAttribute("pagesu", getPageSu(session));
		    model.addAttribute("page", "1");
		    return "product/productlist";
		}
	

		// 상품 검색하기 - 메인페이지
        @GetMapping("mainproductsearch")
        public String searchmainProcess(ProductBean bean, Model model, HttpSession session) {
               
         ArrayList<ProductDto> list = (ArrayList<ProductDto>) productDao.search(bean);
               
         model.addAttribute("list", list);
          model.addAttribute("pagesu", getPageSu(session));
         model.addAttribute("page", "1");
          return "product/productlist";
     }
        
     // 전체 판매자의 전체 상품 리스트 보기 - 관리자
    	@GetMapping("/all_productlist")
    	public String AllList(Model model) {
    		List<ProductDto> productList = productDao.selectAllProducts();
    		model.addAttribute("productList", productList);
    		return "admin/all_productlist"; 
    	}
    		
    	// 대기중인 상품 리스트 보기 - 관리자
    	@GetMapping("/waiting_productlist")
    	public String WaitList(Model model) {
    		List<ProductDto> productList = productDao.selectWaitProducts();
    		model.addAttribute("productList", productList);
    		return "admin/waiting_productlist"; 
    		    }
    	
    	// 판매자 아이디 검색하기 - 관리자
    	@GetMapping("/seller/search")
    	public String searchById(@RequestParam("search") String seller, Model model) {
    	    if (seller == null || seller.isEmpty()) {
    	        // 아이디 검색어가 없을 경우 전체 상품 목록을 불러옴
    	        List<ProductDto> productList = productDao.selectAllProducts();
    	        model.addAttribute("productList", productList);
    	    } else {
    	        // 아이디 검색 수행
    	        List<ProductDto> productList = productDao.selectAll(seller);
    	        model.addAttribute("productList", productList);
    	    }
    	    return "admin/all_productlist"; // all_productlist.html 페이지로 이동
    	}
}
