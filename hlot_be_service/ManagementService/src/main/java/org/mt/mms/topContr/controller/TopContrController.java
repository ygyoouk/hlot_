package org.mt.mms.topContr.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.mt.cmm.dto.Result;
import org.mt.mms.topContr.service.TopContrService;

import org.mt.mms.topContr.vo.TopContrVO;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/topContr")
    public ResponseEntity<Result> newContr(@RequestBody TopContrVO data) throws Exception{
        log.info(data.toString());

        // 프로젝트 Id
        String topContrId = data.getTopContrId();

        // 수정
        if(!topContrId.isEmpty()){
            log.info("프로젝트 수정");
            return ResponseEntity.ok()
                    .body(Result.resSuccess(topContrService.updateTopContr(data)));
        }else{
            /* 프로젝트 단건 등록*/
            return ResponseEntity.ok()
                    .body(Result.resSuccess(topContrService.newTopContr(data)));
        }

    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<Result> one(@PathVariable String projectId) throws Exception{
        log.info("projectId ==>" + projectId);

        return ResponseEntity.ok()
                .body(Result.resSuccess(topContrService.one(projectId)));
    }

    @PutMapping("/project")
    public void updateProject(@RequestBody TopContrVO data) throws Exception{
        log.info("update-project");
        log.info("info {}", data.toString());

        // 프로젝트 관리 수정
        topContrService.updateTopContr(data);

    }

    @DeleteMapping("/project")
    public void deleteProject(@RequestBody ArrayList<String> deldata) throws Exception{
        log.info("delete-project");
        log.info("info {}", deldata.toString());


        topContrService.deleteTopContr(deldata);
    }

}
