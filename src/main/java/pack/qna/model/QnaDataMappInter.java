package pack.qna.model;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.qna.controller.QnaBean;

@Mapper
public interface QnaDataMappInter {
	@Select("select * from qna order by gnum desc, onum asc")
    List<QnaDto> selectList();

    @Select("select * from qna where ${searchName} like concat('%', #{searchValue}, '%') order by gnum desc, onum asc")
    List<QnaDto> searchList(QnaBean bean);

    @Select("select * from qna where num=#{num}")
    QnaDto selectOne(String num);

    @Insert("insert into qna (id, title, cont, bdate, gnum,name,filename)\r\n"
    		+ " values (#{id}, #{title}, #{cont}, #{bdate}, #{gnum},#{name},#{filename})")
    int insertData(QnaBean bean);

    @Update("update qna set readcnt=readcnt + 1 where num=#{num}")
    void updateReadcnt(String num);

    @Update("update qna set title=#{title},cont=#{cont} where num=#{num}")
    int updateData(QnaBean bean);

    @Delete("delete from qna where num=#{num}")
    int deleteData(String num);

    @Select("select max(num) from qna")
    int currentNum();

    @Select("select count(*) from qna")
    int totalCnt();

    @Update("update qna set onum=onum + 1 where onum >= #{onum} and gnum=#{gnum}")
    int updateOnum(QnaBean bean);

    @Insert("insert into qna (id, title, cont, bdate, gnum,onum,nested,name)\r\n"
    		+ " values (#{id}, #{title}, #{cont}, #{bdate}, #{gnum},#{onum},#{nested},#{name})")
    int insertReData(QnaBean bean);

}
