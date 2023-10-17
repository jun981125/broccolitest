package pack.review.controller;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ReviewBean {
	// renickname은 session에서 받은 nickname
	private String rnickname, rcustomerid,title, rating, comment, reviewdate, rimage,  model, brand, pimage;
	private int rproductid , reviewid;
	private MultipartFile rimagePath;
	public void setReviewdate() {
		LocalDate localDate = LocalDate.now();
		int year = localDate.getYear();
		int month = localDate.getMonthValue();
		int day = localDate.getDayOfMonth();
		this.reviewdate = year + "-" + month + "-" + day;
	}
}
