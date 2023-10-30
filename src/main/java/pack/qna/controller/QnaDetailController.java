package pack.qna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pack.qna.model.QnaDaoImpl;

@Controller
public class QnaDetailController {
	@Autowired
	private QnaDaoImpl daoImpl;
	
	@GetMapping("qdetail")
	public String detailProcess(@RequestParam("num") String num,
			@RequestParam("page") String page, Model model,HttpSession session) {
		// 조회수 증가 선행
		daoImpl.updateReadcnt(num);
		  // 관리자 여부를 가져오고, 값이 없을 경우 false로 설정
	       Boolean isseller = (Boolean) session.getAttribute("isseller");
	       if (isseller == null) {
	    	   isseller = false;
	       }
		
		model.addAttribute("data", daoImpl.detail(num));
		model.addAttribute("page", page);
		model.addAttribute("isseller", isseller); // 판매자 여부를 모델에 추가, qna상세보기에서 판매자 여부로 '답글달기'버튼 보임
		
		return "qna/qdetail";
	}
}
