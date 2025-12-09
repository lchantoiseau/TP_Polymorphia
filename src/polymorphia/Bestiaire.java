package polymorphia;

import java.util.Random;

public class Bestiaire {
    private static Random random = new Random();
    
    public static Monstre genererMonstre(int niveauJoueur) {
        int type = random.nextInt(5);
        Monstre monstre;
        
        switch(type) {
            case 0:
                monstre = new Monstre("Loup sauvage", niveauJoueur, 40 + niveauJoueur * 10, 
                                     8 + niveauJoueur * 2, 3 + niveauJoueur);
                break;
            case 1:
                monstre = new Monstre("Zombie pourri", niveauJoueur, 60 + niveauJoueur * 15, 
                                     6 + niveauJoueur * 2, 2 + niveauJoueur);
                break;
            case 2:
                monstre = new Monstre("Gobelin", niveauJoueur, 35 + niveauJoueur * 8, 
                                     10 + niveauJoueur * 3, 2 + niveauJoueur);
                break;
            case 3:
                monstre = new Monstre("Orc guerrier", niveauJoueur, 80 + niveauJoueur * 20, 
                                     12 + niveauJoueur * 3, 5 + niveauJoueur * 2);
                break;
            default:
                monstre = new Monstre("Dragon", niveauJoueur, 150 + niveauJoueur * 30, 
                                     15 + niveauJoueur * 4, 8 + niveauJoueur * 2);
                break;
        }
        
        // Ajouter des drops possibles
        if (random.nextInt(100) < 20) {
            monstre.ajouterDrop(new Spell("Éclair", Rarete.EPIQUE, 30, "Dégâts électriques", "Foudroie l'ennemi"));
        }
        if (random.nextInt(100) < 30) {
            monstre.ajouterDrop(new Materia("Materia rare", Rarete.EPIQUE, 10));
        }
        
        return monstre;
    }
}
