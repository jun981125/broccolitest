package pack.noticeboard.model;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.noticeboard.controller.AnmtBean;




@Repository
public class AnmtDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AnmtDataMapper dataMapper;
	
	public List<AnmtDto> selectAll(){ // 공지 글 불러오기
		List<AnmtDto> list = dataMapper.selectAll();
		logger.info("datas: " + list.size()+"개");
		return list;
	}
	
	public List<AnmtDto> Aselect(){ // 공지 2개만 불러서 커뮤니티 상위 노출
		List<AnmtDto> alist = dataMapper.Aselect();
		return alist;
	}

	public List<AnmtDto> search(AnmtBean bean) {
		List<AnmtDto> list = dataMapper.selectSearch(bean); 
		return list;
	}

	
	public boolean anmtinsert(AnmtBean bean) {  // 공지 글 작성
		boolean b = false;
		int re = dataMapper.insertAnmt(bean);
		if (re > 0)
			b = true;
		return b;
	}
	
	
	public AnmtDto detail(String num) { // 상세 보기
		AnmtDto dto = dataMapper.selectOne(num);
		return dto;
	}
	
	public int totalCnt() {
		return dataMapper.totalCnt();
	}
	
	
	public boolean update(AnmtBean bean) {
		boolean b = false;
		int re = dataMapper.updateData(bean);
		if (re > 0)
			b = true;
		return b;
	}
	
	public boolean delete(String num) {
		boolean b = false;
		int re = dataMapper.deleteData(num);
		if (re > 0)
			b = true;
		return b;
	}
	
}
