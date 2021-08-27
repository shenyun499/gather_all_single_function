package pers.xue.skills.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author huangzhixue
 * @date 2021/8/20 11:51 上午
 * @Description
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CaseException.class)
    public ResponseEntity<GlobalExceptionResponse> handlerCaseException(CaseException e) {
        logger.error("CaseException --- : ", e);
        GlobalExceptionResponse globalExceptionResponse = new GlobalExceptionResponse(e.getCaseConstant().getCode(), e.getMessage(), null);
        return new ResponseEntity<>(globalExceptionResponse, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalExceptionResponse> handlerException(Exception e) {
        logger.error("Exception --- :", e);
        GlobalExceptionResponse globalExceptionResponse = new GlobalExceptionResponse(e.getMessage());
        return new ResponseEntity<>(globalExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
