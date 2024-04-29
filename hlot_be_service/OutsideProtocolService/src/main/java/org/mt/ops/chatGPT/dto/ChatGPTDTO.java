package org.mt.ops.chatGPT.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatGPTDTO {

    private String model;

    private Messages[] messages;

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Messages {
        private String role;
        private String content;
    }


    @Builder
    ChatGPTDTO(String model, String role, Messages[] messages){
        this.model = model;
        this.messages = messages;
    }
}
