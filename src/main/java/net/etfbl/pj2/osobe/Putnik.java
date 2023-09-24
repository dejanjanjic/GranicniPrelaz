package net.etfbl.pj2.osobe;


public class Putnik extends Osoba {
    public boolean mozeImatiKofer;
    public Putnik(){
        this(false);
    }

    public Putnik(boolean mozeImatiKofer){
        super(mozeImatiKofer);
        this.mozeImatiKofer = mozeImatiKofer;
    }

    @Override
    public String toString() {
        String putnik = ime + "{\nValidan dokument: " + !getIdentifikacioniDokument().isNeispravan();
        if(mozeImatiKofer){
            putnik += "\nIma kofer: " + (kofer == null ? "nema" : "ima");
        }
        putnik += "\n}";
        return putnik;
    }
}
