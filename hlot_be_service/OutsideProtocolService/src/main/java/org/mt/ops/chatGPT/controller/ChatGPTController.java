package org.mt.ops.chatGPT.controller;

import lombok.extern.slf4j.Slf4j;
import org.mt.ops.chatGPT.dto.ChatGPTDTO;
import org.mt.ops.chatGPT.service.ChatGPTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hlot/ops/chatGPT")
@Slf4j
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    public ChatGPTController(ChatGPTService chatGPTService){
        this.chatGPTService = chatGPTService;
    }

    /**
     * ChatGPT 에서 제공하는 모델들의 목록을 조회합니다. ss
     */
    @GetMapping("/modelList")
    public ResponseEntity<List<Map<String, Object>>> getModelList() throws Exception {
        log.info("/hlot/ops/chatGPT/modelList >>>>>>>>>>>>>>>>>>>>>>>");

        List<Map<String,Object>> result = chatGPTService.getModelList();
        return ResponseEntity.ok().body(result);
    }

    /**
     * prompt를 받아 ChatGPT에 결과값을 요청합니다.
     */
    @PostMapping("/prompt")
    public ResponseEntity<Map<String, Object>> prompt(String content){
        log.info("/hlot/ops/chatGPT/prompt >>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        Map<String, Object> result = chatGPTService.prompt(content);
        return ResponseEntity.ok().body(result);
    }
}
