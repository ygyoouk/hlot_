package org.mt.mms.prod.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mt.mms.cmm.dto.Result;
import org.mt.mms.prod.service.ProdService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ProdController {

    private final ProdService prodService;

    @GetMapping("/prods/{id}")
    public ResponseEntity<Result> all(@PathVariable String id) throws Exception {
        return ResponseEntity.ok()
                .body(Result.resSuccess(prodService.all(id)));
    }


}
