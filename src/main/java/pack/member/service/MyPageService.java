package pack.member.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pack.customer.CustomerDto;
import pack.customer.CustomerEntity;

import pack.member.repository.MyPageRepository;

@Service
@RequiredArgsConstructor
public class MyPageService {

	private final MyPageRepository customerRepository;
	private static final Logger logger = LoggerFactory.getLogger(MyPageService.class);


	// 회원 정보를 데이터베이스에 저장
	public void save(CustomerDto customerDto) {
		CustomerEntity customerEntity = CustomerEntity.toCustomerEntity(customerDto);
		customerRepository.save(customerEntity);

	}

	// 로그인 성공, 실패 기능 처리
	public CustomerDto login(CustomerDto customerDto) {
		/*
		 * 1. 회원이 입력한 이메일로 DB에서 조회를 함 2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
		 */
		Optional<CustomerEntity> byCustomerId = customerRepository
				.findByCustomerid(customerDto.getCustomerid());
		if (byCustomerId.isPresent()) {
			// 조회 결과가 있다
			CustomerEntity customerEntity = byCustomerId.get();
			if (customerEntity.getPasswordhash().equals(customerDto.getPasswordhash())) {
				// 비밀번호 일치
				// entity -> dto로 변환 후 리턴
				CustomerDto dto = CustomerDto.toCustomerDto(customerEntity);
				return dto;
			} else {
				// 비밀번호 불일치(로그인 실패)
				return null;
			}

		} else {
			// 조회 결과가 없다
			return null;
		}

	}
	
	// customerid를 사용하여 customernum 조회
    public int getCustomernumByCustomerid(String customerid) {
        Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findByCustomerid(customerid);
        return optionalCustomerEntity.map(CustomerEntity::getCustomernum).orElse(-1); // -1은 오류 또는 미조회 시 반환할 값
    }
    
    public CustomerEntity getCustomerByCustomerId(String customerid) {
        // 고객 ID를 사용하여 고객 정보를 조회하는 로직 추가
        Optional<CustomerEntity> customerOptional = customerRepository.findByCustomerid(customerid);
        return customerOptional.orElse(null); // 고객 정보가 없을 경우 null 반환
    }

	public CustomerDto updateForm(String customerid) {
		Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findByCustomerid(customerid);
		if (optionalCustomerEntity.isPresent()) {
			return CustomerDto.toCustomerDto(optionalCustomerEntity.get());
		} else {
			return null;
		}
	}

	public void update(CustomerDto customerDTO) {
		// 기존 회원 정보 가져오기
		Optional<CustomerEntity> optionalCustomerEntity = customerRepository
				.findByCustomerid(customerDTO.getCustomerid());

		if (optionalCustomerEntity.isPresent()) {
			// 기존 회원 정보가 존재할 경우
			CustomerEntity customerEntity = optionalCustomerEntity.get();

			// 수정할 정보로 업데이트
			//CustomerEntity.setCustomernum(CustomerDTO.getCustomernum());
	        customerEntity.setCustomerid(customerDTO.getCustomerid());
	        customerEntity.setCustomername(customerDTO.getCustomername());
	        customerEntity.setCustomernickname(customerDTO.getCustomernickname());
	        customerEntity.setEmail(customerDTO.getEmail());
	        customerEntity.setPasswordhash(customerDTO.getPasswordhash());
	        customerEntity.setCustomergen(customerDTO.getCustomergen());
	        customerEntity.setPhonenumber(customerDTO.getPhonenumber());
	        customerEntity.setZipcode(customerDTO.getZipcode());
	        customerEntity.setDetailaddress(customerDTO.getDetailaddress());
	        customerEntity.setAddress(customerDTO.getAddress());
			// 업데이트된 정보를 데이터베이스에 저장
			customerRepository.save(customerEntity);
		}
	}



	// 회원 삭제 (마스킹 처)
	public void deleteByCustomerid(String customerid) {
		// 회원 정보를 데이터베이스에서 가져옴
		Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findByCustomerid(customerid);

		if (optionalCustomerEntity.isPresent()) {
			// 가져온 회원 정보가 존재할 경우
			CustomerEntity customerEntity = optionalCustomerEntity.get();

			// 회원 정보를 마스킹 처리
	        customerEntity.setCustomerid("탈퇴회원");
	        customerEntity.setCustomername("탈퇴회원");
	        customerEntity.setCustomernickname("탈퇴회원");
	        customerEntity.setEmail("");
	        customerEntity.setPasswordhash("");
	        customerEntity.setCustomergen("");
	        customerEntity.setPhonenumber("");
	        customerEntity.setZipcode("");
	        customerEntity.setDetailaddress("");
	        customerEntity.setAddress("");

			// 마스킹 처리된 회원 정보를 데이터베이스에 저장
			customerRepository.save(customerEntity);

			// 해당 회원이 작성한 게시물도 마스킹 처리
			//maskUserPosts(existingCustomer.getCustomernickname());
		}
	}
	
	// 회원 비활성화 (탈퇴)
	public void deactivateCustomer(String customerid) {
	    Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findByCustomerid(customerid);
	    if (optionalCustomerEntity.isPresent()) {
	        CustomerEntity customerEntity = optionalCustomerEntity.get();
	        customerEntity.setCustomername("탈퇴회원"); 
	        customerEntity.setCustomerid("탈퇴회원"); 
	        customerEntity.setEmail("");
	        customerEntity.setPasswordhash("");
	        customerEntity.setCustomergen("");
	        customerEntity.setPhonenumber("");
	        customerEntity.setZipcode("");
	        customerEntity.setDetailaddress("");
	        customerEntity.setAddress("");
	        customerEntity.setActive(false); // 회원 비활성화
	        customerRepository.save(customerEntity);
	    }
	}
	
	

	
	
    
}