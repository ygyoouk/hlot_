package org.mt.mms.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.mt.mms.project.vo.ProjectVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectMapper {

    List<ProjectVO> getProjects() throws Exception;

    void newProject(ProjectVO data) throws Exception;

    ProjectVO one(String projectId) throws Exception;

    void updateProject(ProjectVO data) throws Exception;

    void deleteProject(ArrayList<String> deldata) throws Exception;
}
