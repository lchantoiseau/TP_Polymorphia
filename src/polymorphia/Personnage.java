package polymorphia;

public class Personnage {
    private String nom;
    private int niveau;
    private int pointsVie;
    private int pointsVieMax;
    private int attaque;
    private int defense;
    private int experience;
    private Inventaire inventaire;
    
    public Personnage(String nom) {
        this.nom = nom;
        this.niveau = 1;
        this.pointsVieMax = 100;
        this.pointsVie = pointsVieMax;
        this.attaque = 10;
        this.defense = 5;
        this.experience = 0;
        this.inventaire = new Inventaire(100);
    }
    
    public String getNom() {
        return nom;
    }
    
    public int getNiveau() {
        return niveau;
    }
    
    public int getPointsVie() {
        return pointsVie;
    }
    
    public int getPointsVieMax() {
        return pointsVieMax;
    }
    
    public void setPointsVie(int pv) {
        this.pointsVie = Math.max(0, Math.min(pv, pointsVieMax));
    }

    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setPointsVieMax(int pvMax) {
        this.pointsVieMax = pvMax;
        if (pointsVie > pointsVieMax) {
            pointsVie = pointsVieMax;
        }
    }

    
    public int getAttaque() {
        int attaqueBase = attaque;
        if (inventaire.getArmeEquipee() != null) {
            attaqueBase += inventaire.getArmeEquipee().getStatAttaque();
        }
        return attaqueBase;
    }
    
    public int getDefense() {
        int defenseBase = defense;
        if (inventaire.getArmureEquipee() != null) {
            defenseBase += inventaire.getArmureEquipee().getStatDefense();
        }
        return defenseBase;
    }
    
    public Inventaire getInventaire() {
        return inventaire;
    }
    
    public void attaquer(Monstre cible) {
        int degats = Math.max(1, getAttaque() - cible.getDefense());
        cible.recevoirDegats(degats);
        System.out.println(nom + " attaque " + cible.getNom() + " et inflige " + degats + " dégâts !");
    }
    
    public void recevoirDegats(int degats) {
        int degatsReels = Math.max(1, degats - getDefense());
        pointsVie -= degatsReels;
        if (pointsVie < 0) pointsVie = 0;
        System.out.println(nom + " subit " + degatsReels + " dégâts ! PV: " + pointsVie + "/" + pointsVieMax);
    }
    
    public void restaurerVie(int montant) {
        pointsVie += montant;
        if (pointsVie > pointsVieMax) pointsVie = pointsVieMax;
    }
    
    public void gagnerExperience(int xp) {
        experience += xp;
        System.out.println(nom + " gagne " + xp + " XP !");
        
        if (experience >= niveau * 100) {
            monterNiveau();
        }
    }
    
    public void monterNiveau() {
        niveau++;
        pointsVieMax += 20;
        pointsVie = pointsVieMax;
        attaque += 5;
        defense += 3;
        System.out.println("\n*** " + nom + " monte au niveau " + niveau + " ! ***");
        System.out.println("PV Max: " + pointsVieMax + " | Attaque: " + attaque + " | Défense: " + defense);
    }
    
    public boolean estVivant() {
        return pointsVie > 0;
    }
    
    public void afficherStats() {
        System.out.println("\n===== " + nom + " =====");
        System.out.println("Niveau: " + niveau + " | XP: " + experience + "/" + (niveau * 100));
        System.out.println("PV: " + pointsVie + "/" + pointsVieMax);
        System.out.println("Attaque: " + getAttaque() + " (Base: " + attaque + ")");
        System.out.println("Défense: " + getDefense() + " (Base: " + defense + ")");
    }
}
