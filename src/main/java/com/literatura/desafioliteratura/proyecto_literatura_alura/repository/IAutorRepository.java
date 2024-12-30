package com.literatura.desafioliteratura.proyecto_literatura_alura.repository;

import com.literatura.desafioliteratura.proyecto_literatura_alura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface IAutorRepository extends JpaRepository<Autor, Long> {

    Autor findByNombreIgnoreCase(String nombre);

    List<Autor> findByAnoDeNacimientoLessThanEqualAndAnoDeFallecimientoGreaterThanEqual(int añoInicial, int añoFinal);
}
