package pack.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
	// 아이디로 회원 정보 조회(아이디 중복)
	Optional<CustomerEntity> findByCustomerid(String customerid);
	
	// 휴대전화로 회원 정보 조회(휴대전화 중복)
	Optional<CustomerEntity> findByPhonenumber(String phonenumber);
	
	// 휴대전화로 회원 정보 조회(휴대전화 중복)
	   Optional<CustomerEntity> findByCustomernickname(String customernickname);
    
    // 이름과 휴대전화로 회원 정보 조회 (아이디 찾기)
	CustomerEntity findByCustomernameAndPhonenumber(String customername, String phonenumber);
	
    // 닉네임, 이름, 휴대전화로 정보 조회 (비밀번호 찾기)
	CustomerEntity findByCustomeridAndCustomernameAndPhonenumber(String customerid, String customername, String phonenumber);

	List<CustomerEntity> findAll();
	
	void deleteByCustomerid(String customerid);
	
	List<CustomerEntity> findByCustomernameContaining(String aa);

}