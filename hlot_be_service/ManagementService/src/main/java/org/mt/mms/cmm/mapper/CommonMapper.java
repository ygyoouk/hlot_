package org.mt.mms.cmm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.mt.mms.cmm.vo.AttachmentVO;
import org.mt.mms.company.vo.CompanyVO;
import org.mt.mms.topContr.vo.TopContrVO;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface CommonMapper {

    void insertFile(HashMap<String,String> paramMap) throws Exception;

    AttachmentVO selectAttachmentInfo(String fileId) throws Exception;

    List<TopContrVO> selectTopContrNms() throws Exception;

    List<CompanyVO> selectCompNms() throws Exception;

    String selectHlotSeq(HashMap<String, String> param) throws Exception;
}
