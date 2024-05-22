package org.mt.mms.company.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompanyVO {

    private String compId;          // 업체ID
    private String compNm;        // 업체명
    private String compBussRegnum; // 사업자등록번호
    private String compDiv;         // 업체구분
    private String compDivNm;       // 업체구분명
    private String compTel;         // 업체번호
    private String compCeoNm;       // 업체대표명
    private String compAddr;        // 업체주소
    private String registUserName;  // 등록자
    private String registDate;      // 등록일자

    private List<CompanyManagerVO> companyManagers;
}
