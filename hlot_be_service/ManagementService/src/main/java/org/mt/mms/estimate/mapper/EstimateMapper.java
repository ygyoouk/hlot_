package org.mt.mms.estimate.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.mt.mms.estimate.vo.EstimateExVO;
import org.mt.mms.estimate.vo.EstimateVO;

import java.util.List;

@Mapper
public interface EstimateMapper {

    String selectEstimateId() throws Exception;

    List<EstimateExVO> selectEstimates() throws Exception;

    EstimateExVO selectEstimate(String id) throws Exception;

    int insertEstimate(EstimateVO estimateVO) throws Exception;


}
