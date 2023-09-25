package net.etfbl.pj2.terminal;

import net.etfbl.pj2.Main;
import net.etfbl.pj2.handler.ProjektniHandler;
import net.etfbl.pj2.incident.Incident;
import net.etfbl.pj2.osobe.Putnik;
import net.etfbl.pj2.simulacija.Simulacija;
import net.etfbl.pj2.vozila.Vozilo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PolicijskiTerminal extends Terminal{

    @Override
    public boolean obradiVozilo(Vozilo vozilo) {
        //Incident incident;
        //obrada vozaca
        try{
            Thread.sleep(vozilo.getVrijemeObradePutnika());
        }
        catch(InterruptedException e){
            Logger.getLogger(ProjektniHandler.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
        //provjeravamo da li vozac ima ispravan dokument
        if(vozilo.getVozac().getIdentifikacioniDokument().isNeispravan()){
            Incident incident = new Incident(vozilo.getIdVozila(), vozilo.prostIspis() + ": " + "Vozac " + vozilo.getVozac().getIme() + " nema validan identifikacioni dokument. ");
            Simulacija.vozilaSaIncidentima.add(incident);
            Main.serijalizujUBinarni(incident);

            vozilo.setImaoPolicijskiIncident(true);
            //Main.postaviNaTrecuScenu(incident.getOpisProblema(), vozilo);
            return false;
        }
        //provjeravamo putnike i dokumente
        ArrayList<Putnik> putnici = vozilo.getPutnici();
        for (Iterator<Putnik> it = putnici.iterator(); it.hasNext();) {
            try{
                Thread.sleep(vozilo.getVrijemeObradePutnika());
            }
            catch(InterruptedException e){
                Logger.getLogger(ProjektniHandler.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
            }
            if(Simulacija.pauza){
                synchronized (Simulacija.lock){
                    try{
                        Simulacija.lock.wait();
                    }catch (InterruptedException e){
                        Logger.getLogger(ProjektniHandler.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                    }
                }
            }
            Putnik putnik = it.next();
            if(putnik.getIdentifikacioniDokument().isNeispravan())
            {
                if(vozilo.isImaoPolicijskiIncident()){
                    for (Incident incident:
                            Simulacija.vozilaSaIncidentima) {
                        if(incident.getIdVozila() == vozilo.getIdVozila())
                        {
                            incident.setOpisProblema(incident.getOpisProblema() +
                                    "\nPutnik " + putnik.getIme() + " nema validan identifikacioni dokument. ");
                        }
                    }
                }
                else {
                    vozilo.setImaoPolicijskiIncident(true);
                    Incident incident = new Incident(vozilo.getIdVozila(), "Putnik " + putnik.getIme() + " nema validan identifikacioni dokument. ");
                    Simulacija.vozilaSaIncidentima.add(incident);
                }

                it.remove();
            }
        }
        if(vozilo.isImaoPolicijskiIncident()){
            for (Incident incident:
                    Simulacija.vozilaSaIncidentima) {
                if(incident.getIdVozila()==vozilo.getIdVozila())
                {
                    Main.serijalizujUBinarni(incident);
                    Main.citajBinarni(vozilo);
                }
            }
        }

        return true;
    }
}
