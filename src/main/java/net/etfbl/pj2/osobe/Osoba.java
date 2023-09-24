package net.etfbl.pj2.osobe;

import java.io.Serializable;
import java.util.Random;
import net.etfbl.pj2.osobe.dodaci.*;

public abstract class Osoba implements Serializable {
    protected IdentifikacioniDokument identifikacioniDokument;
    protected Kofer kofer; //ako je null nema ga
    protected String ime;
    private static final long serialVersionUID = 1;

    String[] mogucaImena = {
            "Marko", "Nikola", "Stefan", "Ivan", "Petar", "Aleksandar", "Nenad", "Đorđe", "Vuk", "Andrija",
            "Luka", "Dusan", "Vladimir", "Nemanja", "Nikolaj", "Stanko", "Goran", "Branko", "Milan", "Dragan",
            "Slobodan", "Miroslav", "Simeon", "Momir", "Miomir", "Svetislav", "David", "Vojin", "Miomir", "Zivko",
            "Veselin", "Branimir", "Jagoda", "Milan", "Dejan",
            "Ana", "Milica", "Jovana", "Maja", "Lana", "Milena", "Jelena", "Sara", "Jana", "Tijana",
            "Danijela", "Tamara", "Katarina", "Marina", "Bojana"
    };
    String[] mogucaPrezimena = {"Jovanović", "Ilić", "Petrović", "Nikolić", "Marković", "Đorđević", "Stojanović", "Pavlović", "Simić", "Milosavljević", "Stanković", "Kovačević", "Ristić", "Đukić", "Pantelić", "Gavrilović", "Lukić", "Todorović", "Vuković", "Stevanović", "Milinković", "Nedeljković", "Tomović", "Nenadović", "Janković", "Milanović", "Veselinović", "Nikolajević", "Milošević", "Grujić", "Radosavljević", "Radovanović", "Aleksić", "Jović", "Aleksandrović", "Milić", "Bošković", "Mitić", "Ivanović", "Marinković", "Mladenović", "Obradović", "Vidović", "Pavlović", "Mihajlović", "Vasiljević", "Maksimović", "Pantić", "Tadić", "Gajić"};
    public Osoba(){
        this(false);
    }

    public Osoba(boolean mozeImatiKofer){
        Random random = new Random();
        ime = mogucaImena[random.nextInt(mogucaImena.length)] + " " + mogucaPrezimena[random.nextInt(mogucaPrezimena.length)];
        identifikacioniDokument = new IdentifikacioniDokument();
        if(mozeImatiKofer){
            kofer = random.nextInt(100) < 70 ? new Kofer() : null;
        }
        else {
            kofer = null;
        }
    }

    @Override
    public String toString() {
        return ime + " (Validan dokument: " + !getIdentifikacioniDokument().isNeispravan() + ")";
    }

    public IdentifikacioniDokument getIdentifikacioniDokument() {
        return identifikacioniDokument;
    }

    public Kofer getKofer() {
        return kofer;
    }

    public String getIme() {
        return ime;
    }
}
