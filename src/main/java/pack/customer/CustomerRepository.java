package pack.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

	// 아이디로 회원 중복 조회(아이디 중복)
	Optional<CustomerEntity> findByCustomerid(String customerid);

	// 휴대전화로 회원 중복 조회(휴대전화 중복)
	Optional<CustomerEntity> findByPhonenumber(String phonenumber);

	// 닉네임으로 회원 중복 조회(닉네임 중복)
	Optional<CustomerEntity> findByCustomernickname(String customernickname);

	// 이름과 휴대전화로 회원 정보 조회 (아이디 찾기)
	CustomerEntity findByCustomernameAndPhonenumber(String customername, String phonenumber);

	// 닉네임, 이름, 휴대전화로 정보 조회 (비밀번호 찾기)
	CustomerEntity findByCustomeridAndCustomernameAndPhonenumber(String customerid, String customername, String phonenumber);

	// 고객 추방
	void deleteByCustomerid(String customerid);

	// 일반 회원 정보 조회
	@Query("SELECT c FROM CustomerEntity c WHERE c.isseller = false")
	Page<CustomerEntity> findPagedCustomers(Pageable pageable);
	// 판매자 정보 조회
	@Query("SELECT c FROM CustomerEntity c WHERE c.isseller = true ")
	Page<CustomerEntity> findPagedCustomersseller(Pageable pageable);

	// 고객 아이디로 검색 하기
	List<CustomerEntity> findByCustomeridContaining(String keyword);
	// 고객 이름으로 검색하기
	List<CustomerEntity> findByCustomernameContaining(String keyword);

}