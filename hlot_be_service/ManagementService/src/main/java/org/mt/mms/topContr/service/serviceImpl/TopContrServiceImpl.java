package org.mt.mms.topContr.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mt.mms.cmm.mapper.CommonMapper;
import org.mt.mms.topContr.mapper.TopContrMapper;
import org.mt.mms.topContr.service.TopContrService;
import org.mt.mms.topContr.vo.TopContrVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class TopContrServiceImpl implements TopContrService {

    private final TopContrMapper topContrMapper;
    private final CommonMapper commonMapper;

    @Value("${hlot.file.upload.path}")
    private String filePath;

    @Override
    public List<TopContrVO> all(TopContrVO searchParam) throws Exception{

        List<TopContrVO> result = null;

        try{
            result = topContrMapper.getTopContrs(searchParam);
            log.info(result.toString());
        }catch(Exception e){
            log.info(e.getMessage());
        }
        return result;
    }

    @Override
    public int newTopContr(TopContrVO data, MultipartFile file) throws Exception{
        String topContrFileId = null;

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

                topContrFileId = uploadFileName;
                log.info("paramMap : {}", paramMap);

                commonMapper.insertFile(paramMap);
            }
        }

        // 프로젝트ID가 없는 경우(신규)
        data.setTopContrFileId(topContrFileId);

        log.info("data : {}", data);

        return topContrMapper.newTopContr(data);
    }

    @Override
    public TopContrVO one(String topContrId) throws Exception{
        log.info("serviceImpl one");
        TopContrVO result = null;

        try{
            result = topContrMapper.one(topContrId);

            log.info("info : {}",result);
        }catch (Exception e){
            log.info(e.getMessage());
        }

        return result;
    }

    @Override
    public int updateTopContr(TopContrVO data, MultipartFile file) throws Exception{
        String topContrFileId = null;

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

                topContrFileId = uploadFileName;
                log.info("paramMap : {}", paramMap);

                commonMapper.insertFile(paramMap);
            }
        }
        data.setTopContrFileId(topContrFileId);

        log.info("s-data : {}", data);

        return topContrMapper.updateTopContr(data);
    }

    @Override
    public int deleteTopContr(ArrayList<String> deldata) throws Exception{

       return  topContrMapper.deleteTopContr(deldata);

    }
}
