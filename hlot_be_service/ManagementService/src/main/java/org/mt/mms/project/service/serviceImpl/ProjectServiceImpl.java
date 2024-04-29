package org.mt.mms.project.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mt.mms.project.mapper.ProjectMapper;
import org.mt.mms.project.service.ProjectService;
import org.mt.mms.project.vo.ProjectVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private  final ProjectMapper projectMapper;
    @Override
    public List<ProjectVO> all() throws Exception{

        List<ProjectVO> result = null;

        try{
            result = projectMapper.getProjects();
            log.info(result.toString());
        }catch(Exception e){
            log.info(e.getMessage());
        }
        return result;
    }

    @Override
    public void newProject(ProjectVO data) throws Exception{
        String projectId = data.getProjectId();

        // 프로젝트ID가 없는 경우(신규)
        if(projectId.isEmpty()){
            projectMapper.newProject(data);
        }
    }

    @Override
    public ProjectVO one(String projectId) throws Exception{
        log.info("serviceImpl one");
        ProjectVO result = null;

        try{
            result = projectMapper.one(projectId);
            log.info("info{}",result);
        }catch (Exception e){
            log.info(e.getMessage());
        }

        return result;
    }

    @Override
    public void updateProject(ProjectVO data) throws Exception{

        projectMapper.updateProject(data);
    }

    @Override
    public void deleteProject(ArrayList<String> deldata) throws Exception{

        projectMapper.deleteProject(deldata);

    }
}
