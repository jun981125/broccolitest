package pack.qna.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QnaErrorController {
	@GetMapping("error")
	public String err() {
		return "error";
	}
}
