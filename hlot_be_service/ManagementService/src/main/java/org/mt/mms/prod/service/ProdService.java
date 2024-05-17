package org.mt.mms.prod.service;

import org.mt.mms.prod.vo.ProdVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProdService {

    List<ProdVO> all(String id) throws Exception;

    int newProd(ProdVO prodVO) throws Exception;

}
