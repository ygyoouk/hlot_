package org.mt.mms.topContr.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data

public class TopContrVO {

    private String topContrId;
    private String topContrNm;
    private String topContrDiv;
    private String clientComp;
    private String topContrDate;
    private String topContrStDate;
    private String topContrEndDate;
    private String contrStDate;
    private String contrEndDate;
    private String prodNm;
    private String contrAmount;
    private String deliveryDeadline;
    private String demandInstNm;
    private String topContrFileId;
    private String fileId;
    private String filePath;
    private String orignFileName;
    private String changeFileName;

}
