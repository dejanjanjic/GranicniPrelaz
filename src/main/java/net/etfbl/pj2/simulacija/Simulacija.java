package net.etfbl.pj2.simulacija;


import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;


import net.etfbl.pj2.incident.Incident;
import net.etfbl.pj2.terminal.CarinskiTerminal;
import net.etfbl.pj2.terminal.PolicijskiTerminal;
import net.etfbl.pj2.vozila.*;

public class Simulacija {
    public static volatile ArrayBlockingQueue<Vozilo> granicniRed = new ArrayBlockingQueue<>(50);
    public static volatile ArrayBlockingQueue<Vozilo> carinskiRed = new ArrayBlockingQueue<>(2);


    public static volatile PolicijskiTerminal p1 = new PolicijskiTerminal();
    public static volatile PolicijskiTerminal p2 = new PolicijskiTerminal();
    public static volatile PolicijskiTerminal pk = new PolicijskiTerminal();

    public static volatile CarinskiTerminal c1 = new CarinskiTerminal();
    public static volatile CarinskiTerminal ck = new CarinskiTerminal();

    public static final int BROJ_LICNIH_VOZILA = 35;
    public static final int BROJ_AUTOBUSA = 10;
    public static final int BROJ_KAMIONA = 5;

    //boolean koji provjerava da li je pauza
    public static volatile boolean pauza = true;
    public static final Object lock = new Object();
    public static final ArrayList<Incident> vozilaSaIncidentima = new ArrayList<>();

}