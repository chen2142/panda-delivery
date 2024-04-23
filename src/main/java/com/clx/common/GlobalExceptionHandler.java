package com.clx.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * exception handling method
     * @param e
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException e){
        log.error(e.getMessage());

        if (e.getMessage().contains("Duplicate entry")){
            String[] splits = e.getMessage().split(" ");
            String msg = splits[2] + "existing";
            return R.error(msg);
        }
        return R.error("unknown exception");
    }

    /**
     * exception handling method
     * @param e
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException e){
        log.error(e.getMessage());

        return R.error(e.getMessage());
    }
}
