package net.etfbl.pj2.vozila;

import net.etfbl.pj2.Main;
import net.etfbl.pj2.handler.ProjektniHandler;
import net.etfbl.pj2.simulacija.Simulacija;
import net.etfbl.pj2.vozila.dodaci.CarinskaDokumentacija;
import net.etfbl.pj2.vozila.dodaci.Teret;
import net.etfbl.pj2.vozila.interfejsi.KamionInterfejs;
import net.etfbl.pj2.osobe.Putnik;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Kamion extends Vozilo implements KamionInterfejs {
    private static final long serialVersionUID = 1;

    private Teret teret;
    private int deklarisanaMasa;
    private boolean nedozvoljenaMasa;
    private CarinskaDokumentacija carinskaDokumentacija;
    private boolean trebaCarinskaDokumentacija;


    private static final int KAPACITET_PUTNIKA = 2;
    private static final int MAX_MASA_DEKLARISANA = 97;

    public Kamion() {
        super(generisiBrojPutnika(KAPACITET_PUTNIKA));
        Random random = new Random();
        trebaCarinskaDokumentacija = random.nextBoolean();
        deklarisanaMasa = random.nextInt(MAX_MASA_DEKLARISANA) + 4;
        nedozvoljenaMasa = random.nextInt(100) < 20;
        if(nedozvoljenaMasa){
            teret = new Teret(random.nextInt((int) (0.3 * deklarisanaMasa)) + deklarisanaMasa + 1);
        }
        else{
            teret = new Teret(deklarisanaMasa);
        }
        vrijemeObradePutnika = 500;
    }

    public String prostIspis(){
        return "Kamion" + idVozila;
    }

    @Override
    public void run() {
            boolean mozeProciPolicijskiTerminal = false;
            boolean mozeProciCarinskiTerminal = false;
            boolean zavrsenaPolicijskaObrada = false;
            boolean zavrsenaCarinskaObrada = false;
            String opisIncidenta = prostIspis() + ": ";


            while (!zavrsenaPolicijskaObrada) {
                if(!Simulacija.pauza)
                {

                    if (Simulacija.granicniRed.size() > 0 && Simulacija.granicniRed.peek() == this && Simulacija.pk.isRadi() && Simulacija.pk.isSlobodan()) {

                        System.out.println(prostIspis() + ": usao u policijski terminal!");

                        Simulacija.pk.setSlobodan(false); //zauzimamo policijski terminal

                        Main.pomjeriVozilaNaPolicijski(3);

                        Simulacija.granicniRed.poll(); //izlazi iz granicnog reda
                        if(Simulacija.granicniRed.peek() != null){
                            Simulacija.granicniRed.peek().start();
                        }


                        mozeProciPolicijskiTerminal = Simulacija.pk.obradiVozilo(this); //obradjujemo vozilo
                        if (!mozeProciPolicijskiTerminal) {
                            Main.izbrisiVozilo(3);
                            Main.citajBinarni(this);

                            System.out.println(prostIspis() + ": pao policijsku provjeru");
                            Simulacija.pk.setSlobodan(true);
                        }
                        //zavrsava
                        zavrsenaPolicijskaObrada = true;
                        System.out.println(prostIspis() + ": izasao iz policijskog terminala!");
                        if(imaoPolicijskiIncident && mozeProciPolicijskiTerminal){
                            opisIncidenta += Main.citajBinarni(this);
                        }
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

            if(mozeProciPolicijskiTerminal){
                while(!zavrsenaCarinskaObrada) {

                    if (!Simulacija.pauza) {

                        if (Simulacija.ck.isRadi() && Simulacija.ck.isSlobodan()) {
                            Simulacija.ck.setSlobodan(false);
                            Main.pomjeriVozilaNaCarinski(3, 2);
                            System.out.println(prostIspis() + ": usao u carinski terminal!");
                            Simulacija.pk.setSlobodan(true);

                            mozeProciCarinskiTerminal = Simulacija.ck.obradiVozilo(this);
                            if(Simulacija.pauza){
                                synchronized (Simulacija.lock){
                                    try{
                                        Simulacija.lock.wait();
                                    }catch (InterruptedException e){
                                        Logger.getLogger(ProjektniHandler.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                                    }
                                }
                            }
                            //if(!Simulacija.pauza)
                            //{
                            Main.izbrisiVozilo(5);
                            if (!mozeProciCarinskiTerminal) {
                                System.out.println(prostIspis() + ": pao carinsku provjeru!");
                                opisIncidenta += Main.citajIzTekstualnog(this);
                                }
                            if(imaoCarinskiIncident || imaoPolicijskiIncident){
                                Main.postaviNaTrecuScenu(opisIncidenta, this);
                            }
                            System.out.println(prostIspis() + ": izasao iz carinskog terminala!");

                                Simulacija.ck.setSlobodan(true);
                                zavrsenaCarinskaObrada = true;
                            //}

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

    public Teret getTeret() {
        return teret;
    }

    public void setTeret(Teret teret) {
        this.teret = teret;
    }

    public int getDeklarisanaMasa() {
        return deklarisanaMasa;
    }

    public void setDeklarisanaMasa(int deklarisanaMasa) {
        this.deklarisanaMasa = deklarisanaMasa;
    }

    public boolean isNedozvoljenaMasa() {
        return nedozvoljenaMasa;
    }

    public void setNedozvoljenaMasa(boolean nedozvoljenaMasa) {
        this.nedozvoljenaMasa = nedozvoljenaMasa;
    }

    public void setCarinskaDokumentacija(CarinskaDokumentacija carinskaDokumentacija) {
        this.carinskaDokumentacija = carinskaDokumentacija;
    }

    public CarinskaDokumentacija getCarinskaDokumentacija() {
        return carinskaDokumentacija;
    }

    public boolean isTrebaCarinskaDokumentacija() {
        return trebaCarinskaDokumentacija;
    }

    @Override
    public String toString(){
        String vozilo = "Kamion " + idVozila + "{\nVozac: " + vozac + "\nBroj putnika: " +
                brojPutnika + "\nMasa tereta: " + teret.getMasa() +"\nDeklarisana masa: " + deklarisanaMasa +
                "\nTreba generisati carinsku dokumentaciju: " + trebaCarinskaDokumentacija + "\nInformacije o putnicima: ";
        for (Putnik putnik :
                putnici) {
            vozilo += "\n" + putnik;
        }
        return vozilo + "\n}";
    }

}
