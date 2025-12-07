// ===== Main.java =====
import config.DatabaseConfig;
import service.UniversityService;
import java.util.Scanner;

/**
 * Classe principale avec interface console
 */
public class Main {

    private static final UniversityService service = new UniversityService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Teste la connexion à la base de données
        System.out.println("=== SYSTÈME DE GESTION UNIVERSITAIRE ===\n");
        DatabaseConfig.testConnection();

        // Lance le menu principal
        boolean running = true;

        while (running) {
            afficherMenu();
            int choix = lireChoix();

            switch (choix) {
                case 1:
                    listerEtudiants();
                    break;
                case 2:
                    ajouterEtudiant();
                    break;
                case 3:
                    listerCours();
                    break;
                case 4:
                    ajouterCours();
                    break;
                case 5:
                    inscrireEtudiant();
                    break;
                case 6:
                    listerCoursEtudiant();
                    break;
                case 7:
                    supprimerEtudiant();
                    break;
                case 0:
                    running = false;
                    System.out.println(" Au revoir !");
                    break;
                default:
                    System.out.println("\n✗ Choix invalide. Réessayez.\n");
            }
        }

        scanner.close();
    }

    /**
     * Affiche le menu principal
     */
    private static void afficherMenu() {
        System.out.println("║ 1. Lister les étudiants            ║");
        System.out.println("║ 2. Ajouter un étudiant             ║");
        System.out.println("║ 3. Lister les cours                ║");
        System.out.println("║ 4. Ajouter un cours                ║");
        System.out.println("║ 5. Inscrire un étudiant            ║");
        System.out.println("║ 6. Lister les cours d'un étudiant  ║");
        System.out.println("║ 7. Supprimer un étudiant           ║");
        System.out.println("║ 0. Quitter                         ║");

        System.out.print("Votre choix : ");
    }


    private static int lireChoix() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }


    private static void listerEtudiants() {
        System.out.println();
        service.listStudents();
    }


    private static void ajouterEtudiant() {
        System.out.println("\n=== AJOUTER UN ÉTUDIANT ===");

        System.out.print("Nom complet : ");
        String nom = scanner.nextLine();

        System.out.print("Email : ");
        String email = scanner.nextLine();

        if (nom.trim().isEmpty() || email.trim().isEmpty()) {
            System.out.println("✗ Le nom et l'email sont obligatoires !\n");
            return;
        }

        service.addStudent(nom, email);
        System.out.println();
    }

    /**
     * 3. Liste tous les cours
     */
    private static void listerCours() {
        System.out.println();
        service.listCourses();
    }

    /**
     * 4. Ajoute un nouveau cours
     */
    private static void ajouterCours() {
        System.out.println("\n=== AJOUTER UN COURS ===");

        System.out.print("Nom du cours : ");
        String nom = scanner.nextLine();

        System.out.print("Description : ");
        String description = scanner.nextLine();

        if (nom.trim().isEmpty()) {
            System.out.println("✗ Le nom du cours est obligatoire !\n");
            return;
        }

        service.addCourse(nom, description);
        System.out.println();
    }

    /**
     * 5. Inscrit un étudiant à un cours
     */
    private static void inscrireEtudiant() {
        System.out.println("\n=== INSCRIRE UN ÉTUDIANT ===");

        // Affiche les étudiants disponibles
        service.listStudents();

        System.out.print("ID de l'étudiant : ");
        int studentId = lireChoix();

        // Affiche les cours disponibles
        service.listCourses();

        System.out.print("ID du cours : ");
        int courseId = lireChoix();

        service.enrollStudent(studentId, courseId);
        System.out.println();
    }

    /**
     * 6. Liste les cours d'un étudiant
     */
    private static void listerCoursEtudiant() {
        System.out.println("\n=== COURS D'UN ÉTUDIANT ===");

        // Affiche les étudiants disponibles
        service.listStudents();

        System.out.print("ID de l'étudiant : ");
        int studentId = lireChoix();

        service.listStudentCourses(studentId);
    }

    /**
     * 7. Supprime un étudiant
     */
    private static void supprimerEtudiant() {
        System.out.println("\n=== SUPPRIMER UN ÉTUDIANT ===");

        // Affiche les étudiants disponibles
        service.listStudents();

        System.out.print("ID de l'étudiant à supprimer : ");
        int studentId = lireChoix();

        System.out.print("Êtes-vous sûr ? (oui/non) : ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("oui")) {
            service.deleteStudent(studentId);
        } else {
            System.out.println("✗ Suppression annulée.\n");
        }
    }
}