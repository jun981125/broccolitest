package pack.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pack.customer.CustomerEntity;
import pack.entity.CustomerPoint;

@Repository
public interface CustomerPointRepository extends JpaRepository<CustomerPoint, Integer> {
	List<CustomerPoint> findByCustomer(CustomerEntity customer);
}
