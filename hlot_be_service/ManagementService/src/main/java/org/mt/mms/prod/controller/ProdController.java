package org.mt.mms.prod.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mt.mms.cmm.dto.Result;
import org.mt.mms.prod.service.ProdService;
import org.mt.mms.prod.vo.ProdVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ProdController {

    private final ProdService prodService;

    @GetMapping(value={"/prods/{id}","/prods"})
    public ResponseEntity<Result> all(@PathVariable(required = false) String id ,@RequestParam HashMap<String,String> params) throws Exception {
        return ResponseEntity.ok()
                .body(Result.resSuccess(prodService.all(params)));
    }


}
