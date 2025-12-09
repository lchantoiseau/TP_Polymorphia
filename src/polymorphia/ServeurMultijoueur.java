package polymorphia;

import java.io.*;
import java.net.*;

public class ServeurMultijoueur {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Personnage joueur1;
    private static final int PORT = 12345;
    
    public ServeurMultijoueur(Personnage joueur1) {
        this.joueur1 = joueur1;
    }
    
    public void demarrer() {
        try {
            System.out.println("Démarrage du serveur sur le port " + PORT + "...");
            serverSocket = new ServerSocket(PORT);
            System.out.println("En attente d'un adversaire...");
            
            clientSocket = serverSocket.accept();
            System.out.println("Adversaire connecté !");
            
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            // Envoyer les stats du joueur 1
            envoyerStats(joueur1);
            
            // Recevoir les stats du joueur 2
            Personnage joueur2 = recevoirStats();
            
            System.out.println("\n*** Début du combat PvP ***");
            lancerCombatServeur(joueur1, joueur2);
            
        } catch (IOException e) {
            System.err.println("Erreur serveur: " + e.getMessage());
        } finally {
            fermer();
        }
    }
    
    private void envoyerStats(Personnage p) throws IOException {
        out.println(p.getNom());
        out.println(p.getPointsVie());
        out.println(p.getAttaque());
        out.println(p.getDefense());
    }
    
    private Personnage recevoirStats() throws IOException {
        String nom = in.readLine();
        int pv = Integer.parseInt(in.readLine());
        int att = Integer.parseInt(in.readLine());
        int def = Integer.parseInt(in.readLine());
        
        Personnage p = new Personnage(nom);
        // Ajuster les stats (simplifié)
        return p;
    }
    
    private void lancerCombatServeur(Personnage j1, Personnage j2) {
        try {
            boolean tourJ1 = true;
            
            while (j1.estVivant() && j2.estVivant()) {
                if (tourJ1) {
                    System.out.println("\n--- Votre tour ---");
                    System.out.print("Action (1=Attaquer): ");
                    String action = new java.util.Scanner(System.in).nextLine();
                    
                    out.println(action); // Envoyer action au client
                    
                    if ("1".equals(action)) {
                        int degats = Math.max(1, j1.getAttaque() - j2.getDefense());
                        j2.recevoirDegats(degats);
                        System.out.println("Vous infligez " + degats + " dégâts !");
                    }
                } else {
                    System.out.println("\n--- Tour de l'adversaire ---");
                    String actionAdv = in.readLine(); // Recevoir action du client
                    
                    if ("1".equals(actionAdv)) {
                        int degats = Math.max(1, j2.getAttaque() - j1.getDefense());
                        j1.recevoirDegats(degats);
                        System.out.println("Vous subissez " + degats + " dégâts !");
                    }
                }
                
                tourJ1 = !tourJ1;
                
                if (!j1.estVivant()) {
                    System.out.println("\n*** Vous avez perdu ! ***");
                    out.println("DEFAITE");
                } else if (!j2.estVivant()) {
                    System.out.println("\n*** Vous avez gagné ! ***");
                    out.println("VICTOIRE");
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur de communication: " + e.getMessage());
        }
    }
    
    private void fermer() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de la fermeture: " + e.getMessage());
        }
    }
}
