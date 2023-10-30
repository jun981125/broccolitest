package pack.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    // 고객아이디를 통해 고객 상세정보 조회
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


    // 일반회원 정보 조회
    public Page<CustomerEntity> findPagedCustomers(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return customerRepository.findPagedCustomers(pageable);
    }
    // 판매자 정보 조회
    public Page<CustomerEntity> findPagedCustomersseller(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return customerRepository.findPagedCustomersseller(pageable);
    }


    // 고객 아이디로 검색 조회
    public List<CustomerEntity> searchCustomersByCustomerid(String keyword) {
        return customerRepository.findByCustomeridContaining(keyword);
    }

    // 고객 이름으로 검색 조회
    public List<CustomerEntity> searchCustomersByCustomername(String keyword) {
        return customerRepository.findByCustomernameContaining(keyword);
    }
}