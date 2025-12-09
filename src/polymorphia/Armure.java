package polymorphia;

public class Armure extends Item {
	private int statDefense;
    private int niveau;
    
    public Armure(String nom, Rarete rarete, int statDefense, String lore) {
        super(nom, rarete, 4 * statDefense, lore);
        this.statDefense = statDefense;
        this.niveau = 1;
    }
    
    public int getStatDefense() {
        return statDefense;
    }
    
    public int getNiveau() {
        return niveau;
    }
    
    public void ameliorer(int bonus) {
        this.statDefense += bonus;
        this.niveau++;
        System.out.println(nom + " a été améliorée ! Défense: " + statDefense);
    }
    
    @Override
    public void utiliser() {
        System.out.println("Vous équipez " + nom);
    }
    
    @Override
    public String toString() {
        return super.toString() + " | Défense: " + statDefense + " (Niv." + niveau + ")";
    }
}