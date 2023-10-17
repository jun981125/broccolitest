package pack.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pack.customer.CustomerEntity;

@Repository
public interface MyPageRepository extends JpaRepository<CustomerEntity, Integer> {
	// 닉네임으로 회원 정보 조회(아이디 중복)
	Optional<CustomerEntity> findByCustomerid(String customerid);
    
    // 이름과 이메일로 회원 정보 조회 (아이디 찾기)
    CustomerEntity findByCustomernameAndEmail(String customername, String email);
    
    // 닉네임, 이름, 이메일로 정보 조회 (비밀번호 찾기)
    Optional<CustomerEntity> findByCustomeridAndCustomernameAndEmail(String customerid, String customername, String email);
   
    
}