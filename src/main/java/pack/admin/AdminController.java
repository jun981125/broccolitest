package pack.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
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


@Controller
@RequiredArgsConstructor   // 생성자 자동으로 생성하는 어노테이션
public class AdminController {
    // 생성자 주입

    private final AdminService adminService;


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
        return "redirect:/customerlist";
    }


    // 일반회원 정보 리스트
    @GetMapping("/customerlist")
    public String findcustomer(Model model, @RequestParam(defaultValue = "1") int page) {
        int pageSize = 10; // 페이지당 보여줄 항목 수
        Page<CustomerEntity> customerPage = adminService.findPagedCustomers(page, pageSize);
        List<CustomerEntity> customerDtoList = customerPage.getContent();

        model.addAttribute("customerlist", customerDtoList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customerPage.getTotalPages());

        return "admin/customerlist";
    }

    // 판매자 회원 정보 리스트
    @GetMapping("/customerlist_seller")
    public String findcustomerseller(Model model, @RequestParam(defaultValue = "1") int page) {
        int pageSize = 10; // 페이지당 보여줄 항목 수
        Page<CustomerEntity> customerPage = adminService.findPagedCustomersseller(page, pageSize);
        List<CustomerEntity> customerDtoList = customerPage.getContent();

        model.addAttribute("customerlist", customerDtoList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customerPage.getTotalPages());

        return "admin/customerlist_seller";
    }


    // 일반 회원 검색 기능
    @GetMapping("/customer/search")
    public String searchCustomers(@RequestParam(name = "s_type") String searchType, @RequestParam(name = "search") String keyword, Model model) {
        List<CustomerEntity> searchResults;

        if (searchType.equals("customerid")) {
            searchResults = adminService.searchCustomersByCustomerid(keyword);
        } else if (searchType.equals("customername")) {
            searchResults = adminService.searchCustomersByCustomername(keyword);
        } else {
            searchResults = new ArrayList<>();
        }

        model.addAttribute("customerlist", searchResults);
        return "admin/customerlist";
    }

    // 판매자 회원 검색 기능
    @GetMapping("/customer/seller/search")
    public String searchCustomerseller(@RequestParam(name = "s_type") String searchType, @RequestParam(name = "search") String keyword, Model model) {
        List<CustomerEntity> searchResults;

        if (searchType.equals("customerid")) {
            searchResults = adminService.searchCustomersByCustomerid(keyword);
        } else if (searchType.equals("customername")) {
            searchResults = adminService.searchCustomersByCustomername(keyword);
        } else {
            searchResults = new ArrayList<>();
        }

        model.addAttribute("customerlist", searchResults);
        return "admin/customerlist_seller";
    }
}