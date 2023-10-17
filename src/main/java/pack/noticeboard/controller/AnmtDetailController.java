package pack.noticeboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.noticeboard.model.AnmtDao;


@Controller
public class AnmtDetailController {
	@Autowired
	private AnmtDao dao;

	@GetMapping("Adetail")
	public String detailProcess(@RequestParam("num") String num, @RequestParam("page") String page, Model model) {

		model.addAttribute("list", dao.detail(num));
		model.addAttribute("page", page);

		return "noticeboard/anmtdetail";
	}

}
