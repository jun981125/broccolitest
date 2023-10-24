package pack.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pack.customer.CustomerEntity;
import pack.entity.CustomerPoint;
import pack.member.service.CustomerPointService;

@Controller
public class CustomerPointController {

    private final CustomerPointService customerPointService;

    @Autowired
    public CustomerPointController(CustomerPointService customerPointService) {
        this.customerPointService = customerPointService;
    }

    @GetMapping("/point")
    public String showPointHistory(CustomerEntity customer, Model model) {
        List<CustomerPoint> pointHistory = customerPointService.getPointHistoryByCustomer(customer);
        model.addAttribute("pointHistory", pointHistory);
        return "mypage/update"; 
    }
}
