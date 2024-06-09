package org.mt.mms.topContr.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor // 모든 필드값을 파라미터로 받는 생성자 생성
@NoArgsConstructor // 파라미터 없는 디폴트 생성자 생성
@Data
public class TopContrVO {

    private String topContrId;
    private String topContrNm;
    private String topContrDiv;
    private String clientComp;
    private String clientCompId;
    private String topContrDate;
    private String topContrStDate;
    private String topContrEndDate;
    private String contrStDate;
    private String contrEndDate;
    private String prodNm;
    private String contrAmount;
    private String totalServBokAmount; // 총용역부기금액
    private String amount1;
    private String amount2;
    private String amount3;
    private String amount4;
    private String amount5;
    private String deliveryDeadline;
    private String demandInst;
    private String demandInstId;
    private String topContrFileId;
    private String fileId;
    private String filePath;
    private String orignFileName;
    private String changeFileName;
    private String registUserName;  // 등록자
    private String registDate;
}
