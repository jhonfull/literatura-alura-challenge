package com.literatura.desafioliteratura.proyecto_literatura_alura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int anoDeNacimiento;
    private int anoDeFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.anoDeNacimiento = datosAutor.anoDeNacimiento();
        this.anoDeFallecimiento = datosAutor.anoDeFallecimiento();
    }

    public Autor(){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnoDeNaciemnto() {
        return anoDeNacimiento;
    }

    public void setAnoDeNaciemnto(int anoDeNacimiento) {
        this.anoDeNacimiento = anoDeNacimiento;
    }

    public int getAnoDeFallecimiento() {
        return anoDeFallecimiento;
    }

    public void setAnoDeFallecimiento(int anoDeFallecimiento) {
        this.anoDeFallecimiento = anoDeFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    // Obtener solo el título de los libros
    public String toString() {
        StringBuilder librosTitulos = new StringBuilder();
        for (Libro libro : libros) {
            librosTitulos.append(libro.getTitulo()).append(", ");
        }

        // Eliminar la última coma y espacio
        if (librosTitulos.length() > 0) {
            librosTitulos.setLength(librosTitulos.length() - 2);
        }

        return  "--------------- AUTOR ---------------" + "\n" +
                "Autor: " + nombre + "\n" +
                "Fecha de nacimiento: " + anoDeNacimiento + "\n" +
                "Fecha de fallecimiento: " + anoDeFallecimiento + "\n" +
                "Libros: " + librosTitulos + "\n";
    }
}
