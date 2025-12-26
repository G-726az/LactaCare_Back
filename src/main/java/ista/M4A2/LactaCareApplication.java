package ista.M4A2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class LactaCareApplication {

	public static void main(String[] args) {
		System.out.println("Spring en marcha");
		SpringApplication.run(LactaCareApplication.class, args);
		System.out.println("Ejecucion de la aplicacion finalizada");
	}

	@Bean
	CommandLineRunner testConnection(DataSource dataSource) {
		return args -> {
			try (Connection connection = dataSource.getConnection()) {
				System.out.println("✅ Conexión exitosa a la base de datos!");
				System.out.println("📊 Base de datos: " + connection.getCatalog());
			} catch (Exception e) {
				System.err.println("❌ Error de conexión: " + e.getMessage());
			}
		};
	}
	/**
	 * // NO BORRAR ES PARA TENER EL PRIMER USUARIO ADMINISTRADOR
	 * 
	 * @Bean public CommandLineRunner imprimirHashPrueba(PasswordEncoder
	 *       passwordEncoder) { return args -> { String passRaw = "12345"; String
	 *       passHash = passwordEncoder.encode(passRaw);
	 *       System.out.println("==========================================");
	 *       System.out.println("HASH PARA '12345': " + passHash);
	 *       System.out.println("=========================================="); }; }
	 */
}
