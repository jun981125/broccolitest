package pack.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import pack.entity.Address;
import pack.customer.CustomerEntity;
import pack.member.repository.MyPageRepository;
import pack.member.service.AddressService;

@Controller
@RequiredArgsConstructor
public class AddressController {
	
	@Autowired
    private AddressService addressService;
	@Autowired
    private MyPageRepository customerRepository;
    
	@GetMapping("/address")
    public String addressList(Model model, HttpSession session) {
        // 현재 로그인한 고객 정보 가져오기
        String loginid = (String) session.getAttribute("loginid");
        if (loginid != null) {
            // 고객의 배송지 목록 가져오기
            List<Address> addresses = addressService.getAddressesByCustomerid(loginid);
            model.addAttribute("addresses", addresses);
            return "mypage/address";
        } else {
            // 로그인되지 않은 경우 처리 (예: 로그인 페이지로 리다이렉트)
            return "redirect:/login";
        }
    }

	@GetMapping("/add-address")
	public String addAddressForm(Model model) {
	    model.addAttribute("address", new Address());
	    return "mypage/address";
	}


	@PostMapping("/add-address")
	public String addAddressSubmit(@RequestParam String zipcode, @RequestParam String address, @RequestParam String detailaddress, @RequestParam String reference, HttpSession session, Model model) {
		// 현재 로그인한 고객 정보 가져오기
		String loginid = (String) session.getAttribute("loginid");
		System.out.println("zipcode: " + zipcode);
		if (loginid != null) {
			// 고객 정보를 주소 엔티티에 연결
			Optional<CustomerEntity> customerOptional = customerRepository.findByCustomerid(loginid);
			if (customerOptional.isPresent()) {
				CustomerEntity customer = customerOptional.get();
				Address addressEntity = new Address();
				addressEntity.setCustomer(customer);
				addressEntity.setZipcode(zipcode);
				addressEntity.setAddress(address);
				addressEntity.setDetailaddress(detailaddress);
				addressEntity.setReference(reference);

				// 추가하는 주소를 기본 배송지로 설정
				addressEntity.setIsdefault(true);

				addressService.addAddress(addressEntity);

				// 추가된 주소 목록을 다시 조회
				List<Address> addresses = addressService.getAddressesByCustomerid(loginid);
				model.addAttribute("addresses", addresses);
			} else {
				// 고객 정보가 없을 경우 처리 (예: 에러 페이지로 리다이렉트)
				return "redirect:/login"; // 에러 페이지 경로로 수정하세요.
			}
		}
		return "redirect:/address"; // 주소 목록 페이지로 이동
	}





	@GetMapping("/get-address/{addressid}")
	@ResponseBody
	public Map<String, String> getAddress(@RequestParam("addressid") int addressid, Model model) {
	    Optional<Address> addressOptional = addressService.getAddressById(addressid);
	    Map<String, String> addressMap = new HashMap<>();
	    
	    if (addressOptional.isPresent()) {
	        Address address = addressOptional.get();
	        // 주소 정보를 Map에 담아 JSON 형식으로 반환
	        addressMap.put("zipcode", address.getZipcode());
	        addressMap.put("address", address.getAddress());
	        addressMap.put("customer", String.valueOf(address.getCustomer()));
	        addressMap.put("detailaddress", address.getDetailaddress());
	        addressMap.put("reference", address.getReference());
	        addressMap.put("addressid", String.valueOf(address.getAddressid()));
	        //model.addAttribute("address", address.getAddressid());
	    }
	    return addressMap;
	}
	
	@PostMapping("/update-address")
	@ResponseBody 
	public String updateAddress(@RequestBody Address address, HttpSession session, Model model) {
	    try {
	        // 로그인한 사용자의 customernum을 세션에서 가져옵니다.
	        String loginid = (String) session.getAttribute("loginid");
	        
	        // 사용자 엔티티를 가져옵니다.
	        Optional<CustomerEntity> customerOptional = customerRepository.findByCustomerid(loginid);

	        if (customerOptional.isPresent()) {
	            CustomerEntity customerEntity = customerOptional.get();
	            
	            // 주소 정보 업데이트 시 customernum을 사용자의 customernum으로 설정합니다.
	            address.setCustomer(customerEntity);
	            
	            // 업데이트된 주소 정보를 서비스를 통해 저장합니다.
	            addressService.updateAddress(address);
	            
	            return "Success"; 
	        } else {
	            return "Error: User not found"; 
	        }
	    } catch (Exception e) {
	        return "Error"; 
	    }
	}

	// 배송지 삭제 메서드
	@GetMapping("/delete-address/{addressid}")
	@ResponseBody
	public String deleteAddress(@PathVariable("addressid") int addressid) {
	    try {
	        // 주소 ID를 사용하여 해당 주소를 삭제합니다.
	        addressService.deleteAddress(addressid);
	        return "Success";
	    } catch (Exception e) {
	        return "Error";
	    }
	}
	
	@PostMapping("/set-default-address/{addressid}")
	public String setDefaultAddress(@PathVariable int addressid, HttpSession session) {
	    // 현재 로그인한 고객 정보 가져오기
	    String loginid = (String) session.getAttribute("loginid");

	    if (loginid != null) {
	        // 주소 엔터티 조회
	        Optional<Address> addressOptional = addressService.getAddressById(addressid);
	        if (addressOptional.isPresent()) {
	            Address address = addressOptional.get();
	            address.setIsdefault(true); // 선택한 주소를 기본 배송지로 설정
	            addressService.setDefaultAddress(loginid, address);
	        } else {
	            // 주소 엔터티가 없을 경우 처리 (예: 에러 페이지로 리다이렉트)
	            return "redirect:/error"; // 에러 페이지 경로로 수정하세요.
	        }
	    }

	    return "redirect:/address"; // 주소 목록 페이지로 이동
	}


	
	
	@PostMapping("/save-address")
    @ResponseBody
    public Map<String, String> saveAddress(@RequestBody Address address) {
        Map<String, String> response = new HashMap<>();

        try {
            // 주소 정보를 서비스로 전달하여 저장합니다.
            addressService.addAddress(address);
            response.put("status", "Success");
        } catch (Exception e) {
            response.put("status", "Error");
        }

        return response;
    }
	
}
