package net.etfbl.pj2.handler;

import net.etfbl.pj2.vozila.Vozilo;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class ProjektniHandler {
    public static Handler handler;



    static {
        try {
            handler = new FileHandler("projektni.log");
            Logger.getLogger(Vozilo.class.getName()).addHandler(handler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
