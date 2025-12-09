package polymorphia;

import java.util.ArrayList;
import java.util.List;

public class Marchand {
    private String nom;
    private List<Item> stock;
    
    public Marchand(String nom) {
        this.nom = nom;
        this.stock = new ArrayList<>();
        initialiserStock();
    }
    
    private void initialiserStock() {
        // Armes
        stock.add(new Arme("Épée rouillée", Rarete.COMMUN, 5, "Une vieille épée"));
        stock.add(new Arme("Hache de guerre", Rarete.RARE, 15, "Hache puissante"));
        stock.add(new Arme("Arc elfique", Rarete.EPIQUE, 25, "Arc des elfes"));
        
        // Armures
        stock.add(new Armure("Armure de cuir", Rarete.COMMUN, 5, "Protection basique"));
        stock.add(new Armure("Cotte de mailles", Rarete.RARE, 12, "Armure solide"));
        
        // Potions
        stock.add(new Potion("Petite potion", Rarete.COMMUN, 30, "Soin léger"));
        stock.add(new Potion("Grande potion", Rarete.RARE, 60, "Soin puissant"));
        
        // Sorts
        stock.add(new Spell("Boule de feu", Rarete.RARE, 20, "Dégâts de feu", "Brûle l'ennemi"));
        
        // Materia
        stock.add(new Materia("Materia mineure", Rarete.COMMUN, 3));
        stock.add(new Materia("Materia majeure", Rarete.RARE, 7));
    }
    
    public void afficherStock() {
        System.out.println("\n===== Boutique de " + nom + " =====");
        for (int i = 0; i < stock.size(); i++) {
            System.out.println((i+1) + ". " + stock.get(i));
        }
        System.out.println("0. Quitter");
    }
    
    public boolean vendre(int index, Personnage acheteur) {
        if (index < 0 || index >= stock.size()) {
            System.out.println("Article invalide !");
            return false;
        }
        
        Item item = stock.get(index);
        
        if (acheteur.getInventaire().retirerIntcoins(item.getPrix())) {
            System.out.println("Vous achetez " + item.getNom() + " pour " + item.getPrix() + " intcoins");
            
            if (item instanceof Arme) {
                acheteur.getInventaire().ajouterArme((Arme)item);
            } else if (item instanceof Armure) {
                acheteur.getInventaire().ajouterArmure((Armure)item);
            } else if (item instanceof Potion) {
                acheteur.getInventaire().ajouterPotion((Potion)item);
            } else if (item instanceof Spell) {
                acheteur.getInventaire().ajouterSort((Spell)item);
            } else if (item instanceof Materia) {
                acheteur.getInventaire().ajouterMateria((Materia)item);
            }
            
            return true;
        } else {
            System.out.println("Vous n'avez pas assez d'intcoins !");
            return false;
        }
    }
    
    public List<Item> getStock() {
        return stock;
    }
}
