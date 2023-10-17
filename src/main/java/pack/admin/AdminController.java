package pack.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import pack.customer.CustomerDto;
import pack.customer.CustomerEntity;
import pack.customer.controller.CustomerService;


@Controller
@RequiredArgsConstructor   // 생성자 자동으로 생성하는 어노테이션
public class AdminController {
   // 생성자 주입


   private final AdminService adminService;
private final CustomerService customerService;


   // 관리자 메인 페이지
   @GetMapping("/adminmain")
   public String adminmain() {
       return "admin/adminmain";
   }
   
   
   // 관리자 로그인 페이지
   @GetMapping("adminlogin")
   public String adminlogin() {
      return "admin/adminlogin";
   }
   
   // 관리자 로그인시 정보 일치여부
   @PostMapping("/admin")
   // adminid와 adminpasswd를 매개변수로 받음
    public String adminlogin(HttpSession session, @RequestParam String adminid, @RequestParam String adminpasswd, Model model) {   
        AdminEntity adminEntity = adminService.adminlogin(adminid, adminpasswd);
        if (adminEntity != null) {
           session.setAttribute("admin", adminEntity.getAdminname());
           session.setAttribute("isAdmin", true); // 관리자로 로그인한 경우 "isAdmin"을 true로 설정
           return "admin/adminmain"; 
        
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "admin/adminlogin";
        }
    }

   
   // 로그아웃 핸들러 메서드, 세션을 무효화하여 로그아웃 처리
   @GetMapping("adminlogout")
   public String adminloggout(HttpSession session) {
      session.invalidate();
      return "redirect:/adminlogin";
   }
   
   // 고객정보리스트
   @GetMapping("/customerlist")
   public String findAll(Model model) {
      List<CustomerDto> customerDtoList = adminService.findAll();
      // 어떠한 html로 가져갈 데이터가 있다면 model 사용
      model.addAttribute("customerlist", customerDtoList);
      return "admin/customerlist";
   }
   
   // 고객 상세 조회
   @GetMapping("/customer/{customerid}")
   public String findById(@PathVariable String customerid, Model model) {
      CustomerDto customerDto = adminService.findById(customerid);
      model.addAttribute("customer", customerDto);
      return "admin/detail";
   }
   
   // 고객 추방
   @GetMapping("/customer/delete/{customerid}")
   public String deleteByCustomerid(@PathVariable String customerid) {
	   adminService.deleteByCustomerid(customerid);
      return "redirect:/admin/customerlist";
   }
   
   // 고객 검색 기능
   @GetMapping("/customer/search")
   public String searchCustomers(String aa, Model model) {
       List<CustomerEntity> searchResults = adminService.searchCustomers(aa);
       model.addAttribute("customerlist", searchResults);
       return "admin/customerlist"; 
   }

}