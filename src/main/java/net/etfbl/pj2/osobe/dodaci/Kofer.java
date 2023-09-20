package net.etfbl.pj2.osobe.dodaci;

import java.io.Serializable;
import java.util.Random;

public class Kofer implements Serializable {
    private boolean imaNedozvoljeneStvari;

    public Kofer(){
        imaNedozvoljeneStvari = new Random().nextInt(100) < 10;
    }

    public boolean isImaNedozvoljeneStvari() {
        return imaNedozvoljeneStvari;
    }
}
