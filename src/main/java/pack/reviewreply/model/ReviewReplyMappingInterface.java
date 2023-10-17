package pack.reviewreply.model;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.reviewreply.controller.ReviewReplyBean;

@Mapper
public interface ReviewReplyMappingInterface {

	// 댓글 작성
	@Insert("insert into reviewreply (renickname ,reviewno, comment, replydate) values (#{renickname} ,#{reviewno}, #{comment}, #{replydate})")
	int insertreply(ReviewReplyBean bean);
	
	// 댓글 조회
	@Select("select * from reviewreply where reviewno=#{reviewno} order by replyid desc")
	List<ReviewReplyDto> selectreply(int reviewno);
	
	// 댓글 수정전 조회
	@Select("select * from reviewreply where replyid=#{replyid}")
	ReviewReplyDto selectOne(int replyid);
	
	// 댓글 수정
	@Update("update reviewreply set comment=#{comment} where replyid=#{replyid}")
	int updatereply(ReviewReplyBean bean);
	
	// 댓글 삭제
	@Delete("delete from reviewreply where replyid=#{replyid}")
	int deletereply (int replyid);
	
	// 리뷰 전체 삭제를 위해 특정 reviewid의 댓글 삭제해야함
	@Delete("delete from reviewreply where reviewno=#{reviewid}")
	int deletereplypart (int reviewid);
}
