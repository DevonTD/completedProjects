/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberrest.controller;

import com.sg.guessthenumberrest.service.ErrorHandler;
import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author devon
 */
@ControllerAdvice
@RestController
public class GameControllerExceptionHandler extends ResponseEntityExceptionHandler {

    final String CONSTRAINT_MSG = "Unable to generate request. Please ensure all fields are valid and try again.";

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public final ResponseEntity<ErrorHandler> handleSqlException(
            SQLIntegrityConstraintViolationException ex,
            WebRequest request) {

        ErrorHandler err = new ErrorHandler();
        err.setMsg(CONSTRAINT_MSG);
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public final ResponseEntity<ErrorHandler> handleArrayException(
            ArrayIndexOutOfBoundsException ex,
            WebRequest request) {

        ErrorHandler err = new ErrorHandler();
        err.setMsg(CONSTRAINT_MSG);
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
     @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ErrorHandler> handleDataException(
            DataIntegrityViolationException ex,
            WebRequest request) {

        ErrorHandler err = new ErrorHandler();
        err.setMsg(CONSTRAINT_MSG);
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
