package pack.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pack.customer.CustomerDto;


@Controller
@RequiredArgsConstructor
public class CustomerRegisterController {

    private final CustomerService customerService;

    // 회원가입 페이지
    @GetMapping("/register")
    public String Register() {
        return "user/register";
    }

    // 회원가입
    @PostMapping("/register")
    public String register(@ModelAttribute @Valid CustomerDto customerDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 에러가 있는 경우
            return "user/register";
        }
        // 회원 정보
        customerService.save(customerDto);
        return "user/login"; // 가입 후 로그인 페이지로 이동
    }

    

    // 아이디 중복 여부 확인
    @PostMapping("id-Check")
    public @ResponseBody String idCheck(@RequestParam("customerid") String customerid) {
        //    System.out.println("customerid = " + customerid);
        String checkResult = customerService.idCheck(customerid);
        return checkResult;
    }
    // 아이디 중복 여부 확인
    @PostMapping("id-Check1")
    public @ResponseBody String idCheck1(@RequestParam("customerid") String customerid) {
        //	System.out.println("customerid = " + customerid);
        String checkResult2 = customerService.idCheck1(customerid);
        return checkResult2;
    }


    // 휴대전화 중복 여부 확인
    @PostMapping("phone-Check")
    public @ResponseBody String phonenumberCheck(@RequestParam("phonenumber") String phonenumber) {
        //	System.out.println("phonenumber = " + phonenumber);
        String phoneCheckResult = customerService.phonenumberCheck(phonenumber);
        return phoneCheckResult;
    }

    // 휴대전화 중복 여부 확인
    @PostMapping("phone-Check1")
    public @ResponseBody String phonenumberCheck1(@RequestParam("phonenumber") String phonenumber) {
        //	System.out.println("phonenumber = " + phonenumber);
        String phoneCheckResult3 = customerService.phonenumberCheck1(phonenumber);
        return phoneCheckResult3;
    }


    // 닉네임 중복 여부 확인
    @PostMapping("nickname-Check")
    public @ResponseBody String nicknameCheck(@RequestParam("customernickname") String customernickname) {
        String nicknameCheckResult = customerService.nicknameCheck(customernickname);
        return nicknameCheckResult;
    }

    // 닉네임 중복 여부 확인
    @PostMapping("sellernickname-Check")
    public @ResponseBody String sellernicknameCheck(@RequestParam("customernickname") String customernickname) {
        String nicknameCheckResult = customerService.nicknameCheck(customernickname);
        return nicknameCheckResult;
    }

}
