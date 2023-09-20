package net.etfbl.pj2.terminal;

import net.etfbl.pj2.handler.ProjektniHandler;
import net.etfbl.pj2.osobe.Putnik;
import net.etfbl.pj2.vozila.Vozilo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PolicijskiTerminal extends Terminal{

    @Override
    public boolean obradiVozilo(Vozilo vozilo) {

        //obrada vozaca
        try{
            Thread.sleep(vozilo.getVrijemeObradePutnika());
        }
        catch(InterruptedException e){
            Logger.getLogger(ProjektniHandler.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
        //provjeravamo da li vozac ima ispravan dokument
        if(vozilo.getVozac().getIdentifikacioniDokument().isNeispravan()){

            vozilo.izbaciVozaca(vozilo.getVozac());
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
            Putnik putnik = it.next();
            if(putnik.getIdentifikacioniDokument().isNeispravan())
            {
                it.remove();
                vozilo.izbaciPutnika(putnik);
            }
        }
        return true;
    }
}
