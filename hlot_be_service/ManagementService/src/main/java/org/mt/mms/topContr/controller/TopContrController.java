package org.mt.mms.topContr.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.mt.mms.cmm.dto.Result;
import org.mt.mms.topContr.service.TopContrService;

import org.mt.mms.topContr.vo.TopContrVO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
@Slf4j // log용도
@RequestMapping(value = "/api")
@RequiredArgsConstructor // autowired 대체자
public class TopContrController {


    private final TopContrService topContrService;

    @GetMapping("/topContr")
    public ResponseEntity<Result> all() throws Exception{

     /* 프로젝트 리스트 조회*/
     return ResponseEntity.ok()
             .body(Result.resSuccess(topContrService.all()));

    }


    @PostMapping(value = "/topContr" ,consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Result> newContr(@RequestPart TopContrVO data, @RequestPart(value = "file", required = false)MultipartFile file) throws Exception{
        log.info("data====>" +  data);
        log.info("FILE OG NAME ====>" +  file.getOriginalFilename());

        // 프로젝트 Id
        String topContrId = data.getTopContrId();

        // 수정
        if(!topContrId.isEmpty()){
            log.info("프로젝트 수정");
            return ResponseEntity.ok()
                    .body(Result.resSuccess(topContrService.updateTopContr(data,file)));
        }else{
            /* 프로젝트 단건 등록*/
            log.info("===========원계약 등록==========");
            return ResponseEntity.ok()
                    .body(Result.resSuccess(topContrService.newTopContr(data,file)));
        }

    }

    @GetMapping("/topContr/{topContrId}")
    public ResponseEntity<Result> one(@PathVariable String topContrId) throws Exception{
        log.info("TOP_CONTR_ID ==>" + topContrId);

        return ResponseEntity.ok()
                .body(Result.resSuccess(topContrService.one(topContrId)));
    }

//    @PutMapping("/topContr")
//    public void updateProject(@RequestBody TopContrVO data) throws Exception{
//        log.info("update-project");
//        log.info("info {}", data.toString());
//
//        // 프로젝트 관리 수정
//        topContrService.updateTopContr(data);
//
//    }

    @DeleteMapping("/topContr")
    public void deleteProject(@RequestBody ArrayList<String> deldata) throws Exception{
        log.info("delete-project");
        log.info("info {}", deldata.toString());


        topContrService.deleteTopContr(deldata);
    }

}
