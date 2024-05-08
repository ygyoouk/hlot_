package org.mt.mms.cmm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.mt.mms.cmm.vo.AttachmentVO;

import java.util.HashMap;

@Mapper
public interface CommonMapper {

    void insertFile(HashMap<String,String> paramMap) throws Exception;

    AttachmentVO selectAttachmentInfo(String fileId) throws Exception;
}
