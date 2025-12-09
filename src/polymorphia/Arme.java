package polymorphia;

public class Arme extends Item{
	private int statAttaque;
    private int niveau;
    
    public Arme(String nom, Rarete rarete, int statAttaque, String lore) {
        super(nom, rarete, 4 * statAttaque, lore);
        this.statAttaque = statAttaque;
        this.niveau = 1;
    }
    
    public int getStatAttaque() {
        return statAttaque;
    }
    
    public int getNiveau() {
        return niveau;
    }
    
    public void ameliorer(int bonus) {
        this.statAttaque += bonus;
        this.niveau++;
        System.out.println(nom + " a été améliorée ! Attaque: " + statAttaque);
    }
    
    @Override
    public void utiliser() {
        System.out.println("Vous équipez " + nom);
    }
    
    @Override
    public String toString() {
        return super.toString() + " | Attaque: " + statAttaque + " (Niv." + niveau + ")";
    }
}
