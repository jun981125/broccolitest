package pack.qna.model;

import lombok.Data;

@Data
public class QnaDto {
	private int num, readcnt, gnum, onum, nested;
	private String id, title, cont, bdate,name,filename;
}
