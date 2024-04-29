package org.mt.mms.commonError;

import org.apache.coyote.BadRequestException;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ContollerExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        // 에러 원인에 대한 로깅 추가
        return new ResponseEntity<>(new ErrorResponse("Server Error"), HttpStatus.OK); // 500
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(BadRequestException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.OK); // 400
    }


    @ExceptionHandler public ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.OK);

    }


    static class ErrorResponse {
        String message;
        ErrorResponse(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }


}
