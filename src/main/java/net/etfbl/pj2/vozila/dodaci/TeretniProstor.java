package net.etfbl.pj2.vozila.dodaci;

import net.etfbl.pj2.osobe.dodaci.Kofer;

import java.util.ArrayList;

public class TeretniProstor {
    private ArrayList<Kofer> koferi = new ArrayList<>();

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
