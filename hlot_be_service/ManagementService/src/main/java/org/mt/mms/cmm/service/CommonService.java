package org.mt.mms.cmm.service;

import org.mt.mms.cmm.vo.CmmCodeVO;
import org.mt.mms.company.vo.CompanyVO;
import org.mt.mms.topContr.vo.TopContrVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommonService {

    List<TopContrVO> topContrNms() throws Exception;

    List<CompanyVO> compNms() throws Exception;

    String selectHlotSeq(String b, String table) throws Exception;

    List<CmmCodeVO> getCode(String codeNm) throws Exception;

    TopContrVO topContrNm(String topContrId);

    CompanyVO compNm(String compId);
}
