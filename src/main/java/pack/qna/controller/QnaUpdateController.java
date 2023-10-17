package pack.qna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.qna.model.QnaDaoImpl;
import pack.qna.model.QnaDto;

@Controller
public class QnaUpdateController {
	@Autowired
	private QnaDaoImpl daoImpl;
	
	@GetMapping("qupdate")
	public String edit(@RequestParam("num")String num, 
			@RequestParam("page")String page,
			Model model) {
		// 수정 대상 자료 읽기
		QnaDto dto = daoImpl.detail(num);
		
		model.addAttribute("data", dto);
		model.addAttribute("page", page);
		
		return "qna/qupdate";
	}
	
	@PostMapping("qupdate")
	public String editProcess(QnaBean bean, 
			@RequestParam("page") String page, 
			Model model) {
		// DB랑 세션비교
	//	String pass = daoImpl.selectPass(Integer.toString(bean.getNum()));
		//System.out.println("bean.getPass:" + bean.getPass() + " pass : " + pass);
	//	if(bean.getPass().equals(pass) || bean.getPass()==pass) {  // 비밀번호 비교 
			boolean b = daoImpl.update(bean);
			if(b) {
				// 상세보기로 이동
				//return "redirect:detail?num=" + bean.getNum() + "&page=" + page;
				
				// 목록보기로 이동
				return "redirect:qlist?page=" + page;
			}else {
				return "redirect:error";
			}
		}
//	else{
//			model.addAttribute("msg", "비밀번호 불일치~");
//			model.addAttribute("page", page);
//			return "update_err";
//		}
	
}
