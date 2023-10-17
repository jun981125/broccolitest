package pack.noticeboard.model;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.noticeboard.controller.AnmtBean;



@Mapper
public interface AnmtDataMapper {
@Select("select * from anmt order by num desc")
List<AnmtDto>selectAll();

@Select("select * from anmt order by num desc limit 2")
List<AnmtDto>Aselect();

@Select("select max(num) from anmt")
int currentNum();

@Select("select * from anmt\r\n"
		+ "		where ${searchName} like concat('%',#{searchValue},'%') order by num desc")
List<AnmtDto>selectSearch(AnmtBean bean);

@Select("select * from anmt where num=#{num}")
AnmtDto selectOne(String num); 

@Select("select count(*) from anmt")
int totalCnt();

@Insert("insert into anmt (adminname, title, content, filename, cdate, eventnotice) VALUES (#{adminname}, #{title}, #{content}, #{filename},#{cdate},#{eventnotice})")
int insertAnmt(AnmtBean bean); 


@Update("update anmt set title=#{title},content=#{content},eventnotice=#{eventnotice},filename=#{filename} where num=#{num}")
int updateData(AnmtBean bean);

@Delete("delete from anmt where num=#{num}")
int deleteData(String num);

}
