package polymorphia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Monstre {
    private String nom;
    private int niveau;
    private int pointsVie;
    private int pointsVieMax;
    private int attaque;
    private int defense;
    private int xpRecompense;
    private int intcoinsRecompense;
    private List<Item> dropsPossibles;
    private static Random random = new Random();
    
    public Monstre(String nom, int niveau, int pv, int attaque, int defense) {
        this.nom = nom;
        this.niveau = niveau;
        this.pointsVieMax = pv;
        this.pointsVie = pv;
        this.attaque = attaque;
        this.defense = defense;
        this.xpRecompense = niveau * 50;
        this.intcoinsRecompense = niveau * 20;
        this.dropsPossibles = new ArrayList<>();
    }
    
    public String getNom() {
        return nom;
    }
    
    public int getPointsVie() {
        return pointsVie;
    }
    
    public int getAttaque() {
        return attaque;
    }
    
    public int getDefense() {
        return defense;
    }
    
    public void ajouterDrop(Item item) {
        dropsPossibles.add(item);
    }
    
    public void attaquer(Personnage cible) {
        int degats = Math.max(1, attaque - cible.getDefense());
        cible.recevoirDegats(degats);
        System.out.println(nom + " attaque " + cible.getNom() + " et inflige " + degats + " dégâts !");
    }
    
    public void recevoirDegats(int degats) {
        pointsVie -= degats;
        if (pointsVie < 0) pointsVie = 0;
        System.out.println(nom + " subit " + degats + " dégâts ! PV: " + pointsVie + "/" + pointsVieMax);
    }
    
    public boolean estVivant() {
        return pointsVie > 0;
    }
    
    public void donnerRecompenses(Personnage joueur) {
        System.out.println("\n" + nom + " est vaincu !");
        
        joueur.gagnerExperience(xpRecompense);
        joueur.getInventaire().ajouterIntcoins(intcoinsRecompense);
        System.out.println("+" + intcoinsRecompense + " intcoins !");
        
        // Chance de drop (30%)
        if (!dropsPossibles.isEmpty() && random.nextInt(100) < 30) {
            Item drop = dropsPossibles.get(random.nextInt(dropsPossibles.size()));
            System.out.println("*** " + nom + " a lâché: " + drop.getNom() + " ! ***");
            
            if (drop instanceof Spell) {
                joueur.getInventaire().ajouterSort((Spell)drop);
            } else if (drop instanceof Materia) {
                joueur.getInventaire().ajouterMateria((Materia)drop);
            }
        }
    }
    
    @Override
    public String toString() {
        return nom + " [Niv." + niveau + "] - PV: " + pointsVie + "/" + pointsVieMax + 
               " | ATQ: " + attaque + " | DEF: " + defense;
    }
}
