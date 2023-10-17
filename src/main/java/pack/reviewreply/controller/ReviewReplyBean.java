package pack.reviewreply.controller;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReviewReplyBean {
	private int replyid, reviewno;
	private String renickname, title, comment, replydate;
	public void setReplydate() {
		LocalDate localDate = LocalDate.now();
		int year = localDate.getYear();
		int month = localDate.getMonthValue();
		int day = localDate.getDayOfMonth();
		this.replydate = year + "-" + month + "-" + day;
	}
}
