package org.mt.mms.cmm.commonError;

import org.apache.coyote.BadRequestException;
import org.apache.ibatis.javassist.NotFoundException;
import org.mt.mms.cmm.dto.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ContollerExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<Result> handleException(Exception e) {
        // 에러 원인에 대한 로깅 추가
        return ResponseEntity.ok()
                .body(Result.resError("Exception"));
    }


    @ExceptionHandler
    public ResponseEntity<Result> handleException(BadRequestException e) {
        // 에러 원인에 대한 로깅 추가
        return ResponseEntity.ok()
                .body(Result.resError("BadRequestException"));
    }


    @ExceptionHandler
    public ResponseEntity<Result> handleException(NotFoundException e) {
        // 에러 원인에 대한 로깅 추가
        return ResponseEntity.ok()
                .body(Result.resError("NotFoundException"));
    }


}
