package polymorphia;

public enum Rarete{
	COMMUN(1.0),
    RARE(1.5),
    EPIQUE(2.0),
    LEGENDAIRE(3.0);
    
    private final double multiplicateur;
    
    Rarete(double multiplicateur) {
        this.multiplicateur = multiplicateur;
    }
    
    public double getMultiplicateur() {
        return multiplicateur;
    }
}
