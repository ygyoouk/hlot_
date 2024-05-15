package org.mt.mms.estimate.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.mt.mms.estimate.vo.EstimateExVO;

import java.util.List;

@Mapper
public interface EstimateMapper {

    List<EstimateExVO> selectEstimates() throws Exception;
}
