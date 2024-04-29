package org.mt.mms.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.mt.mms.project.service.ProjectService;
import org.mt.mms.project.vo.DeleteVo;
import org.mt.mms.project.vo.ProjectVO;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j // log용도
@RequestMapping(value = "/api")
@RequiredArgsConstructor // autowired 대체자
public class ProjectController {


    private final ProjectService projectService;

    @GetMapping("/project")
    public ResponseEntity<List<ProjectVO>> all() throws Exception{
        log.info("testtesttest");
        log.info("testetsetse");
     log.info("selectProjectList");
     
     /* 프로젝트 리스트 조회*/
     return ResponseEntity.ok()
             .body(projectService.all());

    }

    @PostMapping("/project")
    public void newProject(@RequestBody ProjectVO data) throws Exception{
        log.info("newProject-Controller");
        log.info(data.toString());
        
        // 프로젝트 Id
        String projectId = data.getProjectId();
        
        /* 프로젝트 단건 등록*/
        projectService.newProject(data);
        
        // 수정
        if(!projectId.isEmpty()){
            log.info("프로젝트 수정");
            projectService.updateProject(data);
        }
        
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ProjectVO> one(@PathVariable String projectId) throws Exception{
        log.info("projectId ==>" + projectId);

        return ResponseEntity.ok()
                .body(projectService.one(projectId));
    }

    @PutMapping("/project")
    public void updateProject(@RequestBody ProjectVO data) throws Exception{
        log.info("update-project");
        log.info("info {}", data.toString());

        // 프로젝트 관리 수정
        projectService.updateProject(data);

    }

    @DeleteMapping("/project")
    public void deleteProject(@RequestBody ArrayList<String> deldata) throws Exception{
        log.info("delete-project");
        log.info("info {}", deldata.toString());


        projectService.deleteProject(deldata);
    }

}
