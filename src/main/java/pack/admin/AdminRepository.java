package pack.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Integer>{
	// 관리자 아이디,비밀번호 일치여부
	AdminEntity findByAdminidAndAdminpasswd(String adminid, String adminpasswd);
}
