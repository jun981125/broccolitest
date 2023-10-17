package pack.comment.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CmmDataMapper {
	
	//게시글의 댓글 목록 조회
	@Select("select id, num, content,idkey,customernickname FROM comment_table WHERE num = #{num}")
	ArrayList<CommentDto> selectCommentsByNum(int num); 
	
	// 댓글 수정 전 조회
	@Select("select num,content,idkey,id from comment_table where id=#{cnum}")
	CommentDto selectCommentNum(int cnum);
	
	// 댓글 수정 시행
	@Update("update comment_table set content=#{content} where id=#{id}")
	int updateCom(@Param("content")String content, @Param("id") int id);
	
	//댓 작성
	@Insert("insert into comment_table (idkey,num, content,customernickname) VALUES (#{idkey},#{num}, #{content},#{customernickname})")
	void insertComment(CommentDto commentDto);
	
	// 댓삭
	@Delete("delete from comment_table where id=#{num}")
	int deleteComm(String num);
	
	// 댓글 갯수 표시
	@Update("update commu set commentcount = (select count(id) from comment_table where num = #{num})	where num = #{num}")
	int countcomment(int num);
}
