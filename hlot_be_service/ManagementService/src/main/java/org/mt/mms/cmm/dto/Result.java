package org.mt.mms.cmm.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result<T> {

    private boolean isSuccess;
    private boolean isAlert;
    private String message;
    private T data;

    @Builder
    public Result(boolean isSuccess, String message, T data, boolean isAlert){
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
        this.isAlert = isAlert;
    }

    public static <T> Result resSuccess(T data){
        return Result.builder()
                .isSuccess(true)
                .message("성공적으로 처리되었습니다.")
                .data(data)
                .isAlert(false)
                .build();
    }

    public static <T> Result resSuccess(T data, boolean isAlert){
        return Result.builder()
                .isSuccess(true)
                .message("성공적으로 처리되었습니다.")
                .data(data)
                .isAlert(isAlert)
                .build();
    }

    public static <T> Result resError(String message){
        return  Result.builder()
                .isSuccess(false)
                .isAlert(true)
                .message(message)
                .data(null)
                .build();
    }

}
