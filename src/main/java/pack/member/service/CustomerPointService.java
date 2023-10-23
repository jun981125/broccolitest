package pack.member.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pack.customer.CustomerEntity;
import pack.entity.CustomerPoint;
import pack.member.repository.CustomerPointRepository;
import pack.member.repository.MyPageRepository;

@Service
@RequiredArgsConstructor
public class CustomerPointService {
	private final CustomerPointRepository customerPointRepository;
	private final MyPageRepository myPageRepository;


	public void addCustomerPoint(CustomerEntity customer, int orderAmount) {
	    // CustomerPointEntity를 생성하여 필요한 데이터를 설정
	    CustomerPoint customerPoint = new CustomerPoint();
	    customerPoint.setCustomer(customer);
	    customerPoint.setOrderdate(LocalDate.now());
	    customerPoint.setOrderamount(orderAmount);

	    // CustomerEntity를 저장
	    myPageRepository.save(customer); // CustomerEntity를 저장

	    // CustomerPointEntity를 저장
	    customerPointRepository.save(customerPoint);
	}


	public List<CustomerPoint> getPointHistoryByCustomer(CustomerEntity customer) {
	    return customerPointRepository.findByCustomer(customer);
	}
}
