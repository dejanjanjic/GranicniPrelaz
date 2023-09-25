package net.etfbl.pj2.vozila;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.etfbl.pj2.Main;
import net.etfbl.pj2.handler.ProjektniHandler;
import net.etfbl.pj2.osobe.dodaci.Kofer;
import net.etfbl.pj2.simulacija.Simulacija;
import net.etfbl.pj2.vozila.dodaci.TeretniProstor;
import net.etfbl.pj2.osobe.*;
import net.etfbl.pj2.vozila.interfejsi.AutobusInterfejs;

public class Autobus extends Vozilo implements AutobusInterfejs {
    private static final long serialVersionUID = 1;

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

    public String prostIspis(){
        return "Autobus"+idVozila;
    }
    @Override
    public void run() {
        boolean mozeProciPolicijskiTerminal = false;
        boolean mozeProciCarinskiTerminal = false;
        boolean zavrsenaPolicijskaObrada = false;
        boolean zavrsenaCarinskaObrada = false;
        boolean bioNaP1 = false;
        boolean bioNaP2 = false;
        String opisIncidenta = prostIspis() + ": ";

        while (!zavrsenaPolicijskaObrada) {
            if(!Simulacija.pauza)
            {

                if (Simulacija.granicniRed.size() > 0 && Simulacija.granicniRed.peek() == this) {
                    if (Simulacija.p1.isRadi() && Simulacija.p1.isSlobodan()) {
                        bioNaP1 = true;
                        System.out.println(prostIspis() + ": usao u policijski terminal 1!");
                        Simulacija.p1.setSlobodan(false); //zauzimamo policijski terminal
                        Main.pomjeriVozilaNaPolicijski(1);

                        Simulacija.granicniRed.poll(); //izlazi iz granicnog reda
                        if(Simulacija.granicniRed.peek() != null){
                            Simulacija.granicniRed.peek().start();
                        }


                        mozeProciPolicijskiTerminal = Simulacija.p1.obradiVozilo(this); //obradjujemo vozilo
                        if (!mozeProciPolicijskiTerminal) {
                            Main.izbrisiVozilo(1);
                            opisIncidenta = Main.citajBinarni(this);
                            Main.postaviNaTrecuScenu(opisIncidenta, this);
                            System.out.println(prostIspis() + ": pao policijsku provjeru!");
                            Simulacija.p1.setSlobodan(true);
                        } else {
                            Simulacija.carinskiRed.add(this);
                        }
                        System.out.println(prostIspis() + ": izasao iz policijskog terminala 1!");

                        //zavrsava
                        //Simulacija.p1.setSlobodan(true); //izbrisati

                        zavrsenaPolicijskaObrada = true;

                    } else if (Simulacija.p2.isRadi() && Simulacija.p2.isSlobodan()) {
                        bioNaP2 = true;
                        System.out.println(prostIspis() + ": usao u policijski terminal 2!");
                        Simulacija.p2.setSlobodan(false); //zauzimamo policijski terminal
                        Main.pomjeriVozilaNaPolicijski(2);
                        Simulacija.granicniRed.poll(); //izlazi iz granicnog reda
                        if(Simulacija.granicniRed.peek() != null){
                            Simulacija.granicniRed.peek().start();
                        }



                        mozeProciPolicijskiTerminal = Simulacija.p2.obradiVozilo(this); //obradjujemo vozilo
                        if (!mozeProciPolicijskiTerminal) {
                            Main.izbrisiVozilo(2);
                            opisIncidenta = Main.citajBinarni(this);
                            Main.postaviNaTrecuScenu(opisIncidenta, this);

                            System.out.println(prostIspis() + ": pao policijsku provjeru!");
                            Simulacija.p2.setSlobodan(true);
                        } else {
                            Simulacija.carinskiRed.add(this);
                        }
                        System.out.println(prostIspis() + ": izasao iz policijskog terminala 2!");
                        //zavrsava
                        //Simulacija.p2.setSlobodan(true); //izbrisati

                        zavrsenaPolicijskaObrada = true;
                    }
                    if(imaoPolicijskiIncident && mozeProciPolicijskiTerminal){
                        opisIncidenta += Main.citajBinarni(this);
                    }

                }
            }
            else{
                synchronized (Simulacija.lock){
                    try{
                        Simulacija.lock.wait();
                    }catch (InterruptedException e){
                        Logger.getLogger(ProjektniHandler.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                    }
                }
            }

        }
        if(mozeProciPolicijskiTerminal){
            while(!zavrsenaCarinskaObrada){
                if(!Simulacija.pauza)
                {

                    if (Simulacija.carinskiRed.size() > 0
                            && Simulacija.carinskiRed.peek() == this && Simulacija.c1.isRadi() && Simulacija.c1.isSlobodan()) {
                        //ovdje izgubi
                        Simulacija.c1.setSlobodan(false);

                        if (bioNaP1) {
                            Main.pomjeriVozilaNaCarinski(1, 1);
                            Simulacija.carinskiRed.poll();
                            Simulacija.p1.setSlobodan(true);

                        } else if (bioNaP2) {
                            Main.pomjeriVozilaNaCarinski(2, 1);
                            Simulacija.carinskiRed.poll();
                            Simulacija.p2.setSlobodan(true);

                        }


                        System.out.println(prostIspis() + ": usao u carinski terminal 1!");

                        Simulacija.c1.obradiVozilo(this);
                        if(Simulacija.pauza){
                            synchronized (Simulacija.lock){
                                try{
                                    Simulacija.lock.wait();
                                }catch (InterruptedException e){
                                    Logger.getLogger(ProjektniHandler.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                                }
                            }
                        }
                        if(imaoCarinskiIncident){
                            opisIncidenta += Main.citajIzTekstualnog(this);
                        }
                        if(imaoCarinskiIncident || imaoPolicijskiIncident){
                            Main.postaviNaTrecuScenu(opisIncidenta, this);
                        }
                        Main.izbrisiVozilo(4);

                        System.out.println(prostIspis() + ": izasao iz carinskog terminala 1!");

                        Simulacija.c1.setSlobodan(true);
                        zavrsenaCarinskaObrada = true;

                    }
                }else{
                    synchronized (Simulacija.lock){
                        try{
                            Simulacija.lock.wait();
                        }catch (InterruptedException e){
                            Logger.getLogger(ProjektniHandler.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                        }
                    }
                }
            }
        }

    }

    @Override
    public String toString(){
        String vozilo = "Autobus " + idVozila + "{\nVozac: " + vozac + "\nBroj putnika: " +
                brojPutnika  + "\nInformacije o putnicima: ";
        for (Putnik putnik :
                putnici) {
            vozilo += "\n" + putnik;
        }
        return vozilo + "\n}";
    }

}
