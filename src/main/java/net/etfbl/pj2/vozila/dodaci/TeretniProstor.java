package net.etfbl.pj2.vozila.dodaci;

import net.etfbl.pj2.osobe.dodaci.Kofer;

import java.io.Serializable;
import java.util.ArrayList;

public class TeretniProstor implements Serializable {
    private ArrayList<Kofer> koferi = new ArrayList<>();
    private static final long serialVersionUID = 1;


    public ArrayList<Kofer> getKoferi() {
        return koferi;
    }

    public void setKoferi(ArrayList<Kofer> koferi) {
        this.koferi = koferi;
    }

    public void dodajKofer(Kofer kofer){
        koferi.add(kofer);
    }
}
