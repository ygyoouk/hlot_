package org.mt.mms.estimate.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.mt.mms.cmm.service.AttachmentService;
import org.mt.mms.cmm.service.CommonService;
import org.mt.mms.cmm.vo.AttachmentVO;
import org.mt.mms.estimate.mapper.EstimateMapper;
import org.mt.mms.estimate.service.EstimateService;
import org.mt.mms.estimate.vo.EstimateExVO;
import org.mt.mms.estimate.vo.EstimateVO;
import org.mt.mms.prod.service.ProdService;
import org.mt.mms.prod.vo.ProdVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateServiceImpl implements EstimateService {

    private final EstimateMapper estimateMapper;

    private final CommonService commonService;

    private final AttachmentService attachmentService;

    private final ProdService prodService;


    @Override
    public List<EstimateExVO> all() throws Exception {
        return estimateMapper.selectEstimates();
    }

    @Override
    public EstimateExVO one(String id) throws Exception {
        EstimateExVO vo = estimateMapper.selectEstimate(id);
        AttachmentVO file = attachmentService.selectAttachmentInfo(vo.getEstimateFileId());

        vo.setAttachmentVO(file);

        return vo;
    }

    @Override
    public int newEstimate(EstimateVO estimateVO, MultipartFile file) throws Exception {
        if(file!=null)
            estimateVO.setEstimateFileId(attachmentService.upload(file).getFileId());

        String estimateId = commonService.selectHlotSeq("02", "A", "EST");
        estimateVO.setEstimateId(estimateId);

        int result = estimateMapper.insertEstimate(estimateVO);

        for(ProdVO p : estimateVO.getProds()){
            p.setEstimateId(estimateId);
            p.setRegistUserName(estimateVO.getRegistUserName());
            prodService.newProd(p);
        }

        return result;
    }
}
