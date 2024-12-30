package com.literatura.desafioliteratura.proyecto_literatura_alura;

import com.literatura.desafioliteratura.proyecto_literatura_alura.principal.Principal;
import com.literatura.desafioliteratura.proyecto_literatura_alura.repository.IAutorRepository;
import com.literatura.desafioliteratura.proyecto_literatura_alura.repository.ILibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoLiteraturaAluraApplication implements CommandLineRunner {

	@Autowired
	private IAutorRepository autorRepository;
	@Autowired
	private ILibroRepository libroRepository;
	public static void main(String[] args) {
		SpringApplication.run(ProyectoLiteraturaAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(autorRepository, libroRepository);
		principal.muestraElMenu();
	}
}
