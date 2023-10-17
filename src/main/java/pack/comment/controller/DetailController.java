package pack.comment.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pack.comment.model.CmmDao;
import pack.comment.model.CommentDto;
import pack.comment.model.CommuDao;

@Controller
public class DetailController {
   @Autowired
   private CommuDao comDao;
   @Autowired
   private CmmDao cmDao;
   @Autowired
   private CmmDao cmmDao;

   @GetMapping("commudetail")
   public String detailProcess(@RequestParam("num") String num, HttpSession session, @RequestParam("page") String page,
         Model model) {
      session.removeAttribute("msg");
      // 조회수 증가 선행
      comDao.updateReadcnt(num);

      model.addAttribute("list", comDao.detail(num));
      model.addAttribute("page", page);

      // 댓글 조회
      ArrayList<CommentDto> comments = cmDao.selectCommentsByNum(Integer.parseInt(num));
      model.addAttribute("comments", comments);
      // 댓글 수 업데이트
      cmmDao.countcomment(Integer.parseInt(num));
      
      String loginId = (String) session.getAttribute("loginid");
      String nickname = (String) session.getAttribute("nickname");
      model.addAttribute("customerid", loginId);
      model.addAttribute("nickname", nickname);
      return "comment/commudetail";
   }

}