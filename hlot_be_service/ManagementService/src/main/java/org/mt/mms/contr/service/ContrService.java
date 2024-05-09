package org.mt.mms.contr.service;

import org.mt.mms.contr.vo.ContrVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContrService {

    List<ContrVO> all() throws Exception;

}
