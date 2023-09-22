package net.etfbl.pj2.vozila;

import net.etfbl.pj2.Main;
import net.etfbl.pj2.handler.ProjektniHandler;
import net.etfbl.pj2.simulacija.Simulacija;
import net.etfbl.pj2.vozila.dodaci.CarinskaDokumentacija;
import net.etfbl.pj2.vozila.dodaci.Teret;
import net.etfbl.pj2.vozila.interfejsi.KamionInterfejs;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Kamion extends Vozilo implements KamionInterfejs {
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

    @Override
    public void run() {
            boolean mozeProciPolicijskiTerminal = false;
            boolean mozeProciCarinskiTerminal = false;
            boolean zavrsenaPolicijskaObrada = false;
            boolean zavrsenaCarinskaObrada = false;



            while (!zavrsenaPolicijskaObrada) {
                if(!Simulacija.pauza)
                {

                    if (Simulacija.granicniRed.size() > 0 && Simulacija.granicniRed.peek() == this && Simulacija.pk.isRadi() && Simulacija.pk.isSlobodan()) {

                        System.out.println(this + ": usao u policijski terminal!");

                        Simulacija.pk.setSlobodan(false); //zauzimamo policijski terminal

                        Main.pomjeriVozilaNaPolicijski(3);

                        Simulacija.granicniRed.poll(); //izlazi iz granicnog reda
                        if(Simulacija.granicniRed.peek() != null){
                            Simulacija.granicniRed.peek().start();
                        }


                        mozeProciPolicijskiTerminal = Simulacija.pk.obradiVozilo(this); //obradjujemo vozilo
                        if (!mozeProciPolicijskiTerminal) {
                            Main.pomjeriNaTrecuScenu(3);
                            System.out.println("!!!!!!!!!!!!!!!Pao policijsku provjeru!!!!!!!!!!!!!!!!!!!!!!");
                            Simulacija.pk.setSlobodan(true);
                        }
                        //zavrsava
                        zavrsenaPolicijskaObrada = true;
                        System.out.println(this + ": izasao iz policijskog terminala!");

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
                            System.out.println(this + ": usao u carinski terminal!");
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
                                if (!mozeProciCarinskiTerminal) {
                                    Main.pomjeriNaTrecuScenu(4);
                                    System.out.println("Pao carinsku provjeru!");
                                } else {
                                    Main.izbrisiVozilo(2);
                                }
                                System.out.println(this + ": izasao iz carinskog terminala!");

                                Simulacija.ck.setSlobodan(true);
                                zavrsenaCarinskaObrada = true;
                            //}
                            // TODO: 9.9.2023. Napraviti logiku za situacije kad vozila ne mogu proci terminal
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
            else{
                System.out.println(this + ": IZASAO SA GRANICE!");
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
        return "Kamion " + idVozila + "{\nVozac: " + vozac + "\nBroj putnika: " +
                brojPutnika + "\n}";
    }

}
