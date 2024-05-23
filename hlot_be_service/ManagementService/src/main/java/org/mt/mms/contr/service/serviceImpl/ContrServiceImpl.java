package org.mt.mms.contr.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mt.mms.contr.mapper.ContrMapper;
import org.mt.mms.contr.service.ContrService;
import org.mt.mms.contr.vo.ContrVO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContrServiceImpl implements ContrService {

    private final ContrMapper contrMapper;

    /**
     * 계약 전체 조회
     * */
    @Override
    public List<ContrVO> all(HashMap<String, String> params) throws Exception {
        return contrMapper.all(params);
    }

    /**
     * 계약 단건 조회
     * */
    @Override
    public ContrVO one(String contrId) {
        return contrMapper.one(contrId);
    }
}
