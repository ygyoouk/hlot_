package org.mt.mms.contr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.mt.mms.contr.vo.ContrVO;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ContrMapper {
    
    /**
     * 계약 전체 조회
     * */
    List<ContrVO> all(HashMap<String, String> params) throws Exception;

    ContrVO one(String contrId);

    int newContr(ContrVO data) throws Exception;

    int updateContr(ContrVO data) throws Exception;
}
