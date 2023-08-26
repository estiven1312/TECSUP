package com.example.tallermicroservicios.domain.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public @Data class ProductoDTO {
    private String nombre;
    private String precio;
    private String detalles;
    private MultipartFile imagen;
}
