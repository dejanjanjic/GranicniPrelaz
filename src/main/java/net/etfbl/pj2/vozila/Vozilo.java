package net.etfbl.pj2.vozila;

import java.io.*;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.etfbl.pj2.handler.ProjektniHandler;
import net.etfbl.pj2.incident.Incident;
import net.etfbl.pj2.osobe.*;
public abstract class Vozilo extends Thread implements Serializable {
    protected Vozac vozac;
    protected int brojPutnika;
    protected int vrijemeObradePutnika = 0;
    protected ArrayList<Putnik> putnici;
    protected int idVozila;
    protected int pozicijaURedu = -1;
    private static int brojacVozila = 0;
    //flag koji ce se postaviti na false ako vozac nema ispravne dokumente

    protected boolean prosaoGranicu = false;
    protected boolean imaoPolicijskiIncident = false;
    protected boolean imaoCarinskiIncident = false;

    private static final File folderKaznjeni = new File("src" + File.separator +"Kaznjeni");



    public Vozilo(int brojPutnika) {
        this(brojPutnika, false);
    }

    public Vozilo(int brojPutnika, boolean mozeImatiKofer) {
        this.vozac = new Vozac();
        this.brojPutnika = brojPutnika;
        this.putnici = new ArrayList<>();
        if(mozeImatiKofer){
            for (int i = 0; i < brojPutnika; i++) {
                this.putnici.add(i, new Putnik(true));
            }
        }
        else {
            for (int i = 0; i < brojPutnika; i++) {
                this.putnici.add(i, new Putnik());
            }
        }
        this.idVozila = ++brojacVozila;
    }

    public static int generisiBrojPutnika(int capacity){
        Random random = new Random();
        return random.nextInt(capacity + 1);
    }

    public Vozac getVozac() {
        return vozac;
    }

    public void setVozac(Vozac vozac) {
        this.vozac = vozac;
    }

    public int getBrojPutnika() {
        return brojPutnika;
    }

    public void setBrojPutnika(int brojPutnika) {
        this.brojPutnika = brojPutnika;
    }

    public ArrayList<Putnik> getPutnici() {
        return putnici;
    }

    public void setPutnici(ArrayList<Putnik> putnici) {
        this.putnici = putnici;
    }

    public int getIdVozila() {
        return idVozila;
    }

    public void setIdVozila(int idVozila) {
        this.idVozila = idVozila;
    }

    //metoda koja izbacuje vozaca iz vozila
    public void izbaciVozaca(Vozac vozac) {
        File fileKaznjeni = kreirajFajlKaznjeni();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileKaznjeni))) {
            objectOutputStream.writeObject(vozac);
        } catch (IOException e) {
            Logger.getLogger(Vozilo.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

    
    //metoda koja izbacuje putnika iz vozila
    public void izbaciPutnika(Putnik putnik){
        File fileKaznjeni = kreirajFajlKaznjeni();
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileKaznjeni))){
            objectOutputStream.writeObject(putnik);
        }catch(IOException e){
            Logger.getLogger(Vozilo.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

    private File kreirajFajlKaznjeni(){
        //pravimo fileKaznjeni koristimo trenutno vrijeme u imenu kako bismo imali jedinstvene nazive
        File fileKaznjeni = new File(folderKaznjeni.toString() + File.separator
                + System.currentTimeMillis() + ".txt");
        if(!fileKaznjeni.exists()){
            if(!folderKaznjeni.exists()){
                //kreiramo folder u slucaju da ne postoji
                folderKaznjeni.mkdir();
            }
            try{
                fileKaznjeni.createNewFile();
            }catch (IOException e){
                Logger.getLogger(ProjektniHandler.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
            }
        }
        return fileKaznjeni;
    }

    public int getVrijemeObradePutnika() {
        return vrijemeObradePutnika;
    }



    public int getPozicijaURedu() {
        return pozicijaURedu;
    }

    public void setPozicijaURedu(int pozicijaURedu) {
        this.pozicijaURedu = pozicijaURedu;
    }

    public boolean isImaoPolicijskiIncident() {
        return imaoPolicijskiIncident;
    }

    public void setImaoPolicijskiIncident(boolean imaoPolicijskiIncident) {
        this.imaoPolicijskiIncident = imaoPolicijskiIncident;
    }

    public boolean isImaoCarinskiIncident() {
        return imaoCarinskiIncident;
    }

    public void setImaoCarinskiIncident(boolean imaoCarinskiIncident) {
        this.imaoCarinskiIncident = imaoCarinskiIncident;
    }

    @Override
    public String toString() {
        return "Vozilo{" +
                "vozac=" + vozac +
                ", brojPutnika=" + brojPutnika +
                ", id=" + idVozila +
                '}';
    }
}
