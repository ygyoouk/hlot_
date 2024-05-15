package org.mt.mms.estimate.service;

import org.mt.mms.estimate.vo.EstimateExVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EstimateService {


    List<EstimateExVO> all() throws Exception;
}
