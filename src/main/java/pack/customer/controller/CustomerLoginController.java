package pack.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import pack.customer.CustomerDto;
import pack.customer.RecaptchaConfig;
import pack.customer.RecaptchaService;

@Controller
@RequiredArgsConstructor
public class CustomerLoginController {

    private final CustomerService customerService;
    private final RecaptchaService recaptchaService;

    
    // 로그인 페이지 표시
    @GetMapping("/login")
    public String loginForm(HttpSession session) {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute CustomerDto customerDto, HttpSession session, Model model) {
        // [S] 리캡차 검증
        try {
            recaptchaService.verify(customerDto.getRecaptcha());

            // 리캡차 검증 통과하면 계속 진행
            if (RecaptchaConfig.verify(customerDto.getRecaptcha())) {
                CustomerDto loginResult = customerService.login(customerDto);
      
     
                if (loginResult != null) {
                    session.setAttribute("loginid", loginResult.getCustomerid());
                    session.setAttribute("nickname", loginResult.getCustomernickname());

                    String previousPage = (String) session.getAttribute("previousPage");

                    if (previousPage != null) {
                        session.removeAttribute("previousPage");
                        // 이전 페이지로 Redirect
                     
                        return "redirect:" + previousPage;
                    } else {
                    	 // 이전 페이지 정보가 없을 경우 기본 페이지로 Redirect
                    
                        return "redirect:/";
                    }
                } else {
                    // 컨트롤러와 뷰의 데이터 전달 / 로그인 실패라는 값을 msg에 추가
                    model.addAttribute("msg", "아이디나 비밀번호가 일치하지 않습니다");
                    return "user/login";
                }
            } else {
                // 리캡차 검증 실패 처리
                model.addAttribute("msg", "reCAPTCHA 검증 실패");
                return "user/login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 리캡차 검증 실패 처리
            model.addAttribute("msg", "reCAPTCHA 검증 실패");
            return "user/login";
        }
    }

    // 로그아웃 핸들러 메서드, 세션을 무효화하여 로그아웃 처리
    @GetMapping("logout")
    public String logout(HttpSession session) {
        String previousPage = (String) session.getAttribute("previousPage");
        session.invalidate();

        if (previousPage != null) {
            // 이전 페이지로 Redirect
            session.removeAttribute("previousPage"); // Redirect 후 세션에서 제거
            return "redirect:" + previousPage;
        } else {
            // 이전 페이지 정보가 없을 경우 기본 페이지로 Redirect
            return "redirect:/";
        }
    }

    @GetMapping("/update")
    public String updateForm(HttpSession session, Model model) {
        String loginid = (String) session.getAttribute("loginid");

        // 로그인 세션이 없는 경우 로그인 페이지로 리디렉션
        if (loginid == null) {
            return "redirect:/login";
        }

        CustomerDto customerDto = customerService.updateForm(loginid);
        model.addAttribute("updateMember", customerDto); // "updateMember"로 객체 추가
        return "mypage/update";
    }

	@PostMapping("/update")
	public String update(@ModelAttribute("updateMember") CustomerDto customerDto, HttpSession session, Model model) {
		// 세션에서 로그인한 사용자의 닉네임을 가져옴
		String loginid = (String) session.getAttribute("loginid");

		// 로그인한 사용자의 정보를 데이터베이스에서 가져옴
		CustomerDto loggedInUser = customerService.updateForm(loginid);

		// 입력한 비밀번호와 데이터베이스에서 가져온 비밀번호를 비교
		if (loggedInUser != null && loggedInUser.getPasswordhash().equals(customerDto.getPasswordhash())) {
			// 새로운 비밀번호 필드가 비어있지 않다면 업데이트 수행
			if (customerDto.getNewpassword() != null && !customerDto.getNewpassword().isEmpty()) {
				loggedInUser.setPasswordhash(customerDto.getNewpassword()); // 새로운 비밀번호로 업데이트
				customerService.update(loggedInUser);
			}
			 // 모델에 성공 플래그 설정
	        model.addAttribute("updateSuccess", true);
			return "mypage/update";
		} else {
			model.addAttribute("passwordMismatch", true);
			return "mypage/update";
		}
	}

	@GetMapping("/delete")
	public String deleteConfirmationPage(HttpSession session, Model model) {
		String loginid = (String) session.getAttribute("loginid");
		if (loginid != null) {
			model.addAttribute("data", loginid);
			return "mypage/delete";
		} else {
			// 로그인되지 않은 경우 처리 (예: 로그인 페이지로 리다이렉트)
			return "redirect:/login";
		}
	}

	@PostMapping("/delete/{customerid}")
	public String deleteAccountConfirmed(@PathVariable String customerid, HttpSession session) {
		// 사용자 ID에 따른 계정 삭제 로직 구현
		customerService.deactivateCustomer(customerid);

		// 세션 무효화
		session.invalidate();

		// 확인 페이지 또는 홈 페이지로 리디렉션
		return "mypage/deleteok"; // 삭제 확인 페이지를 만들 수 있습니다.
	}


}
