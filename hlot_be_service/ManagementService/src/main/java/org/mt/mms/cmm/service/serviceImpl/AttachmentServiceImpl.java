package org.mt.mms.cmm.service.serviceImpl;

import ch.qos.logback.core.net.SyslogOutputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.transaction.TransactionException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.text.PDFTextStripper;
import org.mt.mms.cmm.mapper.CommonMapper;
import org.mt.mms.cmm.service.AttachmentService;
import org.mt.mms.cmm.vo.AttachmentVO;
import org.mt.mms.contr.vo.ContrVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@RequiredArgsConstructor
@Service
public class AttachmentServiceImpl implements AttachmentService{

    private final CommonMapper commonMapper;
    @Value("${hlot.file.upload.path}")
    private String filePath;
    @Override
    public AttachmentVO selectAttachmentInfo(String fileId) throws Exception {

        return commonMapper.selectAttachmentInfo(fileId);
    }


    @Override
    public AttachmentVO upload(MultipartFile file) throws Exception {
        String fileId = null;

        if(file != null){
            log.info("file : {}", file.getOriginalFilename());

            //1. 서버에 이미지파일을 저장, 이미지를 서버에 업로드
            // 1-1. 파일 저장 위치를 지정하여 파일객체를 포장
            String originFileName = file.getOriginalFilename();

            // 1-1-a 파일명이 중복되지않도록 변경

            Date date = new Date(); // 날짜 생성
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); // 날짜 데이터를 원하는 형태로 다양하게 출력

            String uploadDate = sdf.format(date); // 업로드 년월일

            String uploadFileName = UUID.randomUUID() + "_" + uploadDate;
            // 1-1-b 폴더 위치
            File saveFilePath = new File(filePath + File.separator + uploadFileName); // 파일이 저장될 위치
            File uploadPath = new File(filePath); // 파일 폴더가 없을 경우 대비

            if(!uploadPath.mkdirs()){
                log.info("uploadpath : {}" ,  uploadPath);
                // 1-2 파일을 해당 경로에 업로드
                file.transferTo(saveFilePath);

                HashMap<String, String> paramMap = new HashMap<>();
                paramMap.put("fileId",uploadFileName);
                paramMap.put("filePath",uploadPath.getPath());
                paramMap.put("orignFileName",originFileName);
                paramMap.put("changeFileName",uploadFileName);
                paramMap.put("fileSize", String.valueOf(file.getSize()));
                paramMap.put("registUserName","tester");


                log.info("paramMap : {}", paramMap);
                fileId = uploadFileName;
                commonMapper.insertFile(paramMap);
            }
        }
        return selectAttachmentInfo(fileId);
    }

    @Override
    public ContrVO pdfUpload(MultipartFile file) {

        ContrVO data = new ContrVO();

        if(file != null){
            log.info("PDFFILE : {}", file.getOriginalFilename());

           try {
               // PDF 파일을 로드합니다.
               PDDocument document = Loader.loadPDF(file.getBytes());

               log.info("document : {}", document);

//               PDPage page = document.getPage(0);
//               for(COSName name : page.getResources().getFontNames()){
//                   PDFont font = page.getResources().getFont(name);
//                   String fontName = font.getName();
//               }


               // PDFTextStripper 클래스를 사용하여 텍스트를 추출합니다.
               PDFTextStripper pdfStripper = new PDFTextStripper();

               // UTF-8로 인코딩하여 텍스트를 추출합니다.
               pdfStripper.setAddMoreFormatting(true);
               pdfStripper.setStartPage(1); // 시작 페이지 설정
               pdfStripper.setEndPage(1); // 끝 페이지 설정
               String text = pdfStripper.getText(document);

               // 추출된 텍스트를 출력합니다.
               System.out.println("Extracted text:");
               byte[] tArr = text.getBytes(StandardCharsets.UTF_8);

               String pdfText = new String(tArr, StandardCharsets.UTF_8);


//               System.out.print(new String(tArr, StandardCharsets.UTF_8));

               // 가져온 문자열 자르기

                String contrNmPattern = "계 약 명([^\n]*)"; // 특정 문자열 다음에 개행문자전까지의 문자열 찾기

                Pattern p = Pattern.compile(contrNmPattern);
                Matcher m = p.matcher(pdfText);
                String contrNm = null;

                // 계약명 추출
                if (m.find()) {
                    String found = m.group(1);
                    contrNm = found;
                    System.out.println(found);
                }

               String contrDatePattern = "계약기간([^(\n]*)";
               p = Pattern.compile(contrDatePattern);
               m = p.matcher(pdfText);


               SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMd");
               SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");

               String contrDate;
               String contrStDate = null;
               String contrEndDate = null;

               if (m.find()) {
                   String found = m.group(1);
                   contrDate = found;

                   System.out.println(found);

                   String [] date = contrDate.split("~");

                   contrStDate = date[0].replaceAll("[^0-9]", "");
                   contrEndDate = date[1].replaceAll("[^0-9]", "");

                   // 날짜 형식이  yyyymd일 경우 yyyymmdd 형식으로 변환
                   if(contrStDate.length()<8 || contrEndDate.length()<8){
                       try{
                           Date date1 = dateFormat.parse(contrStDate);
                           Date date2 = dateFormat.parse(contrEndDate);

                           contrStDate = dateFormat2.format(date1);
                           contrEndDate = dateFormat2.format(date2);

                       }catch(Exception  e){
                           e.printStackTrace();
                       }
                   }

               }

               System.out.println("contrStDate ====>" + contrStDate);
               System.out.println("contrEndDate ====>" + contrEndDate);

               String contrAmountPattern = "계약금액([^\n]*)";
               p = Pattern.compile(contrAmountPattern);
               m = p.matcher(pdfText);
               String contrAmount = null;

               // 계약명 추출
               if (m.find()) {
                   String found = m.group(1);
                   contrAmount = found;
                   System.out.println(found);
               }

               String paymentTermPattern1 = "지불조건([^\n]*)";
               String paymentTermPattern2 = "지급방법([^\n]*)";

               p = Pattern.compile(paymentTermPattern1);
               m = p.matcher(pdfText);

               String paymentTerm = null;

               // 지불조건 추출
               if (m.find()) {
                   String found = m.group(1);
                   paymentTerm = found;
                   System.out.println(paymentTerm);
               }else{
                   p = Pattern.compile(paymentTermPattern2);
                   m = p.matcher(pdfText);

                   if(m.find()){
                       String found = m.group(1);
                       paymentTerm = found;
                       System.out.println(paymentTerm);
                   }
               }


               String specialNotePattern = "특이사항([^\n]*)";
               p = Pattern.compile(specialNotePattern);
               m = p.matcher(pdfText);
               String specialNote = null;

               // 계약명 추출
               if (m.find()) {
                   String found = m.group(1);
                   specialNote = found;
                   System.out.println(found);
               }




               data.setContrNm(contrNm);
               data.setContrAmount(contrAmount);
               data.setContrStDate(contrStDate);
               data.setContrEndDate(contrEndDate);
               data.setPaymentTerm(paymentTerm);
               data.setSpecialNote(specialNote);



               // PDF 문서를 닫습니다.
               document.close();

           }catch (IOException e){
               e.printStackTrace();
           }
        }


        return data;
    }


}
