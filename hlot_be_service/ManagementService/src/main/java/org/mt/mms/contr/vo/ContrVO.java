package org.mt.mms.contr.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data // getter, setter , toString ...
@AllArgsConstructor //모든 필드 값을 파라미터로 받는 생성자를 생성
@NoArgsConstructor // 파라미터가 없는 디폴트 생성자를 생성
public class ContrVO {

    private String topContrId; // 원계약 ID
    private String topContrNm; // 원계약명
    private String contrId; // 계약 ID
    private String estimateId; //견적서ID
    private String contrNm; // 계약명
    private String compId; // 업체ID
    private String compName; // 업체명
    private String contrAmount; //계약금액
    private String paymentTerm; // 지불조건
    private String specialNote; // 특이사항
    private String contrStDate; // 계약시작일자
    private String contrEndDate; // 계약종료일자
    private String contrFileId; // 계약첨부파일ID
}
