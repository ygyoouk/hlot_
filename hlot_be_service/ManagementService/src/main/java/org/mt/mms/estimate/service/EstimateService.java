package org.mt.mms.estimate.service;

import org.mt.mms.estimate.vo.EstimateExVO;
import org.mt.mms.estimate.vo.EstimateVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface EstimateService {

    List<EstimateExVO> all() throws Exception;

    EstimateExVO one(String id) throws Exception;

    int newEstimate(EstimateVO estimateVO, MultipartFile file) throws Exception;

    boolean possibleConfirm(String topContrId, String compId, String estimateDiv) throws Exception;

    int confirmEstimate(String id) throws Exception;
}
