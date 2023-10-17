package pack.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pack.customer.CustomerDto;
import pack.customer.CustomerEntity;
import pack.customer.CustomerRepository;

@Service
@RequiredArgsConstructor 
public class AdminService {
	 private final AdminRepository adminRepository;
	 private final CustomerRepository customerRepository;

	 	// 관리자 로그인시 (아이디, 비밀번호)
	    public AdminEntity adminlogin(String adminid, String adminpasswd) {
	        return adminRepository.findByAdminidAndAdminpasswd(adminid, adminpasswd);
	    }
	    
	
	    // 모든 고객 정보 조회
        public List<CustomerDto> findAll(){
             List<CustomerEntity> customerEntityList = customerRepository.findAll();
             List<CustomerDto> customerDtoList = new ArrayList<>();
             // entity가 여러 개 담긴 리스트를 dto가 여러 개 담긴 리스트객체로 옮겨 담는건데
             // 그냥 대입하는거면 불가능 하나씩 꺼내서 옮겨 담는 과정이 필요
             for(CustomerEntity customerEntity:customerEntityList) {    // entity객체를 for each로 접근, 하나씩 dto로 변환
                 customerDtoList.add(CustomerDto.toCustomerDto(customerEntity));  // customerDtoList는 dto객체를 담긴 위한 list, 변환된 객체를 list에 담음
             }
             return customerDtoList;
         }
        
        
        // 고객아이디를 통해 고객 정보 조회
        public CustomerDto findById(String customerid) {
            Optional<CustomerEntity> optional = customerRepository.findByCustomerid(customerid);
            if(optional.isPresent()) {
                return CustomerDto.toCustomerDto(optional.get());
            }else {
                return null;
            }
        }
        
        
        // 고객 추방
        @Transactional
        public void deleteByCustomerid(String customerid) {
            customerRepository.deleteByCustomerid(customerid);
        }
         
        
        // 고객 검색 
        public List<CustomerEntity> searchCustomers(String aa) {
            return customerRepository.findByCustomernameContaining(aa);
        }
}