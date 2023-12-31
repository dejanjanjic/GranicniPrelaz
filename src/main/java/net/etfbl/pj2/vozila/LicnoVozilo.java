package net.etfbl.pj2.vozila;

import net.etfbl.pj2.Main;
import net.etfbl.pj2.handler.ProjektniHandler;
import net.etfbl.pj2.incident.Incident;
import net.etfbl.pj2.osobe.Putnik;
import net.etfbl.pj2.simulacija.Simulacija;
import net.etfbl.pj2.vozila.Vozilo;
import net.etfbl.pj2.vozila.interfejsi.LicnoVoziloInterfejs;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LicnoVozilo extends Vozilo implements LicnoVoziloInterfejs {
    private static final long serialVersionUID = 1;

    private static final int KAPACITET_PUTNIKA = 4;

    public LicnoVozilo() {
        super(generisiBrojPutnika(KAPACITET_PUTNIKA));
        vrijemeObradePutnika = 500;
    }

    public String prostIspis(){
        return "Automobil" + idVozila;
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


                        if(mozeProciPolicijskiTerminal && imaoPolicijskiIncident){
                            opisIncidenta += Main.citajBinarni(this);
                            Main.postaviNaTrecuScenu(opisIncidenta, this);
                        }

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

                        if(mozeProciPolicijskiTerminal && imaoPolicijskiIncident){
                            opisIncidenta += Main.citajBinarni(this);
                            Main.postaviNaTrecuScenu(opisIncidenta, this);
                        }


                        zavrsenaPolicijskaObrada = true;
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
        String vozilo = "Automobil " + idVozila + "{\nVozac: " + vozac + "\nBroj putnika: " +
                brojPutnika  + "\nInformacije o putnicima: ";
        for (Putnik putnik :
                putnici) {
            vozilo += "\n" + putnik;
        }
        return vozilo + "\n}";
    }
}
