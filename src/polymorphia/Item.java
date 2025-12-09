package polymorphia;

public abstract class Item {
	protected String nom;
	protected Rarete rarete;
	protected int prix;
	protected String lore;

	public Item(String nom, Rarete rarete, int prix, String lore) {
	this.nom = nom;
	this.rarete = rarete;
	this.prix = (int)(prix * rarete.getMultiplicateur());
	this.lore = lore;
	}

	public String getNom() {
        return nom;
    }
    
    public Rarete getRarete() {
        return rarete;
    }
    
    public int getPrix() {
        return prix;
    }
    
    public String getLore() {
        return lore;
    }
    
    public abstract void utiliser();
    
    @Override
    public String toString() {
        return nom + " [" + rarete + "] - " + prix + " intcoins";
    }
}
