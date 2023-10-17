package pack.comment.model;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.comment.controller.CommuBean;

@Mapper
public interface CommuDataMapper {
@Select("select * from commu order by num desc")
List<CommuDto>selectAll();

@Select("select max(num) from commu")
int currentNum();

@Select("select * from commu\r\n"
      + "      where ${searchName} like concat('%',#{searchValue},'%') order by num desc")
List<CommuDto>selectSearch(CommuBean bean);

@Select("select * from commu where num=#{num}")
CommuDto selectOne(String num);

@Select("select count(*) from commu")
int totalCnt();

@Select("select count(*) from commu\r\n"
      + "      where ${searchName} like concat('%',#{searchValue},'%') order by num desc")
int searchCnt(@Param("searchName") String searchName, @Param("searchValue") String searchValue);

@Insert("insert into commu (customerid, customernickname, title, cont, filename, cdate) VALUES (#{customerid}, #{customernickname}, #{title}, #{cont}, #{filename}, #{cdate})")
int insertCommu(CommuBean bean);

@Update("update commu set readcnt=readcnt + 1 where num=#{num}")
int updateReadcnt(String num);

@Update("update commu set customerid=#{customerid},title=#{title},cont=#{cont},filename=#{filename} where num=#{num}")
int updateData(CommuBean bean);

@Delete("delete from commu where num=#{num}")
int deleteData(String num);

}