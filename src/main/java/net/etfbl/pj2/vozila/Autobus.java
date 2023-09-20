package net.etfbl.pj2.vozila;

import java.util.Random;

import net.etfbl.pj2.Main;
import net.etfbl.pj2.osobe.dodaci.Kofer;
import net.etfbl.pj2.simulacija.Simulacija;
import net.etfbl.pj2.vozila.dodaci.TeretniProstor;
import net.etfbl.pj2.osobe.*;
import net.etfbl.pj2.vozila.interfejsi.AutobusInterfejs;

public class Autobus extends Vozilo implements AutobusInterfejs {
    private static final int KAPACITET_PUTNIKA = 51;
    private TeretniProstor teretniProstor = new TeretniProstor();
    public Autobus() {
        super(Vozilo.generisiBrojPutnika(KAPACITET_PUTNIKA), true);
        for (Putnik putnik :
                putnici) {
            Kofer kofer = putnik.getKofer();
            if (kofer != null) {
                teretniProstor.dodajKofer(kofer);
            }
        }
        vrijemeObradePutnika = 100;
    }

    @Override
    public void run() {
        boolean mozeProciPolicijskiTerminal = false;
        boolean mozeProciCarinskiTerminal = false;
        boolean zavrsenaPolicijskaObrada = false;
        boolean zavrsenaCarinskaObrada = false;
        boolean bioNaP1 = false;
        boolean bioNaP2 = false;

        while (!zavrsenaPolicijskaObrada) {

            if(Simulacija.granicniRed.size()>0 && Simulacija.granicniRed.peek() == this) {
                if(Simulacija.p1.isSlobodan()){
                    bioNaP1 = true;
                    System.out.println(this + ": usao u policijski terminal 1!");
                    Simulacija.p1.setSlobodan(false); //zauzimamo policijski terminal
                    Main.pomjeriVozilaNaPolicijski(1);
                    Simulacija.granicniRed.poll(); //izlazi iz granicnog reda



                    mozeProciPolicijskiTerminal = Simulacija.p1.obradiVozilo(this); //obradjujemo vozilo
                    if(!mozeProciPolicijskiTerminal){
                        Main.pomjeriNaTrecuScenu(1);
                        System.out.println("Pao policijsku provjeru!");
                        Simulacija.p1.setSlobodan(true);
                    }
                    else{
                        Simulacija.carinskiRed.add(this);
                    }
                    System.out.println(this + ": izasao iz policijskog terminala 1!");

                    //zavrsava
                    //Simulacija.p1.setSlobodan(true); //izbrisati

                    zavrsenaPolicijskaObrada = true;

                } else if (Simulacija.p2.isSlobodan()) {
                    bioNaP2 = true;
                    System.out.println(this + ": usao u policijski terminal 2!");
                    Simulacija.p2.setSlobodan(false); //zauzimamo policijski terminal
                    Main.pomjeriVozilaNaPolicijski(2);
                    Simulacija.granicniRed.poll(); //izlazi iz granicnog reda


                    mozeProciPolicijskiTerminal = Simulacija.p2.obradiVozilo(this); //obradjujemo vozilo
                    if(!mozeProciPolicijskiTerminal){
                        Main.pomjeriNaTrecuScenu(2);
                        System.out.println("Pao policijsku provjeru!");
                        Simulacija.p2.setSlobodan(true);
                    }
                    else {
                        Simulacija.carinskiRed.add(this);
                    }
                    System.out.println(this + ": izasao iz policijskog terminala 2!");
                    //zavrsava
                    //Simulacija.p2.setSlobodan(true); //izbrisati

                    zavrsenaPolicijskaObrada = true;
                }

            }
        }
        if(mozeProciPolicijskiTerminal){
            while(!zavrsenaCarinskaObrada){
                if(Simulacija.c1.isSlobodan() && Simulacija.carinskiRed.size()>0
                        && Simulacija.carinskiRed.peek() == this){
                    Simulacija.c1.setSlobodan(false);

                    if(bioNaP1){
                        Main.pomjeriVozilaNaCarinski(1, 1);
                        Simulacija.carinskiRed.poll();
                        Simulacija.p1.setSlobodan(true);

                    } else if (bioNaP2) {
                        Main.pomjeriVozilaNaCarinski(2, 1);
                        Simulacija.carinskiRed.poll();
                        Simulacija.p2.setSlobodan(true);

                    }


                    System.out.println(this + ": usao u carinski terminal 1!");
                    //uvijek licno vozilo moze proci carinski terminal
                    //mozeProciCarinskiTerminal = Simulacija.c1.obradiVozilo(this);
//                    if(!mozeProciCarinskiTerminal){
//
//                        System.out.println("Pao carinsku provjeru!");
//                    }

                    Simulacija.c1.obradiVozilo(this);

                    Main.izbrisiVozilo(1);

                    System.out.println(this + ": izasao iz carinskog terminala 1!");

                    Simulacija.c1.setSlobodan(true);
                    zavrsenaCarinskaObrada = true;
                }
            }
        }

    }

    @Override
    public String toString(){
        return "Autobus " + idVozila + "{\nVozac: " + vozac + "\nBroj putnika: " +
                brojPutnika + "\n}";
    }

}
