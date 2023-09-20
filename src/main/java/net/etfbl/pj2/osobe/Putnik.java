package net.etfbl.pj2.osobe;

import net.etfbl.pj2.osobe.Osoba;

import java.util.Random;

public class Putnik extends Osoba {

    public Putnik(){
        this(false);
    }

    public Putnik(boolean mozeImatiKofer){
        super(mozeImatiKofer);
    }
}
