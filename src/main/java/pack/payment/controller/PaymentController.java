package pack.payment.controller;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import pack.payment.model.PaymentDataProcess;
import pack.payment.model.ProductVo;




@Controller
public class PaymentController {

   @Autowired
   private PaymentDataProcess dataProcess;
    


    @PostMapping("/product_id_transform")
    public String product_id_transform(@RequestParam("productid") int productid,
    															Model model) {
        System.out.println("POST 요청이 도착했습니다.");
        System.out.println("Product ID: " + productid);
       ProductVo vo = dataProcess.getProductData(productid);
      model.addAttribute("product", vo);
        return "payment/product";
    }
    

   
}
