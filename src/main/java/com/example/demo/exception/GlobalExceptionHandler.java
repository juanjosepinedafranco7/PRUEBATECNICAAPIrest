package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.dto.HttpGlobalResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<HttpGlobalResponse<Object>> manejarRuntimeException(RuntimeException ex) {
        String mensaje = ex.getMessage();
        HttpStatus status;

        if (mensaje.contains("no encontrada")) {
            status = HttpStatus.NOT_FOUND; 
        } else if (mensaje.contains("CANCELADA") || mensaje.contains("COMPLETADA")) {
            status = HttpStatus.CONFLICT; 
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        HttpGlobalResponse<Object> response = new HttpGlobalResponse<>();
        response.setSuccess(false);
        response.setMessage(mensaje);
        response.setData(null);

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpGlobalResponse<Object>> manejarException(Exception ex) {
        HttpGlobalResponse<Object> response = new HttpGlobalResponse<>();
        response.setSuccess(false);
        response.setMessage("Error interno del servidor: " + ex.getMessage());
        response.setData(null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); 
    }
}