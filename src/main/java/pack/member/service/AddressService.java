package pack.member.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pack.entity.Address;
import pack.customer.CustomerEntity;
import pack.member.repository.AddressRepository;
import pack.member.repository.MyPageRepository;

@Service
@RequiredArgsConstructor
public class AddressService {
	private final AddressRepository addressRepository;
    private final MyPageRepository customerRepository;
    
    public List<Address> getAddressesByCustomerid(String customerid) {
        // 고객 아이디를 기반으로 배송지 목록 조회
        Optional<CustomerEntity> customerOptional = customerRepository.findByCustomerid(customerid);
        if (customerOptional.isPresent()) {
            CustomerEntity customer = customerOptional.get();
            return addressRepository.findByCustomer(customer);
        } else {
            // 고객 정보가 없을 경우 처리 (예: 빈 목록 반환 또는 에러 처리)
            return Collections.emptyList(); // 빈 목록 반환 또는 다른 에러 처리 방식을 선택하세요.
        }
    }
    
    public Optional<Address> getAddressById(int addressid) {
        // 주소 ID를 기반으로 주소 엔터티 조회
        return addressRepository.findById(addressid);
    }
    
    public void setDefaultAddress(String customerid, int addressid) {
        // 현재 로그인한 고객 정보 가져오기
        Optional<CustomerEntity> customerOptional = customerRepository.findByCustomerid(customerid);

        if (customerOptional.isPresent()) {
            CustomerEntity customer = customerOptional.get();

            // 기본 배송지 설정 해제
            List<Address> addresses = addressRepository.findByCustomer(customer);
            for (Address address : addresses) {
                address.setIsdefault(true);
                addressRepository.save(address);
            }

            // 선택한 주소를 기본 배송지로 설정
            Optional<Address> selectedAddressOptional = addressRepository.findById(addressid);
            if (selectedAddressOptional.isPresent()) {
                Address selectedAddress = selectedAddressOptional.get();
                selectedAddress.setIsdefault(false);
                addressRepository.save(selectedAddress);
            }
        } else {
            // 고객 정보가 없을 경우 처리 (예: 에러 처리)
            // throw new CustomException("고객 정보를 찾을 수 없습니다."); // CustomException은 예외 처리 방식에 따라서 수정하세요.
        }
    }
    
    @Transactional
    public void resetDefaultAddresses(CustomerEntity customerEntity) {
        // 고객에게 속한 모든 주소를 비기본 상태로 설정 (isdefault = 0)
        List<Address> userAddresses = addressRepository.findByCustomer(customerEntity);
        for (Address address : userAddresses) {
            address.setIsdefault(false);
            addressRepository.save(address);
        }
    }
    
    public void setDefaultAddress(String customerid, Address selectedAddress) {
        // 현재 로그인한 고객 정보 가져오기
        Optional<CustomerEntity> customerOptional = customerRepository.findByCustomerid(customerid);

        if (customerOptional.isPresent()) {
            CustomerEntity customer = customerOptional.get();
            
            // 현재 기본 배송지 해제
            List<Address> addresses = addressRepository.findByCustomer(customer);
            for (Address address : addresses) {
                address.setIsdefault(false);
                addressRepository.save(address);
            }
            
            // 선택한 주소를 기본 배송지로 설정
            selectedAddress.setIsdefault(true);
            addressRepository.save(selectedAddress);
        } else {
            // 고객 정보가 없을 경우 처리 (예: 에러 처리)
           // CustomException은 예외 처리 방식에 따라서 수정하세요.
        }
    }



    public void addAddress(Address address) {
        // 새로운 배송지 추가
        addressRepository.save(address);
    }
    
    public void updateAddress(Address address) {
        // 기본 배송지 설정을 포함한 주소 정보 업데이트
        addressRepository.save(address);
    }

    public void deleteAddress(int addressId) {
        // 주소 삭제 로직 구현
        addressRepository.deleteById(addressId);
    }
    
    

}
