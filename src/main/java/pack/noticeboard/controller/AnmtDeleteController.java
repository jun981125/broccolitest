package pack.noticeboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pack.noticeboard.model.AnmtDao;
import pack.noticeboard.model.AnmtDto;



@Controller
public class AnmtDeleteController {
	@Autowired
	private AnmtDao dao;

	@GetMapping("Adelete")
	public String edit(@RequestParam("num")String num, Model model) {
		AnmtDto dto = dao.detail(num);
		model.addAttribute("list", dto);
		return "noticeboard/anmtdelete";
	}
   
	@PostMapping("Adelete")
	public String del(@RequestParam("num") String num,AnmtBean bean,HttpSession session) {
		//String customerid = (String) session.getAttribute("customernickname");
		//(String) session.getAttribute("idkey");
		//customerid.equals(idkey
		if (true) { 
			boolean b = dao.delete(num);
			if (b) {
					return "redirect:noticeboard/anmt";
			}else {
					return "redirect:error";
			}
		}else{
			return"redirect:noticeboard/anmt";
		}
	}
}
