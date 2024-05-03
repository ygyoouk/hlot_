package org.mt.mms.company.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyManagerVO {

    private String compMngerId;    // 업체 담당자 ID
    private String compId;           // 업체 ID
    private String compMngerName;  // 업체담당자이름
    private String compMngerTel;   // 업체담당자번호
    private String registUserName;      // 등록자
    private String registDate;          // 등록일자

}
