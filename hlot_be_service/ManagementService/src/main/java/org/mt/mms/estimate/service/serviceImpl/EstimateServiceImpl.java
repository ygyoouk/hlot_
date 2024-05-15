package org.mt.mms.estimate.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.mt.mms.estimate.mapper.EstimateMapper;
import org.mt.mms.estimate.service.EstimateService;
import org.mt.mms.estimate.vo.EstimateExVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateServiceImpl implements EstimateService {

    private final EstimateMapper estimateMapper;

    @Override
    public List<EstimateExVO> all() throws Exception {
        return estimateMapper.selectEstimates();
    }
}
