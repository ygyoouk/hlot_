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

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateServiceImpl implements EstimateService {

    private final EstimateMapper estimateMapper;

    private final CommonService commonService;

    private final AttachmentService attachmentService;

    private final ProdService prodService;


    @Override
    public List<EstimateExVO> all(HashMap params) throws Exception {
        return estimateMapper.selectEstimates(params);
    }

    @Override
    public EstimateExVO one(String id) throws Exception {
        EstimateExVO vo = estimateMapper.selectEstimate(id);
        AttachmentVO file = attachmentService.selectAttachmentInfo(vo.getEstimateFileId());

        vo.setAttachmentVO(file);

        boolean isPossible = possibleConfirm(vo.getTopContrId(), vo.getCompId(), vo.getEstimateDiv());
        vo.setPossibleConfirm(isPossible);

        return vo;
    }

    @Override
    public int newEstimate(EstimateVO estimateVO, MultipartFile file) throws Exception {
        if(file!=null)
            estimateVO.setEstimateFileId(attachmentService.upload(file).getFileId());

        String estimateId = commonService.selectHlotSeq("A", "EST");
        estimateVO.setEstimateId(estimateId);

        int result = estimateMapper.insertEstimate(estimateVO);

        for(ProdVO p : estimateVO.getProds()){
            p.setEstimateId(estimateId);
            p.setRegistUserName(estimateVO.getRegistUserName());
            prodService.newProd(p);
        }

        return result;
    }

    /**
     * 같은 원계약과 업체, 견적구분을 가진 견적서들을 조회하여 확정가능한 상태인지 조회한다.
     * 조회결과가 없으면 확정가능상태로 본다.
     */
    @Override
    public boolean possibleConfirm(String topContrId, String compId, String estimateDiv) throws Exception {

        HashMap<String, String> param = new HashMap<>();
        param.put("topContrId", topContrId);
        param.put("compId", compId);
        param.put("estimateDiv", estimateDiv);
        int result = estimateMapper.selectPossibleConfirm(param);

        return result < 1;
    }

    @Override
    public int confirmEstimate(String id) throws Exception {
        return estimateMapper.updateConfirmEstimate(id);
    }

    @Override
    public int deleteEstimate(String id) throws Exception {
        return estimateMapper.deleteEstimate(id);
    }

    @Override
    public int contrEstimate(String id) throws Exception {
        return estimateMapper.updateContrEstimate(id);
    }



}
