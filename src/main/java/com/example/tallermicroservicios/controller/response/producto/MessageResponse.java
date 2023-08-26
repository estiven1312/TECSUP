package com.example.tallermicroservicios.controller.response.producto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@NoArgsConstructor
public @Data class MessageResponse {
    private String message;
    private HttpStatus httpStatus;

}
