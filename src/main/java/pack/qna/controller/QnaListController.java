package pack.qna.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pack.qna.model.QnaDaoImpl;
import pack.qna.model.QnaDto;

@Controller
public class QnaListController {
	@Autowired
	private QnaDaoImpl daoImpl;
	
	private int tot;        // 전체 레코드 수
	private int plist = 10;  // 페이지 당 행 수
	private int pagesu;    // 전체 페이지 수
	
	public ArrayList<QnaDto> getListdata(ArrayList<QnaDto> list, int page){
		ArrayList<QnaDto> result = new ArrayList<QnaDto>();
		
		int start = (page - 1) * plist;   // 0, 10, 20, ...
		
		int size = plist <= list.size() - start?plist : list.size() - start;   // 삼항 연산  
		
		for (int i = 0; i < size; i++) {
			result.add(i, list.get(start + i));
		}
		return result;
	}
	
	public int getPageSu() { // 총 페이지 수 얻기
		tot = daoImpl.totalCnt();
		pagesu = tot / plist;
		if(tot % plist > 0) pagesu += 1;
		return pagesu;
	}

	@GetMapping("qlist")
	public String listProcess(@RequestParam(name="page", defaultValue="1")int page, Model model,HttpSession session) {
		int spage = 0;
		try {
			spage = page;
		} catch (Exception e) {
			spage = 1;
		}
		if(page <= 0) spage = 1;
   
		// paging 처리도 함
		ArrayList<QnaDto> list = (ArrayList<QnaDto>)daoImpl.listAll();
		ArrayList<QnaDto> result = getListdata(list, spage);
		
		model.addAttribute("data", result);
		model.addAttribute("pagesu", getPageSu());
		model.addAttribute("page", spage);
		String nickname = (String) session.getAttribute("nickname");
		model.addAttribute("nickname",nickname);
		
		return "qna/qlist";
	}
	
	@GetMapping("qsearch")
	public String searchProcess(QnaBean bean, Model model,@RequestParam(name = "page", defaultValue = "1") int page, HttpSession session) {
		int spage = page;
		if (page <= 0) {
			spage = 1;
		}

		String loginId = (String) session.getAttribute("loginId");
		String nickname = (String) session.getAttribute("nickname");

		ArrayList<QnaDto> list = (ArrayList<QnaDto>)daoImpl.search(bean);
		ArrayList<QnaDto> result = getListdata(list, spage);

		int searchPagesu = daoImpl.searchCnt(bean.getSearchName(), bean.getSearchValue()) / plist;
		if (daoImpl.searchCnt(bean.getSearchName(), bean.getSearchValue()) % plist > 0) {
			searchPagesu += 1;
		}

		model.addAttribute("data", result);
		model.addAttribute("pagesu", searchPagesu);
		model.addAttribute("page", spage);
		model.addAttribute("page", "1");
		model.addAttribute("customerid",loginId);
		model.addAttribute("nickname",nickname);
		return "qna/qlist";
	}

}
