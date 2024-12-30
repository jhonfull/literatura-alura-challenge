package com.literatura.desafioliteratura.proyecto_literatura_alura.model;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @Column(name = "nombre_autor")
    private String nombreAutor;

    @Column(name = "lenguajes")
    private String lenguajes;
    private int numeroDescargas;

    public Libro() {}

    public Libro(DatosLibro datosLibros, Autor autor) {
        this.titulo = datosLibros.titulo();
        setLenguajes(datosLibros.languages());
        this.numeroDescargas = datosLibros.numeroDescargas();
        this.nombreAutor = autor.getNombre();
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<String> getLenguajes() {
        return Arrays.asList(lenguajes.split(","));
    }

    public void setLenguajes(List<String> lenguajes) {
        this.lenguajes = String.join(",", lenguajes);
    }

    public int getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(int numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "--------------- LIBRO ---------------" + "\n" +
                "Título: " + titulo + "\n" +
                "Autor: " + nombreAutor + "\n" +
                "Idioma: " + lenguajes + "\n" +
                "Número de descargas: " + numeroDescargas + "\n" +
                "------------------------------------" + "\n";
    }
}
