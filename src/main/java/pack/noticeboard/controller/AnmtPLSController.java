package pack.noticeboard.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.noticeboard.model.AnmtDao;
import pack.noticeboard.model.AnmtDto;


@Controller
public class AnmtPLSController {
	@Autowired
	private AnmtDao dao;

	private int tot; // 전체 레코드 수
	private int plist = 10; // 페이지 당 행 수
	private int pagesu; // 전체 페이지 수

	public ArrayList<AnmtDto> getListdata(ArrayList<AnmtDto> list, int page) {
		ArrayList<AnmtDto> result = new ArrayList<AnmtDto>();

		int start = (page - 1) * plist; // 0, 10, 20, ...

		int size = plist <= list.size() - start ? plist : list.size() - start; // 삼항 연산

		for (int i = 0; i < size; i++) {
			result.add(i, list.get(start + i));
		}
		return result;
	}

	public int getPageSu() { // 총 페이지 수 얻기
		tot = dao.totalCnt();
		pagesu = tot / plist;
		if (tot % plist > 0)
			pagesu += 1;
		return pagesu;
	}

	@GetMapping("anmt")
	public String listProcess(@RequestParam(name = "page", defaultValue = "1") int page,Model model) {
		int spage = page;
		if (page <= 0)
			spage = 1;

		// 전체자료 읽어오기, paging 처리도 함
		ArrayList<AnmtDto> list = (ArrayList<AnmtDto>) dao.selectAll();
		ArrayList<AnmtDto> result = getListdata(list, spage);
		

		model.addAttribute("list", result);
		model.addAttribute("pagesu", getPageSu());
		model.addAttribute("page", spage);
		
		
		return "noticeboard/anmt";
	}

	@GetMapping("Asearch")
	public String searchProcess(AnmtBean bean, Model model) {
		// System.out.println(bean.getSearchName() + " " + bean.getSearchValue());
		ArrayList<AnmtDto> list = (ArrayList<AnmtDto>) dao.search(bean);

		model.addAttribute("list", list);
		model.addAttribute("pagesu", getPageSu());
		model.addAttribute("page", "1");
		return "noticeboard/anmt";
	}
	
}
