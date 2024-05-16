package org.mt.mms.cmm.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.mt.mms.cmm.mapper.CommonMapper;
import org.mt.mms.cmm.service.CommonService;
import org.mt.mms.company.vo.CompanyVO;
import org.mt.mms.topContr.vo.TopContrVO;
import org.springframework.stereotype.Service;

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
}
