package polymorphia;

import java.util.ArrayList;
import java.util.List;

public class Inventaire {
    private int intcoins;
    private List<Arme> armes;
    private List<Armure> armures;
    private List<Potion> potions;
    private List<Spell> sorts;
    private List<Materia> materias;
    private Arme armeEquipee;
    private Armure armureEquipee;
    
    public Inventaire(int intcoinsDepart) {
        this.intcoins = intcoinsDepart;
        this.armes = new ArrayList<>();
        this.armures = new ArrayList<>();
        this.potions = new ArrayList<>();
        this.sorts = new ArrayList<>();
        this.materias = new ArrayList<>();
    }
    
    public int getIntcoins() {
        return intcoins;
    }
    
    public void ajouterIntcoins(int montant) {
        this.intcoins += montant;
    }
    
    public boolean retirerIntcoins(int montant) {
        if (intcoins >= montant) {
            intcoins -= montant;
            return true;
        }
        return false;
    }
    
    public void ajouterArme(Arme arme) {
        armes.add(arme);
        System.out.println(arme.getNom() + " ajoutée à l'inventaire !");
    }
    
    public void ajouterArmure(Armure armure) {
        armures.add(armure);
        System.out.println(armure.getNom() + " ajoutée à l'inventaire !");
    }
    
    public void ajouterPotion(Potion potion) {
        potions.add(potion);
        System.out.println(potion.getNom() + " ajoutée à l'inventaire !");
    }
    
    public void ajouterSort(Spell sort) {
        sorts.add(sort);
        System.out.println(sort.getNom() + " ajouté à l'inventaire !");
    }
    
    public void ajouterMateria(Materia materia) {
        materias.add(materia);
        System.out.println(materia.getNom() + " ajoutée à l'inventaire !");
    }
    
    public void equiperArme(Arme arme) {
        if (armes.contains(arme)) {
            this.armeEquipee = arme;
            System.out.println(arme.getNom() + " équipée !");
        }
    }
    
    public void equiperArmure(Armure armure) {
        if (armures.contains(armure)) {
            this.armureEquipee = armure;
            System.out.println(armure.getNom() + " équipée !");
        }
    }
    
    public Arme getArmeEquipee() {
        return armeEquipee;
    }
    
    public Armure getArmureEquipee() {
        return armureEquipee;
    }
    
    public List<Arme> getArmes() {
        return armes;
    }
    
    public List<Armure> getArmures() {
        return armures;
    }
    
    public List<Potion> getPotions() {
        return potions;
    }
    
    public List<Spell> getSorts() {
        return sorts;
    }
    
    public List<Materia> getMaterias() {
        return materias;
    }
    
    public void afficher() {
        System.out.println("\n===== INVENTAIRE =====");
        System.out.println("Intcoins: " + intcoins);
        
        System.out.println("\n--- Armes (" + armes.size() + ") ---");
        for (int i = 0; i < armes.size(); i++) {
            System.out.println((i+1) + ". " + armes.get(i) + 
                (armes.get(i) == armeEquipee ? " [EQUIPÉE]" : ""));
        }
        
        System.out.println("\n--- Armures (" + armures.size() + ") ---");
        for (int i = 0; i < armures.size(); i++) {
            System.out.println((i+1) + ". " + armures.get(i) + 
                (armures.get(i) == armureEquipee ? " [EQUIPÉE]" : ""));
        }
        
        System.out.println("\n--- Potions (" + potions.size() + ") ---");
        for (int i = 0; i < potions.size(); i++) {
            System.out.println((i+1) + ". " + potions.get(i));
        }
        
        System.out.println("\n--- Sorts (" + sorts.size() + ") ---");
        for (int i = 0; i < sorts.size(); i++) {
            System.out.println((i+1) + ". " + sorts.get(i));
        }
        
        System.out.println("\n--- Materias (" + materias.size() + ") ---");
        for (int i = 0; i < materias.size(); i++) {
            System.out.println((i+1) + ". " + materias.get(i));
        }
    }
}
