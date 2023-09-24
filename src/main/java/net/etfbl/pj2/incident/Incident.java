package net.etfbl.pj2.incident;

import net.etfbl.pj2.vozila.Vozilo;

import java.io.Serializable;
import java.util.ArrayList;

public class Incident implements Serializable {
    private int idVozila;
    private String opisProblema;
    private static final long serialVersionUID = 1;
    public Incident(int idVozila, String opisProblema){
        this.idVozila = idVozila;
        this.opisProblema = opisProblema;
    }

    public int getIdVozila() {
        return idVozila;
    }

    public void setIdVozila(int idVozila) {
        this.idVozila = idVozila;
    }

    public String getOpisProblema() {
        return opisProblema;
    }

    public void setOpisProblema(String opisProblema) {
        this.opisProblema = opisProblema;
    }

}
