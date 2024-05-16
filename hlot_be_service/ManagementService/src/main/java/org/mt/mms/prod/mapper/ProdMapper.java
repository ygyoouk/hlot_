package org.mt.mms.prod.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.mt.mms.prod.vo.ProdVO;

import java.util.List;

@Mapper
public interface ProdMapper {

    List<ProdVO> selectProds(String id) throws Exception;
}
