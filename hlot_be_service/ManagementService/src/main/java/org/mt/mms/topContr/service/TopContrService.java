package org.mt.mms.topContr.service;

import org.mt.mms.topContr.vo.TopContrVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface TopContrService {
    // 프로젝트 조회
    List<TopContrVO> all() throws Exception;
    
    // 프로젝트 등록
    int newTopContr(TopContrVO data) throws Exception;
    
    // 프로젝트 상세조회
    TopContrVO one(String projectId) throws Exception;

    int updateTopContr(TopContrVO data) throws Exception;

    int deleteTopContr(ArrayList<String> deldata) throws Exception;
}
