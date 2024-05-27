package org.mt.mms.company.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mt.mms.company.mapper.CompanyMapper;
import org.mt.mms.company.service.CompanyService;
import org.mt.mms.company.vo.CompanyManagerVO;
import org.mt.mms.company.vo.CompanyVO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyMapper companyMapper;

    @Override
    public CompanyVO one(String companyId) throws Exception {

        CompanyVO cv = companyMapper.selectCompany(companyId);
        List<CompanyManagerVO> companyManagers = companyMapper.selectCompanyManagers(companyId);
        cv.setCompanyManagers(companyManagers);

        return cv;
    }

    @Override
    public List<CompanyVO> all(HashMap params) throws Exception {
        return companyMapper.selectCompanys(params);
    }

    @Override
    public int newCompany(CompanyVO companyVO) throws Exception {
        if(companyVO.getCompId() == null || companyVO.getCompId().isEmpty()){
            return companyMapper.insertCompany(companyVO);
        } else {
            return companyMapper.updateCompany(companyVO);
        }

    }

    @Override
    public int deleteCompany(String id) throws Exception {
        /* a. company_manager 조회 후 삭제 */
        List<CompanyManagerVO> cmList = companyMapper.selectCompanyManagers(id);
        if(!cmList.isEmpty()) { // a-1.company_manager 존재시 company_id 로 전체삭제처리
            if (companyMapper.deleteCompanyManagerAll(id) < 0) {
                throw new Exception("저장실패");
            }
        }

        /* b. company 삭제 */
        return companyMapper.deleteCompany(id);
    }

    @Override
    public int newCompanyManager(CompanyManagerVO companyManagerVO) throws Exception {
        if(companyManagerVO.getCompMngerId() == null) {
            return companyMapper.insertCompanyManager(companyManagerVO);
        } else {
            return companyMapper.updateCompanyManager(companyManagerVO);
        }
    }

    @Override
    public int deleteCompanyManager(String id) throws Exception {
        return companyMapper.deleteCompanyManager(id);
    }

}
