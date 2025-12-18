package polymorphia;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientMultijoueur {

    private static final int PORT = 8001;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    // État affiché (pas de logique métier)
    private int pvMoi;
    private int pvAdversaire;

    public void connecter(String host) {
        try {
            System.out.println("Connexion au serveur " + host + ":" + PORT + "...");
            socket = new Socket(host, PORT);
            System.out.println("✅ Connecté au serveur !");

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            recevoirStatsServeur();
            envoyerStatsClient();

            lancerCombat();

        } catch (IOException e) {
            System.err.println("❌ Erreur client : " + e.getMessage());
        } finally {
            fermer();
        }
    }

    /* =========================
       ÉCHANGE DES STATS
       ========================= */

    private void recevoirStatsServeur() throws IOException {
        String nom = in.readLine(); // ignoré côté client
        pvMoi = Integer.parseInt(in.readLine());
        in.readLine(); // pv max (inutile côté client)
        in.readLine(); // attaque
        in.readLine(); // défense

        System.out.println("🧍 Adversaire connecté. Combat prêt !");
    }

    private void envoyerStatsClient() {
        // Stats simples envoyées au serveur (autorité)
        out.println("Client");
        out.println(100);
        out.println(100);
        out.println(10);
        out.println(5);
    }

    /* =========================
       COMBAT PvP (CLIENT ONLY UI)
       ========================= */

    private void lancerCombat() throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String message = in.readLine();

            if (message == null) break;

            if ("DEBUT".equals(message)) {
                System.out.println("\n⚔️  Le combat commence !");
            }
            else if ("TON_TOUR".equals(message)) {
                System.out.println("\n--- Votre tour ---");
                System.out.print("Action (1 = Attaquer) : ");
                String action = scanner.nextLine();
                out.println("ACTION " + action);
            }
            else if (message.startsWith("DEGATS")) {
                int degats = Integer.parseInt(message.split(" ")[1]);
                System.out.println("💥 Dégâts infligés : " + degats);

                pvMoi = Integer.parseInt(in.readLine().split(" ")[1]);
                pvAdversaire = Integer.parseInt(in.readLine().split(" ")[1]);

                afficherEtat();
            }
            else if ("VICTOIRE".equals(message)) {
                System.out.println("\n🏆 VICTOIRE !");
                break;
            }
            else if ("DEFAITE".equals(message)) {
                System.out.println("\n💀 DÉFAITE...");
                break;
            }
        }
    }

    private void afficherEtat() {
        System.out.println("📊 PV Vous : " + pvMoi + " | PV Adversaire : " + pvAdversaire);
    }

    /* =========================
       FERMETURE
       ========================= */

    private void fermer() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Erreur fermeture : " + e.getMessage());
        }
    }
}
