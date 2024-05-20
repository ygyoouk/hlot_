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

    private boolean possibleConfirm; // 확정가능 여부
    private boolean possibleConstr; // 계약가능 여부

}
