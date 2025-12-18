package polymorphia;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServeurMultijoueur {

    private static final int PORT = 8001;

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private Personnage joueurServeur;
    private Personnage joueurClient;

    public ServeurMultijoueur(Personnage joueurServeur) {
        this.joueurServeur = joueurServeur;
    }

    public void demarrer() {
        try {
            String ipLocale = InetAddress.getLocalHost().getHostAddress();

            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║     SERVEUR MULTIJOUEUR DÉMARRÉ        ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("📡 IP : " + ipLocale);
            System.out.println("🔌 Port : " + PORT);
            System.out.println("⏳ En attente d'un adversaire...\n");

            serverSocket = new ServerSocket(PORT);
            clientSocket = serverSocket.accept();
            System.out.println("✅ Adversaire connecté !");

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Échange des stats
            envoyerStats(joueurServeur);
            joueurClient = recevoirStats();

            lancerCombat();

        } catch (IOException e) {
            System.err.println("❌ Erreur serveur : " + e.getMessage());
        } finally {
            fermer();
        }
    }

    /* =========================
       ÉCHANGE DES STATS
       ========================= */

    private void envoyerStats(Personnage p) {
        out.println(p.getNom());
        out.println(p.getPointsVie());
        out.println(p.getPointsVieMax());
        out.println(p.getAttaque());
        out.println(p.getDefense());
    }

    private Personnage recevoirStats() throws IOException {
        String nom = in.readLine();
        int pv = Integer.parseInt(in.readLine());
        int pvMax = Integer.parseInt(in.readLine());
        int attaque = Integer.parseInt(in.readLine());
        int defense = Integer.parseInt(in.readLine());

        Personnage p = new Personnage(nom);
        p.setPointsVieMax(pvMax);
        p.setPointsVie(pv);
        p.setAttaque(attaque);
        p.setDefense(defense);

        return p;
    }

    /* =========================
       COMBAT PvP (SERVEUR ONLY)
       ========================= */

    private void lancerCombat() throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean tourServeur = true;

        out.println("DEBUT");

        while (joueurServeur.estVivant() && joueurClient.estVivant()) {

            if (tourServeur) {
                // Tour du serveur (joueur local)
                System.out.println("\n--- Votre tour ---");
                System.out.print("Action (1 = Attaquer) : ");
                String action = scanner.nextLine();

                if ("1".equals(action)) {
                    int degats = calculerDegats(joueurServeur, joueurClient);
                    joueurClient.setPointsVie(joueurClient.getPointsVie() - degats);

                    System.out.println("💥 Vous infligez " + degats + " dégâts !");
                    envoyerEtat(degats);
                }

            } else {
                // Tour du client
                out.println("TON_TOUR");
                String actionClient = in.readLine();

                if ("ACTION 1".equals(actionClient)) {
                    int degats = calculerDegats(joueurClient, joueurServeur);
                    joueurServeur.setPointsVie(joueurServeur.getPointsVie() - degats);

                    System.out.println("💥 Vous subissez " + degats + " dégâts !");
                    envoyerEtat(degats);
                }
            }

            tourServeur = !tourServeur;
        }

        // Fin du combat
        if (joueurServeur.estVivant()) {
            out.println("DEFAITE");
            System.out.println("\n🏆 VICTOIRE !");
        } else {
            out.println("VICTOIRE");
            System.out.println("\n💀 DÉFAITE...");
        }
    }

    private int calculerDegats(Personnage attaquant, Personnage defenseur) {
        return Math.max(1, attaquant.getAttaque() - defenseur.getDefense());
    }

    private void envoyerEtat(int degats) {
        out.println("DEGATS " + degats);
        out.println("PV_SERVEUR " + joueurServeur.getPointsVie());
        out.println("PV_CLIENT " + joueurClient.getPointsVie());
    }

    /* =========================
       FERMETURE
       ========================= */

    private void fermer() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            System.err.println("Erreur fermeture : " + e.getMessage());
        }
    }
}
