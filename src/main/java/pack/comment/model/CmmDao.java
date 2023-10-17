package pack.comment.model;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CmmDao {
	@Autowired
	private CmmDataMapper dataMapper;

	public int deleteComm(String num) {
		return dataMapper.deleteComm(num);
	}

	public ArrayList<CommentDto> selectCommentsByNum(int num) {
		return dataMapper.selectCommentsByNum(num);
	}

	public void insertComment(CommentDto commentDto) {
		dataMapper.insertComment(commentDto);
	}

	// 수정용 댓글 번호 조회(수정전)
	public CommentDto selectCommentNum(int cnum) {
		CommentDto dto = dataMapper.selectCommentNum(cnum);
		return dto;
	}

	// 댓글 수정
	public boolean updateCom(String content, int id) {
		boolean b = false;
		int r = dataMapper.updateCom(content, id);
		if (r > 0)
			b = true;
		return b;
	}

	// 댓글 수
	public int countcomment(int num) {
		return dataMapper.countcomment(num);
	}
}
