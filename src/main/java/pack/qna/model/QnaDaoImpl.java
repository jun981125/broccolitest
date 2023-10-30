package pack.qna.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.qna.controller.QnaBean;

@Repository
public class QnaDaoImpl {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private QnaDataMappInter dataInterface;

	public List<QnaDto> listAll() {
		List<QnaDto> list = dataInterface.selectList();
		logger.info("반환된 레코드 수 : " + list.size());
		return list;
	}

	public List<QnaDto> search(QnaBean bean) {
		List<QnaDto> list = dataInterface.searchList(bean);
		logger.info("검색 반환된 레코드 수 : " + list.size());
		return list;
	}

	public int totalCnt() {
		return dataInterface.totalCnt();
	}

	public boolean insert(QnaBean bean) {
		boolean b = false;
		int re = dataInterface.insertData(bean);
		if (re > 0)
			b = true;
		return b;
	}

	public int currentNum() {
		// 추가 시 num 자동증가를 위해 현재 레코드 중 가장 큰 번호 얻기
		return dataInterface.currentNum();
	}

	public void updateReadcnt(String num) {
		// 상세보기 전에 조회 수 증가
		dataInterface.updateReadcnt(num);
	}

	public QnaDto detail(String num) { // 상세 보기
		// 글 내용 보기, 글 수정, 댓글 용
		QnaDto dto = dataInterface.selectOne(num);
		return dto;
	}

	public boolean update(QnaBean bean) {
		boolean b = false;
		int re = dataInterface.updateData(bean);
		if (re > 0)
			b = true;
		return b;
	}

	public boolean delete(String num) {
		boolean b = false;
		int re = dataInterface.deleteData(num);
		if (re > 0)
			b = true;
		return b;
	}

	// 답글
	public boolean updateOnum(QnaBean bean) {
		// 답글에서 onum 갱신
		boolean b = false;
		int re = dataInterface.updateOnum(bean);
		if (re > 0)
			b = true;
		return b;
	}

	public boolean insertReply(QnaBean bean) {
		boolean b = false;
		int re = dataInterface.insertReData(bean);
		if (re > 0)
			b = true;
		return b;
	}

	public int searchCnt(String searchName, String searchValue) {
		return dataInterface.searchCnt(searchName, searchValue);
	}

}
