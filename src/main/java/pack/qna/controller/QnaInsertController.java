package pack.qna.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import pack.noticeboard.controller.UploadFile;
import pack.qna.model.QnaDaoImpl;

@Controller
public class QnaInsertController {
	@Autowired
	private QnaDaoImpl daoImpl;

	@GetMapping("qinsert")
	public String insertform(HttpSession session, Model model) {
		String nickname = (String) session.getAttribute("nickname");
		if (nickname != null) {
			model.addAttribute("nickname", nickname);
			return "qna/qinsform";
		} else {
			return "redirect:/qlist?page=1";
		}
	}

	@PostMapping("qinsert")
	public String insertProcess(QnaBean bean,UploadFile uploadfile, BindingResult result, HttpSession session) {
		// 업로드 파일 초기화
		InputStream inputStream = null;
		OutputStream outputStream = null;
	
		// 업로드될 파일 검사
		MultipartFile file = uploadfile.getFile();


		if (result.hasErrors()) {
			return "err"; // 에러 발생 (파일을 선택하지 않은 경우)시 수행
		}

		if (file != null && !file.isEmpty()) {
			String uuid = UUID.randomUUID().toString();
			String filename = uuid + file.getOriginalFilename();

			try {
				inputStream = file.getInputStream();
				File newFile = new File(
						"/Users/heojunho/work/shop/shop/broccoli/src/main/resources/static/upload/"
								+ filename); // 절대경로로 찍기
				if (!newFile.exists()) {
					newFile.createNewFile();
				}
				outputStream = new FileOutputStream(newFile);
				int read = 0;
				byte[] bytes = new byte[1024];
				// -1 끝을 의미
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read); // 업로드된 파일의 데이터가 읽혀서 새로운 파일(newFile)에 저장되는 과정
				}
				bean.setFilename(filename); // 파일명 bean에 저장
			} catch (Exception e) {
				System.out.println("file submit err : " + e);
				return "err";
			} finally {
				try {
					inputStream.close();
					outputStream.close();
				} catch (Exception e2) {
				}
			}
			// 파일 업로드 과정 끝
		}
		
		String id = (String) session.getAttribute("loginId");
		String nickname = (String) session.getAttribute("nickname");
		bean.setId(id);
		bean.setName(nickname);
		bean.setBdate();
		int newNum = daoImpl.currentNum() + 1; // 새글 번호
		bean.setNum(newNum);
		bean.setGnum(newNum);

		boolean b = daoImpl.insert(bean);
		if (b) {
			return "redirect:/qlist?page=1"; // 추가 후 목록 보기
		} else {
			return "redirect:/error";
		}
	}
}
