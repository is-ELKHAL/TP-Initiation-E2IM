// ===== DatabaseConfig.java =====
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    // Configuration de la connexion MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/university_db";
    private static final String USER = "root";  // Change selon ton config
    private static final String PASSWORD = "";  // Change selon ton config

    // Pour PostgreSQL, utilise :
    // private static final String URL = "jdbc:postgresql://localhost:5432/university_db";

    /**
     * Méthode pour obtenir une connexion à la base de données
     * @return Connection object
     */
    public static Connection getConnection() {
        try {
            // Charger le driver JDBC (optionnel pour versions récentes)
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Pour PostgreSQL : Class.forName("org.postgresql.Driver");

            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC non trouvé : " + e.getMessage());
            throw new RuntimeException("Erreur de chargement du driver", e);
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base : " + e.getMessage());
            throw new RuntimeException("Erreur de connexion", e);
        }
    }

    /**
     * Teste la connexion à la base de données
     */
    public static void testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("✓ Connexion réussie à la base de données !");
            }
        } catch (SQLException e) {
            System.err.println("✗ Échec de la connexion : " + e.getMessage());
        }
    }
}