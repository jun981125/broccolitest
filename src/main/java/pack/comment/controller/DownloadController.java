package pack.comment.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class DownloadController {
	@PostMapping("download")
	@ResponseBody
	public byte[] downProcess(HttpServletResponse response, @RequestParam String filename) throws Exception{
		System.out.println("filename : " + filename);
	    File newFile = new File("C:/work/sprsou/sprweb16fileupload/src/main/resources/static/upload/" + filename); // 절대경로로 찍기
		
		byte[] bytes = FileCopyUtils.copyToByteArray(newFile);
		String fn = new String(newFile.getName().getBytes(), "iso_8859_1");
		
		response.setHeader("Content-Disposition", "attachment;filename=\"" + fn + "\"");
		response.setContentLength(bytes.length);
		
		return bytes;
		}
}
