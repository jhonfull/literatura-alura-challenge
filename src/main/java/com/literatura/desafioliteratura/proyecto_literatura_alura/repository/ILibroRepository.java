package com.literatura.desafioliteratura.proyecto_literatura_alura.repository;

import com.literatura.desafioliteratura.proyecto_literatura_alura.model.Autor;
import com.literatura.desafioliteratura.proyecto_literatura_alura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ILibroRepository extends JpaRepository<Libro, Long> {

    Libro findByTitulo(String titulo);

    List<Libro> findByLenguajesContaining(String lenguaje);
}
