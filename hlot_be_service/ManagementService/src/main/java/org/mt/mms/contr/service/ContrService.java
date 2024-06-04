package org.mt.mms.contr.service;

import org.mt.mms.contr.vo.ContrVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Service
public interface ContrService {

    List<ContrVO> all(HashMap<String, String> params) throws Exception;

    ContrVO one(String contrId);

    int newContr(ContrVO data)throws Exception;

    int updateContr(ContrVO data) throws Exception;
}
