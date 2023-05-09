package com.vaccineadminsystem.exception.handle;

import com.vaccineadminsystem.dto.MessageRes;
import com.vaccineadminsystem.dto.RestResponse;
import com.vaccineadminsystem.exception.*;
import com.vaccineadminsystem.util.ErrorMess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class AppExceptionHandle {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = {ImageNotFoundException.class})
    public ResponseEntity<Object> imageNotFoundHandle(
            Exception ex, WebRequest request) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(200).body(new MessageRes(ex.getMessage()));
    }

    @ExceptionHandler(value = {SaveImageException.class})
    public ResponseEntity<?> saveImageExceptionHandle(Exception e) {
        logger.error(e.getMessage());
        RestResponse response = new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({UsernameNotFoundException.class, AuthenticationException.class})
    public ResponseEntity<?> usernameNotFoundHandle(Exception e) {
        logger.error(e.getMessage());
        RestResponse response = new RestResponse(HttpStatus.UNAUTHORIZED.value(), ErrorMess.INVALID_CREDENTIAL);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(ExistVaccineTypeIdException.class)
    public ResponseEntity<?> existVaccineTypeIdExceptionHandle(Exception e) {
        logger.error(e.getMessage());
        RestResponse response = new RestResponse(400, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SaveInActiveVaccineTypeException.class)
    public ResponseEntity<?> saveInActiveVaccineTypeExceptionHandle(Exception e) {
        logger.error(e.getMessage());
        RestResponse response = new RestResponse(400, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImportFileException.class)
    public ResponseEntity<Object> importFileExceptionHandle(Exception e) {
        logger.error(e.getMessage());
        RestResponse response = new RestResponse(400, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistVaccineTypeIdException.class)
    public ResponseEntity<?> notExistVaccineTypeIdException(Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", 400);
        response.put("message", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExistEmployeeException.class)
    public ResponseEntity<?> employeeExistHandle(Exception e) {
        RestResponse response = new RestResponse(400, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidAgeException.class})
    public ResponseEntity<?> invalidAgeExceptionHandle(Exception e) {
        RestResponse response = new RestResponse(400, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintException.class})
    public ResponseEntity<?> constraintExceptionHandle(Exception e){
        logger.error(e.getMessage());
        RestResponse response = new RestResponse(400, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({SaveEmployeeException.class})
    public ResponseEntity<?> saveEmployeeExceptionHandle(Exception e){
        RestResponse response = new RestResponse(500, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
