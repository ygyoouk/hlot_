package org.mt.mms.cmm.commonError;

import lombok.extern.slf4j.Slf4j;
import org.mt.mms.cmm.dto.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ContollerExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<Result> handleException(Exception e) {
        log.error("에러 => " + e.getMessage());

        return ResponseEntity.ok()
                .body(Result.resError("처리에 실패하였습니다."));
    }

    @ExceptionHandler
    public ResponseEntity<Result> handleException(BadCredentialsException e) {
        log.info("BadCredentialsException");

        return ResponseEntity.ok()
                .body(Result.resError("아이디 혹은 패스워드를 확인해주세요."));
    }
}
