package pack.comment.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pack.noticeboard.model.AnmtDao;
import pack.noticeboard.model.AnmtDto;
import pack.comment.model.CommuDao;
import pack.comment.model.CommuDto;

@Controller
public class PLSController {
	@Autowired
	private CommuDao comDao;
	@Autowired
	private AnmtDao dao;

	private int tot; // 전체 레코드 수
	private int plist = 10; // 페이지 당 행 수
	private int pagesu; // 전체 페이지 수

	public ArrayList<CommuDto> getListdata(ArrayList<CommuDto> list, int page) {
		ArrayList<CommuDto> result = new ArrayList<CommuDto>();

		int start = (page - 1) * plist; // 0, 10, 20, ...

		int size = plist <= list.size() - start ? plist : list.size() - start; // 삼항 연산

		for (int i = 0; i < size; i++) {
			result.add(i, list.get(start + i));
		}
		return result;
	}

	public int getPageSu() { // 총 페이지 수 얻기
		tot = comDao.totalCnt();
		pagesu = tot / plist;
		if (tot % plist > 0)
			pagesu += 1;
		return pagesu;
	}

	@GetMapping("commu")
	public String listProcess(@RequestParam(name = "page", defaultValue = "1") int page,Model model,HttpSession session) {
		int spage = page;
		if (page <= 0)
			spage = 1;
		session.removeAttribute("msg");
		String loginId = (String) session.getAttribute("loginId");
		String nickname = (String) session.getAttribute("nickname");
	    // 관리자 여부를 가져오고, 값이 없을 경우 false로 설정
	       Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
	       if (isAdmin == null) {
	           isAdmin = false;
	       }
		
//		session.setAttribute("loginId", customerDto.getCustomerid());
		
		// 전체자료 읽어오기, paging 처리도 함
		ArrayList<CommuDto> list = (ArrayList<CommuDto>) comDao.selectAll();
		ArrayList<CommuDto> result = getListdata(list, spage);
		ArrayList<AnmtDto> alist =(ArrayList<AnmtDto>) dao.Aselect();
		
		model.addAttribute("alist",alist);
		model.addAttribute("list", result);
		model.addAttribute("pagesu", getPageSu());
		model.addAttribute("page", spage);
		model.addAttribute("customerid",loginId);
		model.addAttribute("nickname",nickname);
		model.addAttribute("isAdmin", isAdmin); // 관리자 여부를 모델에 추가

		return "comment/commu";
	}

	@GetMapping("/commu/search")
	public String searchProcess(CommuBean bean, Model model,@RequestParam("page")int page,HttpSession session) {
		// System.out.println(bean.getSearchName() + " " + bean.getSearchValue());

		int spage = page;
		if (page <= 0)
			spage = 1;
		
		ArrayList<CommuDto> list = (ArrayList<CommuDto>) comDao.search(bean);
		ArrayList<CommuDto> result = getListdata(list, spage);

		 int searchPagesu = comDao.searchCnt(bean.getSearchName(), bean.getSearchValue()) / plist;
		    if (comDao.searchCnt(bean.getSearchName(), bean.getSearchValue()) % plist > 0)
		        searchPagesu += 1;
		    
		model.addAttribute("list", result);
		model.addAttribute("pagesu", searchPagesu);
		model.addAttribute("page", spage);
		String nickname = (String) session.getAttribute("nickname");
		model.addAttribute("nickname",nickname);
		return "comment/commu";
	}

}
