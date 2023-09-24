package net.etfbl.pj2.terminalWatcher;

import net.etfbl.pj2.handler.ProjektniHandler;
import net.etfbl.pj2.simulacija.Simulacija;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TerminalWatcher extends Thread {
    private String path = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "terminal.txt";

    public TerminalWatcher() {
        setDaemon(true);
    }

    public void run() {
        File file = new File(path);
        String read;
        String[] s;
        while (true) {
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
                read = bufferedReader.readLine();
                s = read.split(",");
                if("1".equals(s[0])){
                    Simulacija.p1.setRadi(true);
                }else if("0".equals(s[0])){
                    Simulacija.p1.setRadi(false);
                }
                if("1".equals(s[1])){
                    Simulacija.p2.setRadi(true);
                }else if ("0".equals(s[1])){
                    Simulacija.p2.setRadi(false);
                }
                if("1".equals(s[2])){
                    Simulacija.pk.setRadi(true);
                }else if("0".equals(s[2])){
                    Simulacija.pk.setRadi(false);
                }
                if("1".equals(s[3])){
                    Simulacija.c1.setRadi(true);
                }else if("0".equals(s[3])){
                    Simulacija.c1.setRadi(false);
                }
                if("1".equals(s[4])){
                    Simulacija.ck.setRadi(true);
                }else if("0".equals(s[4])){
                    Simulacija.ck.setRadi(false);
                }

            }catch (IOException e){
                Logger.getLogger(ProjektniHandler.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
            }
        }
    }
}
