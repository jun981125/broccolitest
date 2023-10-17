package pack.comment.model;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.comment.controller.CommuBean;

@Repository
public class CommuDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CommuDataMapper dataMapper;
	
	public List<CommuDto> selectAll(){ // 커뮤니티 글 불러오기
		List<CommuDto> list = dataMapper.selectAll();
		logger.info("datas: " + list.size()+"개");
		return list;
	}

	public List<CommuDto> search(CommuBean bean) {
		List<CommuDto> list = dataMapper.selectSearch(bean);
		return list;
	}
	
	public int currentNum() {
		// 추가 시 num 자동증가를 위해 현재 레코드 중 가장 큰 번호 얻기
		return dataMapper.currentNum();
	}
	
	public void updateReadcnt(String num) {
		// 상세보기 전에 조회 수 증가
		dataMapper.updateReadcnt(num);
	}
	
	public boolean comuinsert(CommuBean bean) {  // 커뮤 일반 글 작성
		boolean b = false;
		int re = dataMapper.insertCommu(bean);
		if (re > 0)
			b = true;
		return b;
	}
	
	public CommuDto detail(String num) { // 상세 보기
		// 글 내용 보기 + 글 수정 + 댓글 용
		CommuDto dto = dataMapper.selectOne(num);
		return dto;
	}
	
	public int totalCnt() {
		return dataMapper.totalCnt();
	}
	
	public int searchCnt(String searchName, String searchValue) {
	    return dataMapper.searchCnt(searchName, searchValue);
	}
	
	public boolean update(CommuBean bean) {
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
