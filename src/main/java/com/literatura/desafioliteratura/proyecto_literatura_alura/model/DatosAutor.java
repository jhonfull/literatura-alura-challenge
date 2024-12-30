package com.literatura.desafioliteratura.proyecto_literatura_alura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") int anoDeNacimiento,
        @JsonAlias("death_year") int anoDeFallecimiento
) {
}
