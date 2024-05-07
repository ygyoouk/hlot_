package org.mt.mms.topContr.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mt.mms.topContr.mapper.TopContrMapper;
import org.mt.mms.topContr.service.TopContrService;
import org.mt.mms.topContr.vo.TopContrVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class TopContrServiceImpl implements TopContrService {

    private  final TopContrMapper topContrMapper;
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
            File uploadFilePath = new File("C:" + File.separator + "fileUploadTest");

            // 1-2 파일을 해당 경로에 업로드
            file.transferTo(uploadFilePath);
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
