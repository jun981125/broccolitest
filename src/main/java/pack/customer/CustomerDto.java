package pack.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerDto {
	private int customernum;
	
	@NotBlank(message = "아이디를 입력하세요")
	@Pattern(regexp = "^[a-z0-9]{5,20}$", message = "아이디는 영어 소문자와 숫자만 사용하여 5~20자리여야 합니다.")
	private String customerid;
	
	@NotBlank(message = "이름을 입력하세요")
	@Pattern(regexp = "^[가-힣]{2,10}$")
	private String customername;
	
	@NotBlank
	private String customernickname;

	private String email;

	@NotBlank(message = "비밀번호를 입력하세요")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{5,16}", message = "비밀번호는 5~16자 영어 대소문자, 숫자, 특수문자를 사용하세요.")
    private String passwordhash;
	
    private String customergen;
    
    @Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", message = "올바른 전화번호 형식이 아닙니다")
    @NotBlank(message = "휴대전화를 입력하세요")
    private String phonenumber;
    private String zipcode;
    private String detailaddress;
    private String address;
    
    private Boolean isseller = false;
    private String newpassword;
    private String recaptcha; 
    
    public static CustomerDto toCustomerDto(CustomerEntity customerEntity) {
    	CustomerDto customerDto = new CustomerDto();
    	customerDto.setCustomernum(customerEntity.getCustomernum());
    	customerDto.setCustomerid(customerEntity.getCustomerid());
    	customerDto.setCustomername(customerEntity.getCustomername());
    	customerDto.setCustomernickname(customerEntity.getCustomernickname());
    	customerDto.setEmail(customerEntity.getEmail());
    	customerDto.setPasswordhash(customerEntity.getPasswordhash());
    	customerDto.setCustomergen(customerEntity.getCustomergen());
    	customerDto.setPhonenumber(customerEntity.getPhonenumber());
    	customerDto.setZipcode(customerEntity.getZipcode());
    	customerDto.setDetailaddress(customerEntity.getDetailaddress());
    	customerDto.setAddress(customerEntity.getAddress());
    	customerDto.setIsseller(customerEntity.isIsseller());
    	return customerDto;
    }
}
