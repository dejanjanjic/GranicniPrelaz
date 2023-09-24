package net.etfbl.pj2.terminal;

import net.etfbl.pj2.vozila.Vozilo;

public abstract class Terminal {
    protected volatile boolean slobodan = true;
    protected volatile boolean radi = true;



    public abstract boolean obradiVozilo(Vozilo vozilo);

    public boolean isSlobodan() {
        return slobodan;
    }

    public void setSlobodan(boolean slobodan) {
        this.slobodan = slobodan;
    }

    public boolean isRadi() {
        return radi;
    }

    public void setRadi(boolean radi) {
        this.radi = radi;
    }
}
