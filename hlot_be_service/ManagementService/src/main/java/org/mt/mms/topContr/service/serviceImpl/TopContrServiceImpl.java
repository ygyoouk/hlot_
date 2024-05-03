package org.mt.mms.topContr.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mt.mms.topContr.mapper.TopContrMapper;
import org.mt.mms.topContr.service.TopContrService;
import org.mt.mms.topContr.vo.TopContrVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TopContrServiceImpl implements TopContrService {

    private  final TopContrMapper topContrMapper;
    @Override
    public List<TopContrVO> all() throws Exception{

        List<TopContrVO> result = null;

        try{
            result = topContrMapper.getTopContrs();
            log.info(result.toString());
        }catch(Exception e){
            log.info(e.getMessage());
        }
        return result;
    }

    @Override
    public int newTopContr(TopContrVO data) throws Exception{
        String projectId = data.getTopContrId();

        // 프로젝트ID가 없는 경우(신규)
        return topContrMapper.newTopContr(data);

    }

    @Override
    public TopContrVO one(String projectId) throws Exception{
        log.info("serviceImpl one");
        TopContrVO result = null;

        try{
            result = topContrMapper.one(projectId);
            log.info("info{}",result);
        }catch (Exception e){
            log.info(e.getMessage());
        }

        return result;
    }

    @Override
    public int updateTopContr(TopContrVO data) throws Exception{

        return topContrMapper.updateTopContr(data);
    }

    @Override
    public int deleteTopContr(ArrayList<String> deldata) throws Exception{

       return  topContrMapper.deleteTopContr(deldata);

    }
}
