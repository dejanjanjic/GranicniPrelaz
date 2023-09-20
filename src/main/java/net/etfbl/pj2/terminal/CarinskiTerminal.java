package net.etfbl.pj2.terminal;

import net.etfbl.pj2.handler.ProjektniHandler;
import net.etfbl.pj2.vozila.Kamion;
import net.etfbl.pj2.vozila.Vozilo;
import net.etfbl.pj2.vozila.dodaci.CarinskaDokumentacija;
import net.etfbl.pj2.vozila.interfejsi.AutobusInterfejs;
import net.etfbl.pj2.vozila.interfejsi.KamionInterfejs;
import net.etfbl.pj2.vozila.interfejsi.LicnoVoziloInterfejs;
import net.etfbl.pj2.osobe.*;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarinskiTerminal extends Terminal{
    @Override 
    public boolean obradiVozilo(Vozilo vozilo){
        if(vozilo instanceof LicnoVoziloInterfejs){
            try{
                Thread.sleep(2000);
            }catch (InterruptedException e){
                Logger.getLogger(ProjektniHandler.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
            }
        }
        else if(vozilo instanceof AutobusInterfejs){
            for(Iterator<Putnik> it = vozilo.getPutnici().iterator(); it.hasNext();){
                try {
                    Thread.sleep(vozilo.getVrijemeObradePutnika());
                }catch (InterruptedException e){
                    Logger.getLogger(ProjektniHandler.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                }
                Putnik putnik = it.next();
                if(putnik.getKofer() != null && putnik.getKofer().isImaNedozvoljeneStvari()){
                    vozilo.izbaciPutnika(putnik);
                    it.remove();
                }
            }
        } else if (vozilo instanceof KamionInterfejs) {
            try {
                Thread.sleep(500);
            }catch (InterruptedException e){
                Logger.getLogger(ProjektniHandler.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
            }
            Kamion kamion = (Kamion) vozilo;
            if(kamion.isTrebaCarinskaDokumentacija()){
                kamion.setCarinskaDokumentacija(new CarinskaDokumentacija());
                if(kamion.getTeret().getMasa() > kamion.getDeklarisanaMasa()){
                    //izbaci kamion
                    System.out.println(vozilo + ": IZBACEN!!!!!!!");
                    // TODO: 9.9.2023.
                    return false;
                }
            }
        }
        return true;
    }
}
