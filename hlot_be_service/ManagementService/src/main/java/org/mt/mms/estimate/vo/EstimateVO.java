package org.mt.mms.estimate.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mt.mms.cmm.vo.AttachmentVO;
import org.mt.mms.prod.vo.ProdVO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstimateVO {

    private String estimateId;          // 견적서ID
    private String topContrId;          // 원계약ID
    private String compId;              // 업체ID
    private int orderNo;                // 견적 순번
    private String estimateDiv;         // 견적구분
    private String estimateDivNm;       // 견적구분명
    private String confirmYn;           // 확정여부
    private String contrYn;             // 계약여부
    private String estimateFileId;      // 견적서첨부파일ID
    private String registDate;          // 등록일자
    private String delYn;               // 삭제여부
    private String registUserName;      // 등록자명

    private List<ProdVO> prods; // 품목목록
    private AttachmentVO attachmentVO; //첨부파일관련

}
