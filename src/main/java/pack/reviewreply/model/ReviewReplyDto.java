package pack.reviewreply.model;

import lombok.Data;

@Data
public class ReviewReplyDto {
	private int replyid, reviewno;
	private String renickname, title, comment, replydate;
}
