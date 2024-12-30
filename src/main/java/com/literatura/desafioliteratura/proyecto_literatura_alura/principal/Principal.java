package com.literatura.desafioliteratura.proyecto_literatura_alura.principal;

import com.literatura.desafioliteratura.proyecto_literatura_alura.model.*;
import com.literatura.desafioliteratura.proyecto_literatura_alura.repository.IAutorRepository;
import com.literatura.desafioliteratura.proyecto_literatura_alura.repository.ILibroRepository;
import com.literatura.desafioliteratura.proyecto_literatura_alura.service.ConsumoAPI;
import com.literatura.desafioliteratura.proyecto_literatura_alura.service.ConvierteDatos;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    private IAutorRepository autorRepository;
    private ILibroRepository libroRepository;

    public Principal(IAutorRepository autorRepository, ILibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public void muestraElMenu() {

        var opcion = -1;
        System.out.println("Bienvenido a libreria colombia!");
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por nombre
                    2 - Mostrar libros registrados
                    3 - Mostrar autores registrados
                    4 - Buscar autores vivos en un determinado año
                    5 - Listar libros por idioma

                    0 - Salir
                    """;

            System.out.println(menu);
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorNombre();
                    break;
                case 2:
                    mostrarLibros();
                    break;
                case 3:
                    mostrarAutores();
                    break;
                case 4:
                    buscarAutoresVivosDesdeUnAno();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private Datos getDatosLibros() {
        var nombreLibro = scanner.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE+nombreLibro.replace(" ", "+"));
        Datos datosLibros = conversor.obtenerDatos(json, Datos.class);
        return datosLibros;
    }

    private Libro crearLibro(DatosLibro datosLibro, Autor autor) {
        if (autor != null) {
           return new Libro(datosLibro, autor);
        } else {
            System.out.println("No se encontro el autor");
            return null;
        }
    }

    private void buscarLibroPorNombre() {
        System.out.println("Escribe el libro que deseas buscar: ");
        Datos datos = getDatosLibros();
        if (!datos.resultados().isEmpty()) {
            DatosLibro datosLibro = datos.resultados().get(0);
            DatosAutor datosAutor = datosLibro.autor().get(0);
            Libro libro = null;
            Libro libroRepositorio = libroRepository.findByTitulo(datosLibro.titulo());
            if (libroRepositorio != null) {
                System.out.println("Este libro ya esta registrado en la base de datos");
                System.out.println(libroRepositorio.toString());
            } else {
                Autor autorRepositorio = autorRepository.findByNombreIgnoreCase(datosLibro.autor().get(0).nombre());
                if (autorRepositorio != null) {
                    libro = crearLibro(datosLibro, autorRepositorio);
                    libroRepository.save(libro);
                    System.out.println("Libro agregado\n");
                    System.out.println(libro);
                } else {
                    Autor autor = new Autor(datosAutor);
                    autor = autorRepository.save(autor);
                    libro = crearLibro(datosLibro, autor);
                    libroRepository.save(libro);
                    System.out.println("Libro agregado\n");
                    System.out.println(libro);
                }
            }
        } else {
            System.out.println("El libro no existe");
        }
    }

    private void mostrarLibros() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("no hay libros registrados");
            return;
        }
        System.out.println("Los libros registrados son:\n");
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    private void mostrarAutores() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("no hay autores registrados");
            return;
        }
        System.out.println("Los autores registrados son:\n");
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }

    private void buscarAutoresVivosDesdeUnAno() {
        System.out.println("*****Buscar Autores por año*****\n\n" + "Ingrese el año:");
        var ano = scanner.nextInt();
        scanner.nextLine();
        if (ano < 0) {
            System.out.println("año negativo, no valido, ingresa el año de nuevo");
            return;
        }
        List<Autor> autorAno = autorRepository.findByAnoDeNacimientoLessThanEqualAndAnoDeFallecimientoGreaterThanEqual(ano, ano);
        if (autorAno.isEmpty()) {
            System.out.println("No hay autores registrados en ese año");
            return;
        }
        System.out.println("Los autores registrados en el año "+ano+" son:\n");
        autorAno.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }

    private void mostrarLibrosPorIdioma() {
        System.out.println("Ingresa el idioma de los libros de tu interes:");
        String idiomas = """
                es - Español
                en - Ingles
                pt - Portugués
                fr - Francés
                """;
        System.out.println(idiomas);
        var idioma = scanner.nextLine();
        if (!idioma.equals("es") && !idioma.equals("en") && !idioma.equals("pt") &&
        !idioma.equals("fr")) {
            System.out.println("Idioma no valido, intenta de nuevo");
            return;
        }
        List<Libro> librosPorIdioma = libroRepository.findByLenguajesContaining(idioma);
        if (librosPorIdioma.isEmpty()) {
            System.out.println("No hay libros en ese idioma");
            return;
        }
        System.out.println("Los Libros con el idioma seleccionado son:\n");
        librosPorIdioma.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }
}
