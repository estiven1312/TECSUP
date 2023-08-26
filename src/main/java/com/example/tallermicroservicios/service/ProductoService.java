package com.example.tallermicroservicios.service;

import com.example.tallermicroservicios.domain.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> findAll();
    Producto findById(Long id);
    public void save(Producto producto);
    public void deleteById(Long id);
}
