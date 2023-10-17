package pack.comment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pack.comment.model.CmmDao;
import pack.comment.model.CommentDto;

@Controller
public class CommentController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CmmDao cmmDao;
    
    @PostMapping("/addComment")
    public String addComment(CommentDto commentDto, @RequestParam("num")String num, @RequestParam("page")String page,
    		HttpSession session,Model model) {
    	String idkey = (String) session.getAttribute("loginid");
    	String nickname = (String) session.getAttribute("nickname");
    	
    	commentDto.setIdkey(idkey);
    	commentDto.setCustomernickname(nickname);
    	cmmDao.insertComment(commentDto);
    	// 댓글 수 카운트
        cmmDao.countcomment(Integer.parseInt(num));
    	return "redirect:/commudetail?num=" + num + "&page="+page;
    }
    
    @PostMapping("/updateComment")
    public String edit(@RequestParam("idkey")String idkey, @RequestParam("cnum")int  cnum,
			HttpSession session,@RequestParam("page")String page, @RequestParam("num")String num,
			Model model,@RequestParam("content") String content) {
    	String loginId = (String) session.getAttribute("loginid");

		if (idkey.equals(loginId)) {
			cmmDao.updateCom(content,cnum);
		}else {
			return "redirect:/commudetail?num=" + num + "&page=" + page;
		}	return "redirect:/commudetail?num=" + num + "&page=" + page;
	}
    
//    @PostMapping("/deleteComment")
//    public String upCom(@RequestParam("content")String content,@RequestParam("page")String page,
//    		@RequestParam("num")String num, @RequestParam("idkey")int id ,Model model) {
//    	logger.info(num +" "+ id);
//    	boolean b = cmmDao.updateCom(content,id);
//		if(b) {
//			// 상세보기로 이동
//			return "redirect:/commudetail?num=" + num + "&page=" + page;
//		}else {
//			return "redirect:err";
//		}
//}
    
    
    @GetMapping("/deleteComment")
	public String delo(@RequestParam("idkey")String idkey, @RequestParam("cnum")String  cnum,
			HttpSession session,@RequestParam("page")String page, @RequestParam("num")String num,Model model) {
    	
    	String loginId = (String) session.getAttribute("loginId");
			
		if (loginId.equals(idkey)) {
			cmmDao.deleteComm(cnum);
			 	return "redirect:/commudetail?num=" + num + "&page=" + page;
		}else {
			return "redirect:/commudetail?num=" + num + "&page=" + page;
		}
	}
	
}
