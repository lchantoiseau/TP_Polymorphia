package polymorphia;

public class Spell extends Item{
	private int puissance;
    private String effet;
    private String description;
    
    public Spell(String nom, Rarete rarete, int puissance, String effet, String description) {
        super(nom, rarete, puissance * 15, description);
        this.puissance = puissance;
        this.effet = effet;
        this.description = description;
    }
    
    public int getPuissance() {
        return puissance;
    }
    
    public String getEffet() {
        return effet;
    }
    
    public void lancer(Personnage lanceur, Monstre cible) {
        int degats = puissance + (lanceur.getAttaque() / 2);
        cible.recevoirDegats(degats);
        System.out.println(lanceur.getNom() + " lance " + nom + " et inflige " + degats + " dégâts !");
    }
    
    @Override
    public void utiliser() {
        System.out.println("Sort " + nom + " prêt à être lancé !");
    }
    
    @Override
    public String toString() {
        return super.toString() + " | Puissance: " + puissance + " | " + effet;
    }
}
