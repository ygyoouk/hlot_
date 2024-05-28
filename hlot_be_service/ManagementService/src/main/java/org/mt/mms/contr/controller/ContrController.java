package org.mt.mms.contr.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mt.mms.cmm.Util;
import org.mt.mms.cmm.dto.Result;
import org.mt.mms.cmm.service.AttachmentService;
import org.mt.mms.cmm.vo.AttachmentVO;
import org.mt.mms.contr.service.ContrService;
import org.mt.mms.contr.vo.ContrVO;
import org.mt.mms.topContr.vo.TopContrVO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
@Slf4j
@RequiredArgsConstructor // autowired
@RequestMapping("/api")
public class ContrController {

    private final ContrService contrService;
    private final AttachmentService attachmentService;
    private final Util util;
    /**
     * 계약 전체 조회
     * */
    @GetMapping("/contrs")
    public ResponseEntity<Result> all(@RequestParam HashMap<String,String> params) throws Exception{
        log.info("request : {}", params);

        return ResponseEntity.ok()
                .body(Result.resSuccess(contrService.all(params)));
    }

    @GetMapping("/contr/{contrId}")
    public ResponseEntity<Result> one(@PathVariable String contrId){
        log.info("CONTR_ID : {}", contrId);

        return ResponseEntity.ok()
                .body(Result.resSuccess(contrService.one(contrId)));
    }

    @PostMapping(value= "/newContr" ,consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Result> newContr(@RequestPart ContrVO data, @RequestPart(value = "file", required = false) MultipartFile file) throws Exception {

        log.info("data : {}", data);
        log.info("file : {}", file);

        AttachmentVO attachmentVO =  attachmentService.upload(file);

        String fileId = attachmentVO.getFileId();

        log.info("fileId : {}", fileId);

        data.setContrFileId(fileId);

        log.info("data_fileId : {}", data);

        return ResponseEntity.ok()
                .body(Result.resSuccess(contrService.newContr(data)));
    }

}
