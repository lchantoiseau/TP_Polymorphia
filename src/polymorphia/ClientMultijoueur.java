package polymorphia;

import java.io.*;
import java.net.*;

public class ClientMultijoueur {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Personnage joueur2;
    private static final int PORT = 12345;
    
    public ClientMultijoueur(Personnage joueur2) {
        this.joueur2 = joueur2;
    }
    
    public void connecter(String host) {
        try {
            System.out.println("Connexion au serveur " + host + ":" + PORT + "...");
            socket = new Socket(host, PORT);
            System.out.println("Connecté au serveur !");
            
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // Recevoir les stats du joueur 1
            Personnage joueur1 = recevoirStats();
            
            // Envoyer les stats du joueur 2
            envoyerStats(joueur2);
            
            System.out.println("\n*** Début du combat PvP ***");
            lancerCombatClient(joueur1, joueur2);
            
        } catch (IOException e) {
            System.err.println("Erreur client: " + e.getMessage());
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
        return p;
    }
    
    private void lancerCombatClient(Personnage j1, Personnage j2) {
        try {
            boolean tourJ1 = true;
            
            while (j1.estVivant() && j2.estVivant()) {
                if (!tourJ1) {
                    System.out.println("\n--- Votre tour ---");
                    System.out.print("Action (1=Attaquer): ");
                    @SuppressWarnings("resource")
                    String action = new java.util.Scanner(System.in).nextLine();
                    
                    out.println(action);
                    
                    if ("1".equals(action)) {
                        int degats = Math.max(1, j2.getAttaque() - j1.getDefense());
                        j1.recevoirDegats(degats);
                        System.out.println("Vous infligez " + degats + " dégâts !");
                    }
                } else {
                    System.out.println("\n--- Tour de l'adversaire ---");
                    String actionAdv = in.readLine();
                    
                    if ("1".equals(actionAdv)) {
                        int degats = Math.max(1, j1.getAttaque() - j2.getDefense());
                        j2.recevoirDegats(degats);
                        System.out.println("Vous subissez " + degats + " dégâts !");
                    }
                }
                
                tourJ1 = !tourJ1;
                
                String resultat = in.readLine();
                if ("VICTOIRE".equals(resultat)) {
                    System.out.println("\n*** Vous avez perdu ! ***");
                    break;
                } else if ("DEFAITE".equals(resultat)) {
                    System.out.println("\n*** Vous avez gagné ! ***");
                    break;
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
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de la fermeture: " + e.getMessage());
        }
    }
}
