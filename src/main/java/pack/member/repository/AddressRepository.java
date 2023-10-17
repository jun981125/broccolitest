package pack.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pack.entity.Address;
import pack.customer.CustomerEntity;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
	List<Address> findByCustomer(CustomerEntity customer);

	void deleteById(int addressId);
}
