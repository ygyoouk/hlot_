package org.mt.mms.cmm.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.mt.mms.cmm.mapper.CommonMapper;
import org.mt.mms.cmm.service.CommonService;
import org.mt.mms.cmm.vo.CmmCodeVO;
import org.mt.mms.company.vo.CompanyVO;
import org.mt.mms.topContr.vo.TopContrVO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    private final CommonMapper commonMapper;


    @Override
    public List<TopContrVO> topContrNms() throws Exception {
        return commonMapper.selectTopContrNms();
    }

    @Override
    public List<CompanyVO> compNms() throws Exception {
        return commonMapper.selectCompNms();
    }

    /**
     *
     * @param a 내부조직구분 01|02|03
     * @param b 사업유형
     * @param table 테이블구분
     * @return seq ID
     * @throws Exception
     */
    @Override
    public String selectHlotSeq(String a, String b, String table) throws Exception {
        HashMap<String, String> param = new HashMap<>();
        param.put("a", a);
        param.put("b", a);
        param.put("table", a);
        return commonMapper.selectHlotSeq(param);
    }

    @Override
    public List<CmmCodeVO> getCode(String codeNm) throws Exception {
        return commonMapper.selectCode(codeNm);
    }
}
