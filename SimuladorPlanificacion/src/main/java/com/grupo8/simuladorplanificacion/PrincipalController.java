package com.grupo8.simuladorplanificacion;

import javafx.animation.FadeTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class PrincipalController implements Initializable, EventHandler<ActionEvent> {
    private int numPistas;
    @FXML
    Button btnEjecutarSimulacion,btnVerEstadisticas;

    @FXML
    Canvas canvas;

    @FXML
    ChoiceBox<String> cbAlgoritmos;

    @FXML
    TextField txtListadoPeticiones;

    @FXML
    Spinner<Integer> spnNumPistas;
    @FXML
    Button btnNumPistas;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> listadoAlgoritmos = new ArrayList<>();
        listadoAlgoritmos.add("FCFS");
        listadoAlgoritmos.add("SSTF");
        listadoAlgoritmos.add("SCAN");
        listadoAlgoritmos.add("C-SCAN");
        listadoAlgoritmos.add("LOOK");
        listadoAlgoritmos.add("C-LOOK");

        spnNumPistas.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(100,400,100,100));
        spnNumPistas.setPromptText("Max. 400 pistas");


        spnNumPistas.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                numPistas =  Integer.parseInt(newValue);
            }
        });

        cbAlgoritmos.setItems(FXCollections.observableArrayList(listadoAlgoritmos));
        cbAlgoritmos.setOnAction(this);

        //Hacer funcion que traslade los puntos
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(2);
        double ancho = canvas.getWidth();
        double alto = canvas.getHeight();

        gc.strokeLine(20,20,20,alto);
        gc.strokeLine(20,20,alto,20);

        gc.fillText("0",10,15);

        for(int i = 1 ;i<=10;i++){
            gc.strokeLine(i*40+20,15,i*40+20,25);
        }

        for(int i = 1 ;i<=10;i++){
            gc.strokeLine(15,i*40+20,25,i*40+20);
        }

        //Metodo para limpiar al canvas
        //gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());

    }



    @Override
    public void handle(ActionEvent event) {
        //System.out.println(((ChoiceBox<String>)event.getSource()).getSelectionModel().getSelectedItem());

        boolean estaDentroIntervaloPeticiones, esListaNumeros;

        estaDentroIntervaloPeticiones = estaDentroDeIntervaloPeticiones();

        esListaNumeros = esListaNumeros();

        if( esListaNumeros) {
            if(estaDentroIntervaloPeticiones){
                btnEjecutarSimulacion.setDisable(false);
            }
            else{
                deseleccionarAlgoritmo();
                new Alert(Alert.AlertType.ERROR,"Introduzca valores en el intervalo 0 a "+(numPistas-1),ButtonType.CLOSE)
                        .showAndWait();

            }
        }
        else {
            btnEjecutarSimulacion.setDisable(true);
            deseleccionarAlgoritmo();
            new Alert(Alert.AlertType.ERROR,"Introduzca valores validos en el listado de peticiones.",ButtonType.CLOSE)
                    .showAndWait();
        }


    }


    @FXML
    public void onClickBtnSetNumeroPistas(){

        txtListadoPeticiones.setDisable(false);
        numPistas = spnNumPistas.getValue();
        int saltos = numPistas/10;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(40,0,400,15);

        for(int i = 0;i<=10;i++){
            if(i!=0 && i*saltos < numPistas) gc.fillText(String.valueOf(i*saltos),40*i+20-i,10);
            else if (i*saltos == numPistas)  gc.fillText(String.valueOf(i*saltos-1),40*i+20-i,10);

        }

        StringBuilder stringBuilder = new StringBuilder("Ejemplo: ");
        for(int i = 0;i<4;i++){
            stringBuilder.append((int) (Math.random()*numPistas)).append(",");
        }

        stringBuilder.setCharAt(stringBuilder.length()-1,' ');

        txtListadoPeticiones.setPromptText(stringBuilder.toString());

    }


    @FXML
    public void onClickCorrerSimulacion(){
        if (esListaNumeros()){
            if (estaDentroDeIntervaloPeticiones()){

            }else {
                deseleccionarAlgoritmo();
                btnEjecutarSimulacion.setDisable(true);

            }
        }else {
            deseleccionarAlgoritmo();
        }
        txtListadoPeticiones.setDisable(false);
    }


    public boolean esListaNumeros(){
        return txtListadoPeticiones.getText().matches("^\\d+(?:[ \\t]*,[ \\t]*\\d+)+$");
    }

    public boolean estaDentroDeIntervaloPeticiones(){
        return  Arrays.stream(txtListadoPeticiones.getText().split(","))
                .mapToInt(Integer::parseInt)
                .allMatch(i -> i > 0 && i < numPistas);
    }

    public void deseleccionarAlgoritmo(){
        cbAlgoritmos.getSelectionModel().select(-1);
    }
}