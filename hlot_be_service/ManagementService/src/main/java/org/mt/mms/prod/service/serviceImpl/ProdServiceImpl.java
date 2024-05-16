package org.mt.mms.prod.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.mt.mms.prod.mapper.ProdMapper;
import org.mt.mms.prod.service.ProdService;
import org.mt.mms.prod.vo.ProdVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdServiceImpl implements ProdService {

    private final ProdMapper prodMapper;

    @Override
    public List<ProdVO> all(String id) throws Exception {
        return prodMapper.selectProds(id);
    }
}
