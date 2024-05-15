package org.mt.mms.estimate.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstimateExVO extends EstimateVO{

    private String topContrNm;      // 원계약명
    private String compNm;          // 업체명

}
