package org.mt.mms.company.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyVO {

    private String compId;          // 업체ID
    private String compName;        // 업체명
    private String compTel;         // 업체번호
    private String compCeoNm;       // 업체대표명
    private String compAddr;        // 업체주소
    private String registUserName;  // 등록자
    private String registDate;      // 등록일자

    private List<CompanyManagerVO> companyManagers;
}
