package pack.comment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pack.comment.model.CommuDao;
import pack.comment.model.CommuDto;

@Controller
public class CommuDeleteController {
	@Autowired
	private CommuDao comDao;

	@GetMapping("commudelete")
	public String edit(@RequestParam("num") String num, Model model,HttpSession session) {
		CommuDto dto = comDao.detail(num);
		String loginId = (String) session.getAttribute("loginid");
		String customerid = dto.getCustomerid();
        boolean isAdmin = (boolean) session.getAttribute("isAdmin"); // 관리자 여부 확인

        if (isAdmin || customerid.equals(loginId)) { // 관리자 또는 작성자인 경우에만 삭제 권한 부여
			boolean b = comDao.delete(num);
			if (b) {
				return "redirect:commu";
			} else {
				return "redirect:error";
			}
		} else {
			return "redirect:/commu";
		}
	}
}
