package pack.noticeboard.controller;

import java.time.LocalDate;
import lombok.Data;

@Data
public class AnmtBean {
	private int num;
	private String adminname, title, content, filename, cdate;
	private String searchName, searchValue;
	private int eventnotice;
	
	public void setCdate() {
		LocalDate localDate = LocalDate.now();
		int year = localDate.getYear();
		int month = localDate.getMonthValue();
		int day = localDate.getDayOfMonth();
		this.cdate = year + "-" + month + "-" + day;	
	}
}
