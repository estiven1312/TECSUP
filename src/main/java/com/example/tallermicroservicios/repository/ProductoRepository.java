package com.example.tallermicroservicios.repository;

import com.example.tallermicroservicios.domain.Producto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long> {
    @Override
    List<Producto> findAll();
}
