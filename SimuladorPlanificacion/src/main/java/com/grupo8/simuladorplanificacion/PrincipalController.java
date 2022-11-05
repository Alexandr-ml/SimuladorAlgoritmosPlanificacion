package com.grupo8.simuladorplanificacion;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class PrincipalController implements Initializable {

    @FXML
    Button btnEjecutarSimulacion,btnVerEstadisticas;

    @FXML
    Canvas canvas;

    @FXML
    ChoiceBox<String> cbAlgoritmos;

    @FXML
    TextField txtListadoPeticiones;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> listadoAlgoritmos = new ArrayList<>();
        listadoAlgoritmos.add("FCFS");
        listadoAlgoritmos.add("SSTF");
        listadoAlgoritmos.add("SCAN");
        listadoAlgoritmos.add("C-SCAN");
        listadoAlgoritmos.add("LOOK");
        listadoAlgoritmos.add("C-LOOK");

        cbAlgoritmos.setItems(FXCollections.observableArrayList(listadoAlgoritmos));

        //Hacer funcion que traslade los puntos
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(2);
        double ancho = canvas.getWidth();
        double alto = canvas.getHeight();

        gc.strokeLine(20,20,20,alto);
        gc.strokeLine(20,20,alto,20);


        for(int i = 1 ;i<=10;i++){
            gc.strokeLine(i*40+20,20,i*40+20,30);
            gc.fillText(String.valueOf(i*10),i*40+10,10);
        }

        for(int i = 1 ;i<=10;i++){
            gc.strokeLine(20,i*40+20,30,i*40+20);
            gc.fillText(String.valueOf(i*10),0,i*40+25);
        }




    }



    }