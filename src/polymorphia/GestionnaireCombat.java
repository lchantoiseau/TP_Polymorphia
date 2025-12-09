package polymorphia;

import java.util.List;

public class GestionnaireCombat {
    
    public static boolean combattreMonstre(Personnage joueur, Monstre monstre) {
        System.out.println("\n*** COMBAT COMMENCE ***");
        System.out.println(monstre);
        ConsoleUI.afficherSeparateur();
        
        while (joueur.estVivant() && monstre.estVivant()) {
            // Tour du joueur
            ConsoleUI.afficherMenuCombat();
            int choix = ConsoleUI.lireChoix();
            
            switch (choix) {
                case 1: // Attaquer
                    joueur.attaquer(monstre);
                    break;
                    
                case 2: // Utiliser sort
                    List<Spell> sorts = joueur.getInventaire().getSorts();
                    if (sorts.isEmpty()) {
                        System.out.println("Vous n'avez aucun sort !");
                        continue;
                    }
                    
                    System.out.println("\n--- Vos sorts ---");
                    for (int i = 0; i < sorts.size(); i++) {
                        System.out.println((i+1) + ". " + sorts.get(i));
                    }
                    System.out.print("Choisir un sort (0 pour annuler): ");
                    int sortChoix = ConsoleUI.lireChoix() - 1;
                    
                    if (sortChoix >= 0 && sortChoix < sorts.size()) {
                        sorts.get(sortChoix).lancer(joueur, monstre);
                        sorts.remove(sortChoix); // Sort utilisé une fois
                    } else {
                        System.out.println("Action annulée");
                        continue;
                    }
                    break;
                    
                case 3: // Utiliser potion
                    List<Potion> potions = joueur.getInventaire().getPotions();
                    if (potions.isEmpty()) {
                        System.out.println("Vous n'avez aucune potion !");
                        continue;
                    }
                    
                    System.out.println("\n--- Vos potions ---");
                    for (int i = 0; i < potions.size(); i++) {
                        System.out.println((i+1) + ". " + potions.get(i));
                    }
                    System.out.print("Choisir une potion (0 pour annuler): ");
                    int potionChoix = ConsoleUI.lireChoix() - 1;
                    
                    if (potionChoix >= 0 && potionChoix < potions.size()) {
                        potions.get(potionChoix).soigner(joueur);
                        potions.remove(potionChoix);
                    } else {
                        System.out.println("Action annulée");
                        continue;
                    }
                    break;
                    
                case 4: // Fuir
                    System.out.println(joueur.getNom() + " prend la fuite !");
                    return false;
                    
                default:
                    System.out.println("Action invalide !");
                    continue;
            }
            
            // Vérifier si le monstre est mort
            if (!monstre.estVivant()) {
                monstre.donnerRecompenses(joueur);
                return true;
            }
            
            // Tour du monstre
            System.out.println();
            monstre.attaquer(joueur);
            
            // Vérifier si le joueur est mort
            if (!joueur.estVivant()) {
                System.out.println("\n*** VOUS ÊTES MORT ! ***");
                System.out.println("Game Over...");
                return false;
            }
            
            ConsoleUI.afficherSeparateur();
        }
        
        return joueur.estVivant();
    }
    
    public static boolean combattrePvP(Personnage joueur1, Personnage joueur2) {
        System.out.println("\n*** COMBAT PVP COMMENCE ***");
        System.out.println(joueur1.getNom() + " VS " + joueur2.getNom());
        ConsoleUI.afficherSeparateur();
        
        Personnage attaquant = joueur1;
        Personnage defenseur = joueur2;
        
        while (joueur1.estVivant() && joueur2.estVivant()) {
            System.out.println("\n--- Tour de " + attaquant.getNom() + " ---");
            attaquant.afficherStats();
            
            System.out.println("\n1. Attaquer");
            System.out.println("2. Utiliser un sort");
            System.out.println("3. Utiliser une potion");
            System.out.print("Votre action: ");
            
            int choix = ConsoleUI.lireChoix();
            
            switch (choix) {
                case 1:
                    // Attaque PvP
                    int degats = Math.max(1, attaquant.getAttaque() - defenseur.getDefense());
                    defenseur.recevoirDegats(degats);
                    System.out.println(attaquant.getNom() + " attaque " + defenseur.getNom() + 
                                     " et inflige " + degats + " dégâts !");
                    break;
                    
                case 2:
                    List<Spell> sorts = attaquant.getInventaire().getSorts();
                    if (!sorts.isEmpty()) {
                        System.out.println("Sort utilisé ! (simplifié pour PvP)");
                        int degatsSort = sorts.get(0).getPuissance() + attaquant.getAttaque();
                        defenseur.recevoirDegats(degatsSort);
                        sorts.remove(0);
                    } else {
                        System.out.println("Aucun sort disponible !");
                        continue;
                    }
                    break;
                    
                case 3:
                    List<Potion> potions = attaquant.getInventaire().getPotions();
                    if (!potions.isEmpty()) {
                        potions.get(0).soigner(attaquant);
                        potions.remove(0);
                    } else {
                        System.out.println("Aucune potion disponible !");
                        continue;
                    }
                    break;
                    
                default:
                    System.out.println("Action invalide !");
                    continue;
            }
            
            // Vérifier victoire
            if (!defenseur.estVivant()) {
                System.out.println("\n*** " + attaquant.getNom() + " REMPORTE LE COMBAT ! ***");
                return attaquant == joueur1;
            }
            
            // Changer de tour
            Personnage temp = attaquant;
            attaquant = defenseur;
            defenseur = temp;
            
            ConsoleUI.afficherSeparateur();
            ConsoleUI.pause();
        }
        
        return joueur1.estVivant();
    }
}
