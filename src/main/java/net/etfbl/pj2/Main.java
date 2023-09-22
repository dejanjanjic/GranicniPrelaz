package net.etfbl.pj2;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.etfbl.pj2.simulacija.Simulacija;
import net.etfbl.pj2.terminalWatcher.TerminalWatcher;
import net.etfbl.pj2.vozila.Autobus;
import net.etfbl.pj2.vozila.Kamion;
import net.etfbl.pj2.vozila.LicnoVozilo;
import net.etfbl.pj2.vozila.Vozilo;
import net.etfbl.pj2.vozila.interfejsi.AutobusInterfejs;
import net.etfbl.pj2.vozila.interfejsi.KamionInterfejs;
import net.etfbl.pj2.vozila.interfejsi.LicnoVoziloInterfejs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    public static VBox pocetakReda = new VBox();
    public static TilePane ostatakReda = new TilePane();
    public static TilePane kaznjeni = new TilePane();
    public static StackPane policijskiTerminal1 = new StackPane();
    public static StackPane policijskiTerminal2 = new StackPane();
    public static StackPane policijskiTerminalK = new StackPane();
    public static StackPane carinskiTerminal1 = new StackPane();
    public static StackPane carinskiTerminalK = new StackPane();
    private Timer timer;
    private int seconds = 0;
    private int minutes = 0;
    private Text timeText;


    @Override
    public void start(Stage stage) throws IOException {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 670, 690);
        Image image = new Image("icon.png");
        stage.getIcons().add(image);
        stage.setTitle("Granicni prelaz");
        stage.setResizable(false);
        stage.setScene(scene);

        AnchorPane anchorPane = new AnchorPane();
        root.getChildren().add(anchorPane);

        //Pozadinska slika za scenu 1
        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image("background-1.png", 670, 690, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        root.setBackground(background);

        //dodavanje policijskog terminala 1
        Image borderImage = new Image("border.png");
        ImageView borderImageView1 = new ImageView(borderImage);
        Rectangle rectangle = new Rectangle(120, 110, Color.DODGERBLUE);
        rectangle.setOpacity(0.1);

        Text text = new Text("P1");
        //StackPane policijskiTerminal1 = new StackPane(rectangle, text);
        //policijskiTerminal1.getChildren().add(rectangle);
       // policijskiTerminal1.getChildren().add(text);
        policijskiTerminal1.getChildren().add(borderImageView1);
        anchorPane.getChildren().add(policijskiTerminal1);
        policijskiTerminal1.setLayoutX(145); // X koordinata //bilo 14
        policijskiTerminal1.setLayoutY(222); // Y koordinata
//        BorderStroke borderStroke = new BorderStroke(
//                Color.BLACK,
//                BorderStrokeStyle.SOLID,
//                CornerRadii.EMPTY,
//                new BorderWidths(1) // Debljina crne linije oko StackPane-a
//        );
        //Border border = new Border(borderStroke);
        //policijskiTerminal1.setBorder(border);

        //dodavanje policijskog terminala 2
        rectangle = new Rectangle(120, 110, Color.DODGERBLUE);
        rectangle.setOpacity(0.1);
        ImageView borderImageView2 = new ImageView(borderImage);
        text = new Text("P2");
        policijskiTerminal2.getChildren().add(borderImageView2);
        //StackPane policijskiTerminal2 = new StackPane(rectangle, text);
        //policijskiTerminal2.getChildren().add(rectangle);
        //policijskiTerminal2.getChildren().add(text);
        anchorPane.getChildren().add(policijskiTerminal2);
        policijskiTerminal2.setLayoutX(275); // X koordinata
        policijskiTerminal2.setLayoutY(222); // Y koordinata


        //dodavanje policijskog terminala za kamione
        rectangle = new Rectangle(120, 110, Color.DODGERBLUE);
        rectangle.setOpacity(0.1);
        ImageView borderImageView3 = new ImageView(borderImage);
        text = new Text("PK");
        policijskiTerminalK.getChildren().add(borderImageView3);
        //StackPane policijskiTerminalK = new StackPane(rectangle, text);
        //policijskiTerminalK.getChildren().add(rectangle);
        //policijskiTerminalK.getChildren().add(text);
        anchorPane.getChildren().add(policijskiTerminalK);
        policijskiTerminalK.setLayoutX(406); // X koordinata //bilo 536
        policijskiTerminalK.setLayoutY(222); // Y koordinata


        //dodavanje carinskog terminala 1
        rectangle = new Rectangle(120, 110, Color.DODGERBLUE);
        rectangle.setOpacity(0.1);
        ImageView borderImageView4 = new ImageView(borderImage);
        text = new Text("C1");
        carinskiTerminal1.getChildren().add(borderImageView4);
        //StackPane carinskiTerminal1 = new StackPane(rectangle, text);
        //carinskiTerminal1.getChildren().add(rectangle);
        //carinskiTerminal1.getChildren().add(text);
        anchorPane.getChildren().add(carinskiTerminal1);
        carinskiTerminal1.setLayoutX(145); // X koordinata
        carinskiTerminal1.setLayoutY(84); // Y koordinata


        //dodavanje carinskog terminala za kamione
        rectangle = new Rectangle(120, 110, Color.DODGERBLUE);
        rectangle.setOpacity(0.1);
        ImageView borderImageView5 = new ImageView(borderImage);
        text = new Text("CK");
        carinskiTerminalK.getChildren().add(borderImageView5);
        //StackPane carinskiTerminalK = new StackPane(rectangle, text);
        //carinskiTerminalK.getChildren().add(rectangle);
        //carinskiTerminalK.getChildren().add(text);
        anchorPane.getChildren().add(carinskiTerminalK);
        carinskiTerminalK.setLayoutX(406); // X koordinata
        carinskiTerminalK.setLayoutY(84); // Y koordinata


        //kreiranje VBox-a u kojem ce se nalaziti prvih 5 vozila u redu
        //VBox pocetakReda = new VBox();
        pocetakReda.setLayoutX(305);
        pocetakReda.setLayoutY(388);
        pocetakReda.setPrefHeight(221);
        pocetakReda.setPrefWidth(56);
        anchorPane.getChildren().add(pocetakReda);

        //button za prikazivanje druge scene
        Button prikaziDruguScenu = new Button("Druga scena");
        prikaziDruguScenu.setLayoutX(536);
        prikaziDruguScenu.setLayoutY(14);
        prikaziDruguScenu.setPrefHeight(34);
        prikaziDruguScenu.setPrefWidth(88);
        anchorPane.getChildren().add(prikaziDruguScenu);


        //Kreiranje druge scene
        StackPane root2 = new StackPane();
        Scene scene2 = new Scene(root2, 670, 690);
        AnchorPane anchorPane2 = new AnchorPane();
        //TilePane ostatakReda = new TilePane();
        ostatakReda.setLayoutX(179);
        ostatakReda.setLayoutY(105);
        //ostatakReda.setVgap(1);
        ostatakReda.setHgap(1);
        root2.getChildren().add(anchorPane2);
        anchorPane2.getChildren().add(ostatakReda);

        //Pozadinska slika za scenu 2
        BackgroundImage backgroundImage2 = new BackgroundImage(
                new javafx.scene.image.Image("background-2.png", 670, 690, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background2 = new Background(backgroundImage2);
        root2.setBackground(background2);

        //kreiranje buttona za prelazak na trecu scenu
        Button prikaziTrecuScenu = new Button("Treca scena");
        prikaziTrecuScenu.setLayoutX(536);
        prikaziTrecuScenu.setLayoutY(14);
        prikaziTrecuScenu.setPrefHeight(34);
        prikaziTrecuScenu.setPrefWidth(88);
        anchorPane2.getChildren().add(prikaziTrecuScenu);



        //Kreiranje trece scene
        StackPane root3 = new StackPane();
        AnchorPane anchorPane3 = new AnchorPane();
        Scene scene3 = new Scene(root3, 670, 690);
        root3.getChildren().add(anchorPane3);

        //kreiranje buttona za prelazak na prvu scenu
        Button prikaziPrvuScenu = new Button("Prva scena");
        prikaziPrvuScenu.setLayoutX(536);
        prikaziPrvuScenu.setLayoutY(14);
        prikaziPrvuScenu.setPrefHeight(34);
        prikaziPrvuScenu.setPrefWidth(88);
        anchorPane3.getChildren().add(prikaziPrvuScenu);

        //kreiranje buttona za pauzu
        Button pauzaButton = new Button("Start/Pauza");
        pauzaButton.setLayoutX(395);
        pauzaButton.setLayoutY(14);
        pauzaButton.setPrefHeight(34);
        pauzaButton.setPrefWidth(88);
        anchorPane.getChildren().add(pauzaButton);

        //Dodavanje TilePane na trecu scenu
        kaznjeni.setLayoutX(179);
        kaznjeni.setLayoutY(107);
        kaznjeni.setVgap(7);
        kaznjeni.setHgap(1);
        anchorPane3.getChildren().add(kaznjeni);

        //kreiranje stoperice
        timeText = new Text("00:00");
        timeText.setStyle("-fx-font-size: 25;");
        timeText.setLayoutX(305);
        timeText.setLayoutY(120);

        anchorPane.getChildren().add(timeText);

        timer = new Timer(true);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(!Simulacija.pauza) {
                    updateTime();
                }
            }
        }, 1000, 1000);


        //logika za buttone
        prikaziDruguScenu.setOnAction(event -> stage.setScene(scene2));
        prikaziTrecuScenu.setOnAction(event -> stage.setScene(scene3));
        prikaziPrvuScenu.setOnAction(event -> stage.setScene(scene));
        pauzaButton.setOnAction(event -> {
            Simulacija.pauza = !Simulacija.pauza;
            synchronized (Simulacija.lock){
                if(!Simulacija.pauza){
                    Simulacija.lock.notifyAll();
                }
            }
        });

        stage.show();


