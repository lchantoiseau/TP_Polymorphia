package polymorphia;

public class Potion extends Item{
	private int pointsVie;
    private String effet;
    
    public Potion(String nom, Rarete rarete, int pointsVie, String effet) {
        super(nom, rarete, pointsVie * 2, "Restaure " + pointsVie + " points de vie");
        this.pointsVie = pointsVie;
        this.effet = effet;
    }
    
    public int getPointsVie() {
        return pointsVie;
    }
    
    public void soigner(Personnage p) {
        p.restaurerVie(pointsVie);
        System.out.println(p.getNom() + " utilise " + nom + " et récupère " + pointsVie + " PV !");
    }
    
    @Override
    public void utiliser() {
        System.out.println("Utilisation de " + nom);
    }
    
    @Override
    public String toString() {
        return super.toString() + " | Soin: +" + pointsVie + " PV";
    }
}
