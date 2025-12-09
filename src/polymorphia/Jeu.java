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
        System.out.println("Votre quête commence maintenant...");
        ConsoleUI.pause();
        
        bouclePrincipale();
    }
    
    private void bouclePrincipale() {
        while (enCours && joueur.estVivant()) {
            ConsoleUI.effacerEcran();
            ConsoleUI.afficherMenuPrincipal();
            
            int choix = ConsoleUI.lireChoix();
            
            switch (choix) {
                case 1:
                    commercer();
                    break;
                case 2:
                    explorer();
                    break;
                case 3:
                    joueur.getInventaire().afficher();
                    ConsoleUI.pause();
                    break;
                case 4:
                    equiperObjet();
                    break;
                case 5:
                    utiliserMateria();
                    break;
                case 6:
                    utiliserPotion();
                    break;
                case 7:
                    joueur.afficherStats();
                    ConsoleUI.pause();
                    break;
                case 8:
                    modeMultijoueur();
                    break;
                case 0:
                    System.out.println("\nMerci d'avoir joué à Polymorphia !");
                    enCours = false;
                    break;
                default:
                    System.out.println("Choix invalide !");
            }
        }
        
        if (!joueur.estVivant()) {
            System.out.println("\n*** GAME OVER ***");
            System.out.println("Votre aventure s'arrête ici...");
        }
    }
    
    private void commercer() {
        boolean shopping = true;
        
        while (shopping) {
            ConsoleUI.effacerEcran();
            System.out.println("Intcoins disponibles: " + joueur.getInventaire().getIntcoins());
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
        System.out.println("\n" + joueur.getNom() + " explore les terres de Polymorphia...");
        ConsoleUI.pause();
        
        Monstre monstre = Bestiaire.genererMonstre(joueur.getNiveau());
        System.out.println("\n*** Un " + monstre.getNom() + " apparaît ! ***");
        System.out.println(monstre);
        ConsoleUI.pause();
        
        boolean victoire = GestionnaireCombat.combattreMonstre(joueur, monstre);
        
        if (victoire) {
            System.out.println("\nVictoire ! Vous pouvez continuer votre aventure.");
        }
        
        ConsoleUI.pause();
    }
    
    private void equiperObjet() {
        System.out.println("\n=== ÉQUIPEMENT ===");
        System.out.println("1. Équiper une arme");
        System.out.println("2. Équiper une armure");
        System.out.println("0. Retour");
        System.out.print("Choix: ");
        
        int choix = ConsoleUI.lireChoix();
        
        if (choix == 1) {
            List<Arme> armes = joueur.getInventaire().getArmes();
            if (armes.isEmpty()) {
                System.out.println("Vous n'avez aucune arme !");
            } else {
                System.out.println("\n--- Vos armes ---");
                for (int i = 0; i < armes.size(); i++) {
                    System.out.println((i+1) + ". " + armes.get(i));
                }
                System.out.print("Choisir une arme: ");
                int index = ConsoleUI.lireChoix() - 1;
                if (index >= 0 && index < armes.size()) {
                    joueur.getInventaire().equiperArme(armes.get(index));
                }
            }
        } else if (choix == 2) {
            List<Armure> armures = joueur.getInventaire().getArmures();
            if (armures.isEmpty()) {
                System.out.println("Vous n'avez aucune armure !");
            } else {
                System.out.println("\n--- Vos armures ---");
                for (int i = 0; i < armures.size(); i++) {
                    System.out.println((i+1) + ". " + armures.get(i));
                }
                System.out.print("Choisir une armure: ");
                int index = ConsoleUI.lireChoix() - 1;
                if (index >= 0 && index < armures.size()) {
                    joueur.getInventaire().equiperArmure(armures.get(index));
                }
            }
        }
        
        ConsoleUI.pause();
    }
    
    private void utiliserMateria() {
        List<Materia> materias = joueur.getInventaire().getMaterias();
        if (materias.isEmpty()) {
            System.out.println("Vous n'avez aucune materia !");
            ConsoleUI.pause();
            return;
        }
        
        System.out.println("\n--- Vos Materias ---");
        for (int i = 0; i < materias.size(); i++) {
            System.out.println((i+1) + ". " + materias.get(i));
        }
        System.out.print("Choisir une materia (0 pour annuler): ");
        int materiaIndex = ConsoleUI.lireChoix() - 1;
        
        if (materiaIndex < 0 || materiaIndex >= materias.size()) {
            return;
        }
        
        System.out.println("\nAméliorer:");
        System.out.println("1. Une arme");
        System.out.println("2. Une armure");
        System.out.print("Choix: ");
        int typeChoix = ConsoleUI.lireChoix();
        
        Materia materia = materias.get(materiaIndex);
        
        if (typeChoix == 1) {
            List<Arme> armes = joueur.getInventaire().getArmes();
            if (!armes.isEmpty()) {
                System.out.println("\n--- Vos armes ---");
                for (int i = 0; i < armes.size(); i++) {
                    System.out.println((i+1) + ". " + armes.get(i));
                }
                System.out.print("Choisir une arme: ");
                int armeIndex = ConsoleUI.lireChoix() - 1;
                if (armeIndex >= 0 && armeIndex < armes.size()) {
                    materia.ameliorerArme(armes.get(armeIndex));
                    materias.remove(materiaIndex);
                }
            }
        } else if (typeChoix == 2) {
            List<Armure> armures = joueur.getInventaire().getArmures();
            if (!armures.isEmpty()) {
                System.out.println("\n--- Vos armures ---");
                for (int i = 0; i < armures.size(); i++) {
                    System.out.println((i+1) + ". " + armures.get(i));
                }
                System.out.print("Choisir une armure: ");
                int armureIndex = ConsoleUI.lireChoix() - 1;
                if (armureIndex >= 0 && armureIndex < armures.size()) {
                    materia.ameliorerArmure(armures.get(armureIndex));
                    materias.remove(materiaIndex);
                }
            }
        }
        
        ConsoleUI.pause();
    }
    
    private void utiliserPotion() {
        List<Potion> potions = joueur.getInventaire().getPotions();
        if (potions.isEmpty()) {
            System.out.println("Vous n'avez aucune potion !");
            ConsoleUI.pause();
            return;
        }
        
        System.out.println("\n--- Vos potions ---");
        for (int i = 0; i < potions.size(); i++) {
            System.out.println((i+1) + ". " + potions.get(i));
        }
        System.out.print("Choisir une potion (0 pour annuler): ");
        int index = ConsoleUI.lireChoix() - 1;
        
        if (index >= 0 && index < potions.size()) {
            potions.get(index).soigner(joueur);
            potions.remove(index);
        }
        
        ConsoleUI.pause();
    }
    
    private void modeMultijoueur() {
        ConsoleUI.afficherMenuMultijoueur();
        int choix = ConsoleUI.lireChoix();
        
        switch (choix) {
            case 1:
                System.out.println("\nCréation du serveur...");
                ServeurMultijoueur serveur = new ServeurMultijoueur(joueur);
                serveur.demarrer();
                break;
                
            case 2:
                String host = ConsoleUI.lireTexte("Adresse IP du serveur (localhost par défaut): ");
                if (host.isEmpty()) host = "localhost";
                ClientMultijoueur client = new ClientMultijoueur(joueur);
                client.connecter(host);
                break;
                
            case 0:
                return;
        }
        
        ConsoleUI.pause();
    }
}
