package org.mt.mms.cmm.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface CommonMapper {

    int insertFile(HashMap<String,Object> paramMap) throws Exception;

}
