package org.mt.ops.chatGPT.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.mt.ops.chatGPT.config.ChatGPTConfig;
import org.mt.ops.chatGPT.dto.ChatGPTDTO;
import org.mt.ops.chatGPT.service.ChatGPTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class ChatGPTServiceImpl implements ChatGPTService {

    @Value("${openai.model}")
    private String model;


    private final ChatGPTConfig chatGPTConfig;
    public ChatGPTServiceImpl(ChatGPTConfig chatGPTConfig){
        this.chatGPTConfig = chatGPTConfig;
    }

    @Override
    public List<Map<String, Object>> getModelList(){
        log.info("getModelList() : chatGPT에 api요청을 보내 모델목록을 조회합니다.");

        List<Map<String, Object>> resultList = null;

        /* 1. ChatGPTConfig로 부터 미리 설정해둔 API KEY값이 포함된 header정보를 구함 */
        HttpHeaders headers = chatGPTConfig.httpHeaders();

        /* 2. RestTemplate 구성 */
        ResponseEntity<String> response = chatGPTConfig.restTemplate()
                .exchange(
                        "https://api.openai.com/v1/models",
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        String.class
                );

        try{
            /* 3. 응답결과 Json형식으로 변환 (JSON -> JavaObject) */
            ObjectMapper om = new ObjectMapper();
            Map<String, Object> rMap = om.readValue(String.valueOf(response.getBody()), new TypeReference<>(){
            });
            resultList = (List<Map<String,Object>>)rMap.get("data");

            /* 응답결과 로깅 */
            Set<String> keySet = rMap.keySet();
            for(String key : keySet){
                log.info(String.valueOf(rMap.get(key)));
            }


        } catch (JsonMappingException e){
            log.info("JSONMappingException !!! > " + e.getMessage());
        } catch (JsonProcessingException e){
            log.info("JsonProcessingException !!! > " + e.getMessage());
        }


        return resultList;
    }

    @Override
    public Map<String, Object> prompt(String content) {
        log.info("prompt() : 사용자에게 prompt 를 받아 chatGPT에 api요청을 보내 명령결과를 조회합니다.");

        Map<String, Object> result = new HashMap<>();

        /* 1. ChatGPTConfig로 부터 미리 설정해둔 API KEY값이 포함된 header정보를 구함 */
        HttpHeaders headers = chatGPTConfig.httpHeaders();

        /* 2. DTO 객체 생성 */
        ChatGPTDTO.Messages[] messages = new ChatGPTDTO.Messages[1];
        messages[0] = ChatGPTDTO.Messages.builder().role("user").content(content).build();
        ChatGPTDTO chatGPTDTO = ChatGPTDTO.builder()
                .model(model)
                .messages(messages)
                .build();

        String reqBody = "";
        ObjectMapper om = new ObjectMapper();
        /* 4. 통신을 위한 restTemplate 준비 */
        ResponseEntity response = chatGPTConfig.restTemplate()
                .exchange(
                        "https://api.openai.com/v1/chat/completions",
                        HttpMethod.POST,
                        new HttpEntity<>(chatGPTDTO, headers),
                        String.class
                );

        try{
            /* 5. String to HashMap */
            result = om.readValue(String.valueOf(response.getBody()), new TypeReference<>(){
            });

            log.info("result : " + result);

        } catch (JsonProcessingException e) {
            log.info("JsonProcessingException !!! > " + e.getMessage());
        }

        return result;
    }
}
