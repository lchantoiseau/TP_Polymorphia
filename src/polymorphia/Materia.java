package polymorphia;

public class Materia extends Item{
private int bonusStats;
    
    public Materia(String nom, Rarete rarete, int bonusStats) {
        super(nom, rarete, bonusStats * 20, "Améliore les armes et armures");
        this.bonusStats = bonusStats;
    }
    
    public int getBonusStats() {
        return bonusStats;
    }
    
    public void ameliorerArme(Arme arme) {
        arme.ameliorer(bonusStats);
        System.out.println("Materia utilisée sur " + arme.getNom() + " !");
    }
    
    public void ameliorerArmure(Armure armure) {
        armure.ameliorer(bonusStats);
        System.out.println("Materia utilisée sur " + armure.getNom() + " !");
    }
    
    @Override
    public void utiliser() {
        System.out.println("Materia " + nom + " prête à améliorer un objet (+"+bonusStats+" stats)");
    }
    
    @Override
    public String toString() {
        return super.toString() + " | Bonus: +" + bonusStats;
    }
}
