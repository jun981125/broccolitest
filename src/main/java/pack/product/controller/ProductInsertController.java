package pack.product.controller;

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
import pack.product.model.ProductDao;

@Controller
public class ProductInsertController {
   @Autowired
   private ProductDao productDao;

   @GetMapping("productinsert") 
   public String insertform() {
      return "product/productinsert";
   }

   @PostMapping("productinsert")
   public String insertProcess(ProductBean bean, Model model, BindingResult result, HttpSession session) throws Exception{

	   // 세션을 통해 판매자 닉네임	
	   String customerid = (String) session.getAttribute("loginid");
	   bean.setCustomerid(customerid);
	   
      InputStream inputStream1 = null;
      InputStream inputStream2 = null;
      OutputStream outputStream1 = null;
      OutputStream outputStream2 = null;
      
      // 이름중복을 방지하기 위해 난수 발생시킴
       String uuid1=UUID.randomUUID().toString(); 
       String uuid2=UUID.randomUUID().toString(); 
        MultipartFile file1 = bean.getPimagePath();
        MultipartFile file2 = bean.getDimagePath();
        String filename1 = uuid1+file1.getOriginalFilename();
      String filename2 = uuid2+file2.getOriginalFilename();
      if (result.hasErrors()) {
         return "err"; 
      }
      
      try {
         inputStream1 = file1.getInputStream();
         inputStream2 = file2.getInputStream();
         File newFile1 = new File("/Users/heojunho/work/shop/shop/broccoli/src/main/resources/static/upload/" + filename1); // 절대경로로 찍기
         File newFile2 = new File("/Users/heojunho/work/shop/shop/broccoli/src/main/resources/static/upload/" + filename2);
         if(!newFile1.exists()) {
            newFile1.createNewFile();
         }
         if(!newFile2.exists()) {
            newFile2.createNewFile();
         }
         outputStream1 = new FileOutputStream(newFile1);
         outputStream2 = new FileOutputStream(newFile2);
         int read = 0;
         byte[] bytes = new byte[1024];  // -1 끝을 의미
         while((read = inputStream1.read(bytes))!=-1) {
            outputStream1.write(bytes,0,read);
         }
         while((read = inputStream2.read(bytes))!=-1) {
            outputStream2.write(bytes,0,read);
         }
         
      } catch (Exception e) {
         System.out.println("file submit err : "+e);
      } finally {
         try {
            inputStream1.close();
            inputStream2.close();
            outputStream1.close();
            outputStream2.close();
         } catch (Exception e2) {
            // TODO: handle exception
         }
      }
      
      bean.setPimage(filename1);
      bean.setDimage(filename2);
      
        boolean b =  productDao.insert(bean);;
           if(b) {
              return "redirect:productlist_seller";
           } else {
              return "redirect:productinsert";
           }
   }
}