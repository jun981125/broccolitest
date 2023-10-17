package pack.qna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pack.qna.model.QnaDaoImpl;
import pack.qna.model.QnaDto;

@Controller
public class QnaDeleteController {
	@Autowired
	private QnaDaoImpl daoImpl;

	@GetMapping("qdelete")
	public String del(@RequestParam("num") String num, HttpSession session) {
		QnaDto dto = daoImpl.detail(num);
		String loginId = (String) session.getAttribute("loginId");
		String customerid = dto.getId();

		if (customerid.equals(loginId)) {
			boolean b = daoImpl.delete(num);
			if (b) {
				return "redirect:qlist";
			} else {
				return "redirect:error";
			}
		} else {
			return "redirect:/qlist";
		}
	}
}
