package org.mt.ops.chatGPT.service;

import org.mt.ops.chatGPT.dto.ChatGPTDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ChatGPTService {

    List<Map<String,Object>> getModelList() throws Exception;

    Map<String, Object> prompt(String content);
}