//        for (Vozilo vozilo :
//                Simulacija.granicniRed) {
//            vozilo.start();
//        }
    }

    public static void main(String[] args) {

        ArrayList<Vozilo> probniRed = new ArrayList<>();

        for (int i = 0; i < Simulacija.BROJ_LICNIH_VOZILA; i++) {
            probniRed.add(new LicnoVozilo());
        }
        for (int i = 0; i < Simulacija.BROJ_AUTOBUSA; i++) {
            probniRed.add(new Autobus());
        }
        for (int i = 0; i < Simulacija.BROJ_KAMIONA; i++) {
            probniRed.add(new Kamion());
        }

        Collections.shuffle(probniRed);
        new TerminalWatcher().start();

        for (Vozilo vozilo :
                probniRed) {
            dodajVozilo(vozilo);
        }

        Simulacija.granicniRed.peek().start();
        launch(args);
        Simulacija.pauza = false;
        synchronized (Simulacija.lock){
            Simulacija.lock.notifyAll();
        }
    }

    public static void dodajVozilo(Vozilo vozilo) {
        Simulacija.granicniRed.add(vozilo);
        StackPane novoVozilo = new StackPane();
        // Dodajte iste komponente kao i postojeći StackPane
        Image imageAutomobil = new Image("automobil.png");
        Image imageAutobus = new Image("autobus.png");
        Image imageKamion = new Image("kamion.png");
        ImageView imageView = new ImageView();

        if(vozilo instanceof LicnoVoziloInterfejs){
            imageView.setImage(imageAutomobil);
        } else if (vozilo instanceof KamionInterfejs) {
            imageView.setImage(imageKamion);
        } else if (vozilo instanceof AutobusInterfejs) {
            imageView.setImage(imageAutobus);
        }
        // Add a click event handler to the ImageView
        imageView.setOnMouseClicked(event -> {
            // Create and configure an Alert dialog
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacije o vozilu");
            alert.setHeaderText(null);
            alert.setContentText(vozilo.toString());

            // Show the Alert dialog
            alert.showAndWait();
        });
        novoVozilo.getChildren().addAll(imageView);
        if(Simulacija.granicniRed.size() <= 5)
        {
            pocetakReda.getChildren().add(novoVozilo);
        }
        else{
            novoVozilo.setRotate(270);
            ostatakReda.getChildren().add(novoVozilo);
        }
    }
    public static void pomjeriVozilaNaPolicijski(int terminal) {
        //1 - policijski terminal 1
        //2 - policijski terminal 2
        //3- policijski terminal za kamione

        Platform.runLater(() -> {

            StackPane voziloNaTerminalu = null;
            StackPane voziloNaPocetniRed = null;
            for (Node node : pocetakReda.getChildren()) {
                if (node instanceof StackPane) {
                    voziloNaTerminalu = (StackPane) node;
                    break;
                }
            }

            for (Node node : ostatakReda.getChildren()) {
                if (node instanceof StackPane) {
                    voziloNaPocetniRed = (StackPane) node;
                    break;
                }
            }

            // Ako smo pronašli StackPane unutar VBox-a, premjestite ga
            // u StackPane s tekstom "P1"
            if (voziloNaTerminalu != null) {
                if (terminal == 1) {
                    policijskiTerminal1.getChildren().add(voziloNaTerminalu);

                } else if (terminal == 2) {
                    policijskiTerminal2.getChildren().add(voziloNaTerminalu);
                } else if (terminal == 3) {
                    policijskiTerminalK.getChildren().add(voziloNaTerminalu);
                }
                StackPane.setMargin(voziloNaTerminalu, new Insets(30, 0, 0, 40));
            }
            if (voziloNaPocetniRed != null) {
                voziloNaPocetniRed.setRotate(0);
                pocetakReda.getChildren().add(voziloNaPocetniRed);
            }
        });
    }

    public static void pomjeriVozilaNaCarinski(int saTerminala, int naTerminal) {
        //saTerminala
        //1 - policijski terminal 1
        //2 - policijski terminal 2
        //3- policijski terminal za kamione
        //naTerminal
        //1 - carinski terminal 1
        //2 - carinski terminal za kamione

        Platform.runLater(() -> {

            StackPane voziloNaPolicijskomTerminalu = null;
            if(saTerminala == 1)
            {
                for (Node node : policijskiTerminal1.getChildren()) {
                    if (node instanceof StackPane) {
                        voziloNaPolicijskomTerminalu = (StackPane) node;
                        break;
                    }
                }
            } else if (saTerminala == 2) {
                for (Node node : policijskiTerminal2.getChildren()) {
                    if (node instanceof StackPane) {
                        voziloNaPolicijskomTerminalu = (StackPane) node;
                        break;
                    }
                }
            }
            else if (saTerminala == 3) {
                for (Node node : policijskiTerminalK.getChildren()) {
                    if (node instanceof StackPane) {
                        voziloNaPolicijskomTerminalu = (StackPane) node;
                        break;
                    }
                }
            }

            // Ako smo pronašli StackPane unutar VBox-a, premjestite ga
            // u StackPane s tekstom "P1"
            if (voziloNaPolicijskomTerminalu != null) {
                if (naTerminal == 1) {
                    carinskiTerminal1.getChildren().add(voziloNaPolicijskomTerminalu);
                } else if (naTerminal == 2) {
                    carinskiTerminalK.getChildren().add(voziloNaPolicijskomTerminalu);
                }
            }
        });
    }

    public static void pomjeriNaTrecuScenu(int saTerminala) {
        //saTerminala
        //1 - policijski terminal 1
        //2 - policijski terminal 2
        //3- policijski terminal za kamione
        //4 - carinski terminal za kamione

        Platform.runLater(() -> {


            // Pronađite StackPane unutar VBox-a
            StackPane kaznjenoVozilo = null;
            if(saTerminala == 1)
            {
                for (Node node : policijskiTerminal1.getChildren()) {
                    if (node instanceof StackPane) {
                        kaznjenoVozilo = (StackPane) node;
                        break;
                    }
                }
            } else if (saTerminala == 2) {
                for (Node node : policijskiTerminal2.getChildren()) {
                    if (node instanceof StackPane) {
                        kaznjenoVozilo = (StackPane) node;
                        break;
                    }
                }
            }
            else if (saTerminala == 3) {
                for (Node node : policijskiTerminalK.getChildren()) {
                    if (node instanceof StackPane) {
                        kaznjenoVozilo = (StackPane) node;
                        break;
                    }
                }
            }
            else if (saTerminala == 4) {
                for (Node node : carinskiTerminalK.getChildren()) {
                    if (node instanceof StackPane) {
                        kaznjenoVozilo = (StackPane) node;
                        break;
                    }
                }
            }


            if (kaznjenoVozilo != null) {
                    kaznjeni.getChildren().add(kaznjenoVozilo);
            }
        });
    }

    public static void izbrisiVozilo(int saTerminala) {
        //saTerminala
        //1 - carinski terminal 1
        //2 - carinski terminal za kamione

        Platform.runLater(() -> {


            // Pronađite StackPane unutar VBox-a
            StackPane prihvatajuceVozilo = null;
            if(saTerminala == 1)
            {
                for (Node node : carinskiTerminal1.getChildren()) {
                    if (node instanceof StackPane) {
                        carinskiTerminal1.getChildren().remove(node);
                        break;
                    }
                }
            } else if (saTerminala == 2) {
                for (Node node : carinskiTerminalK.getChildren()) {
                    if (node instanceof StackPane) {
                        carinskiTerminalK.getChildren().remove(node);
                        break;
                    }
                }
            }

        });
    }

    private void updateTime() {
        seconds++;
        if (seconds == 60) {
            seconds = 0;
            minutes++;
        }

        String timeString = String.format("%02d:%02d", minutes, seconds);
        timeText.setText(timeString);
    }
}