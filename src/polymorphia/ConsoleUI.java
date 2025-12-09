package polymorphia;

import java.util.Scanner;

public class ConsoleUI {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void afficherBanniere() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║        POLYMORPHIA RPG GAME            ║");
        System.out.println("║    L'aventure de Javalt de Riv         ║");
        System.out.println("╚════════════════════════════════════════╝\n");
    }
    
    public static void afficherMenuPrincipal() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1. Commercer avec le marchand");
        System.out.println("2. Explorer Polymorphia (chercher des monstres)");
        System.out.println("3. Voir l'inventaire");
        System.out.println("4. Équiper arme/armure");
        System.out.println("5. Utiliser Materia");
        System.out.println("6. Utiliser Potion");
        System.out.println("7. Voir mes statistiques");
        System.out.println("8. Mode Multijoueur (PvP)");
        System.out.println("0. Quitter le jeu");
        System.out.print("\nVotre choix: ");
    }
    
    public static void afficherMenuCombat() {
        System.out.println("\n--- COMBAT ---");
        System.out.println("1. Attaquer");
        System.out.println("2. Utiliser un sort");
        System.out.println("3. Utiliser une potion");
        System.out.println("4. Fuir");
        System.out.print("\nVotre action: ");
    }
    
    public static void afficherMenuMultijoueur() {
        System.out.println("\n===== MODE MULTIJOUEUR =====");
        System.out.println("1. Créer un serveur (Joueur 1)");
        System.out.println("2. Rejoindre un serveur (Joueur 2)");
        System.out.println("0. Retour");
        System.out.print("\nVotre choix: ");
    }
    
    public static int lireChoix() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    public static String lireTexte(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    
    public static void afficherMessage(String message) {
        System.out.println(message);
    }
    
    public static void afficherSeparateur() {
        System.out.println("\n" + "=".repeat(50) + "\n");
    }
    
    public static void pause() {
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }
    
    public static void effacerEcran() {
        // Simuler l'effacement (afficher des lignes vides)
        for (int i = 0; i < 2; i++) {
            System.out.println();
        }
    }
}
