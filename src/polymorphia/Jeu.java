package polymorphia;

import java.util.List;

public class Jeu {

    private Personnage joueur;
    private Marchand marchand;
    private boolean enCours;

    public Jeu() {
        this.marchand = new Marchand("Grimarr le Marchand");
        this.enCours = true;
    }

    public void demarrer() {
        ConsoleUI.afficherBanniere();

        String nom = ConsoleUI.lireTexte("Entrez le nom de votre héros: ");
        joueur = new Personnage(nom);

        System.out.println("\nBienvenue " + nom + " dans le monde de Polymorphia !");
        ConsoleUI.pause();

        bouclePrincipale();
    }

    private void bouclePrincipale() {
        while (enCours && joueur.estVivant()) {
            ConsoleUI.effacerEcran();
            ConsoleUI.afficherMenuPrincipal();

            int choix = ConsoleUI.lireChoix();

            switch (choix) {
                case 1 -> commercer();
                case 2 -> explorer();
                case 3 -> {
                    joueur.getInventaire().afficher();
                    ConsoleUI.pause();
                }
                case 4 -> equiperObjet();
                case 5 -> utiliserMateria();
                case 6 -> utiliserPotion();
                case 7 -> {
                    joueur.afficherStats();
                    ConsoleUI.pause();
                }
                case 8 -> modeMultijoueur();
                case 0 -> quitterJeu();
                default -> System.out.println("Choix invalide !");
            }
        }

        if (!joueur.estVivant()) {
            System.out.println("\n*** GAME OVER ***");
        }
    }

    private void quitterJeu() {
        System.out.println("\nMerci d'avoir joué à Polymorphia !");
        enCours = false;
    }

    /* =========================
       JEU SOLO
       ========================= */

    private void commercer() {
        boolean shopping = true;

        while (shopping) {
            ConsoleUI.effacerEcran();
            System.out.println("Intcoins : " + joueur.getInventaire().getIntcoins());
            marchand.afficherStock();

            System.out.print("\nChoisir un article (0 pour quitter): ");
            int choix = ConsoleUI.lireChoix() - 1;

            if (choix == -1) {
                shopping = false;
            } else {
                marchand.vendre(choix, joueur);
                ConsoleUI.pause();
            }
        }
    }

    private void explorer() {
        ConsoleUI.effacerEcran();
        System.out.println(joueur.getNom() + " explore Polymorphia...");
        ConsoleUI.pause();

        Monstre monstre = Bestiaire.genererMonstre(joueur.getNiveau());
        System.out.println("\n*** Un " + monstre.getNom() + " apparaît ! ***");
        ConsoleUI.pause();

        boolean victoire = GestionnaireCombat.combattreMonstre(joueur, monstre);

        if (victoire) {
            System.out.println("\nVictoire !");
        }

        ConsoleUI.pause();
    }

    private void equiperObjet() {
        ConsoleUI.effacerEcran();
        System.out.println("=== ÉQUIPEMENT ===");
        System.out.println("1. Arme");
        System.out.println("2. Armure");
        System.out.println("0. Retour");

        int choix = ConsoleUI.lireChoix();

        if (choix == 1) equiperArme();
        else if (choix == 2) equiperArmure();

        ConsoleUI.pause();
    }

    private void equiperArme() {
        List<Arme> armes = joueur.getInventaire().getArmes();
        if (armes.isEmpty()) {
            System.out.println("Aucune arme !");
            return;
        }

        for (int i = 0; i < armes.size(); i++) {
            System.out.println((i + 1) + ". " + armes.get(i));
        }

        int index = ConsoleUI.lireChoix() - 1;
        if (index >= 0 && index < armes.size()) {
            joueur.getInventaire().equiperArme(armes.get(index));
        }
    }

    private void equiperArmure() {
        List<Armure> armures = joueur.getInventaire().getArmures();
        if (armures.isEmpty()) {
            System.out.println("Aucune armure !");
            return;
        }

        for (int i = 0; i < armures.size(); i++) {
            System.out.println((i + 1) + ". " + armures.get(i));
        }

        int index = ConsoleUI.lireChoix() - 1;
        if (index >= 0 && index < armures.size()) {
            joueur.getInventaire().equiperArmure(armures.get(index));
        }
    }

    private void utiliserMateria() {
        List<Materia> materias = joueur.getInventaire().getMaterias();
        if (materias.isEmpty()) {
            System.out.println("Aucune materia !");
            ConsoleUI.pause();
            return;
        }

        for (int i = 0; i < materias.size(); i++) {
            System.out.println((i + 1) + ". " + materias.get(i));
        }

        int index = ConsoleUI.lireChoix() - 1;
        if (index < 0 || index >= materias.size()) return;

        Materia materia = materias.get(index);

        System.out.println("1. Arme | 2. Armure");
        int choix = ConsoleUI.lireChoix();

        if (choix == 1) equiperMateriaArme(materia);
        else if (choix == 2) equiperMateriaArmure(materia);

        materias.remove(index);
        ConsoleUI.pause();
    }

    private void equiperMateriaArme(Materia materia) {
        List<Arme> armes = joueur.getInventaire().getArmes();
        if (!armes.isEmpty()) {
            int index = ConsoleUI.lireChoix() - 1;
            if (index >= 0 && index < armes.size()) {
                materia.ameliorerArme(armes.get(index));
            }
        }
    }

    private void equiperMateriaArmure(Materia materia) {
        List<Armure> armures = joueur.getInventaire().getArmures();
        if (!armures.isEmpty()) {
            int index = ConsoleUI.lireChoix() - 1;
            if (index >= 0 && index < armures.size()) {
                materia.ameliorerArmure(armures.get(index));
            }
        }
    }

    private void utiliserPotion() {
        List<Potion> potions = joueur.getInventaire().getPotions();
        if (potions.isEmpty()) {
            System.out.println("Aucune potion !");
            ConsoleUI.pause();
            return;
        }

        for (int i = 0; i < potions.size(); i++) {
            System.out.println((i + 1) + ". " + potions.get(i));
        }

        int index = ConsoleUI.lireChoix() - 1;
        if (index >= 0 && index < potions.size()) {
            potions.get(index).soigner(joueur);
            potions.remove(index);
        }

        ConsoleUI.pause();
    }

    /* =========================
       MULTIJOUEUR
       ========================= */

    private void modeMultijoueur() {
        ConsoleUI.afficherMenuMultijoueur();
        int choix = ConsoleUI.lireChoix();

        switch (choix) {
            case 1 -> {
                System.out.println("\nDémarrage du serveur PvP...");
                new ServeurMultijoueur(joueur).demarrer();
            }
            case 2 -> {
                String host = ConsoleUI.lireTexte("IP du serveur (localhost): ");
                if (host.isBlank()) host = "localhost";
                new ClientMultijoueur().connecter(host);
            }
            case 0 -> {}
        }
    }
}
