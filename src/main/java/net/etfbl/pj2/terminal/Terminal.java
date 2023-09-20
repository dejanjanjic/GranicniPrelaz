package net.etfbl.pj2.terminal;

import net.etfbl.pj2.vozila.Vozilo;

public abstract class Terminal {

    protected boolean moguLicnaVozila;
    protected boolean moguAutobusi;
    protected boolean moguKamioni;
    protected volatile boolean slobodan = true;

    public Terminal(){
        moguAutobusi = false;
        moguKamioni = false;
        moguLicnaVozila = false;
    }

    public Terminal(boolean moguAutobusi, boolean moguKamioni, boolean moguLicnaVozila){
        this.moguLicnaVozila = moguLicnaVozila;
        this.moguAutobusi = moguAutobusi;
        this.moguKamioni = moguKamioni;
    }

    public abstract boolean obradiVozilo(Vozilo vozilo);

    public boolean isMoguLicnaVozila() {
        return moguLicnaVozila;
    }

    public boolean isMoguAutobusi() {
        return moguAutobusi;
    }

    public boolean isMoguKamioni() {
        return moguKamioni;
    }

    public boolean isSlobodan() {
        return slobodan;
    }

    public void setSlobodan(boolean slobodan) {
        this.slobodan = slobodan;
    }


}
