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
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class TopContrServiceImpl implements TopContrService {

    private final TopContrMapper topContrMapper;
    private final CommonMapper commonMapper;

    @Value("${hlot.file.upload.path}")
    String filePath;

    @Override
    public List<TopContrVO> all() throws Exception{

        List<TopContrVO> result = null;

        try{
            result = topContrMapper.getTopContrs();
            log.info(result.toString());
        }catch(Exception e){
            log.info(e.getMessage());
        }
        return result;
    }

    @Override
    public int newTopContr(TopContrVO data, MultipartFile file) throws Exception{

        if(file != null){
            log.info("file : {}", file.getOriginalFilename());

            //1. 서버에 이미지파일을 저장, 이미지를 서버에 업로드
            // 1-1. 파일 저장 위치를 지정하여 파일객체를 포장
            String originFileName = file.getOriginalFilename();

            // 1-1-a 파일명이 중복되지않도록 변경
            String uploadFileName = UUID.randomUUID() + "_" + originFileName;
            // 1-1-b 폴더 위치
            File saveFilePath = new File(filePath + File.separator + file.getOriginalFilename()); // 파일이 저장될 위치
            File uploadPath = new File(filePath); // 파일 폴더가 없을 경우 대비

            if(!uploadPath.mkdirs()){
                log.info("uploadpath : {}" ,  uploadPath);
                // 1-2 파일을 해당 경로에 업로드
                file.transferTo(saveFilePath);

                HashMap<String, Object> paramMap = new HashMap<>();
                paramMap.put("fileId",uploadFileName);
                paramMap.put("filePath",uploadPath);
                paramMap.put("orignFileName",originFileName);
                paramMap.put("changeFileName",uploadFileName);
                paramMap.put("registUserName","tester");
                paramMap.put("fileSize",file.getSize());

                data.setTopContrId(uploadFileName);

                log.info("paramMap : {}", paramMap);

                return commonMapper.insertFile(paramMap);

            }

        }

        // 프로젝트ID가 없는 경우(신규)

        return topContrMapper.newTopContr(data);

    }

    @Override
    public TopContrVO one(String projectId) throws Exception{
        log.info("serviceImpl one");
        TopContrVO result = null;

        try{
            result = topContrMapper.one(projectId);
            log.info("info{}",result);
        }catch (Exception e){
            log.info(e.getMessage());
        }

        return result;
    }

    @Override
    public int updateTopContr(TopContrVO data, MultipartFile file) throws Exception{

        return topContrMapper.updateTopContr(data);
    }

    @Override
    public int deleteTopContr(ArrayList<String> deldata) throws Exception{

       return  topContrMapper.deleteTopContr(deldata);

    }
}
