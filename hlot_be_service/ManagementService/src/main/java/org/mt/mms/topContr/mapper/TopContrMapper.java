package org.mt.mms.topContr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.mt.mms.topContr.vo.TopContrVO;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface TopContrMapper {

    List<TopContrVO> getTopContrs(TopContrVO searchParam) throws Exception;

    int newTopContr(TopContrVO data) throws Exception;

    TopContrVO one(String topContrId) throws Exception;

    int updateTopContr(TopContrVO data) throws Exception;

    int deleteTopContr(ArrayList<String> deldata) throws Exception;
}
