package org.mt.mms.estimate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mt.mms.cmm.dto.Result;
import org.mt.mms.estimate.service.EstimateService;
import org.mt.mms.estimate.vo.EstimateExVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class EstimateController {

    private final EstimateService estimateService;

    /* company 목록 조회 */
    @GetMapping("/estimates")
    public ResponseEntity<Result> all() throws Exception{
        return ResponseEntity.ok()
                .body(Result.resSuccess(estimateService.all()));
    }
}
