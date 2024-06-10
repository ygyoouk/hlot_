package org.mt.mms.prod.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProdVO {

    private String estimateId;
    private String orderNo;
    private String prodNm;
    private String compNm;
    private String detailProdNm;
    private String clientUnitPrice;
    private String provProdPrice;
    private String dcPer;
    private String quantity;
    private String registUserName;
    private String registDate;
    private String delYn;

}
