package com.grupo8.simuladorplanificacion;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TamañoDiscoController implements Initializable {
    private int numPistas;
    @FXML
    Spinner<Integer> spnNumPistas;
    @FXML
    Spinner<Integer> spnSectoresPista;
    @FXML
    Spinner<Integer> spnNumCabezas;
    @FXML
    Spinner<Integer> spnBytesSector;
    @FXML
    TextField tamanoDisco;
    @FXML
    Button btnCalcular;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numPistas = PrincipalController.getNumPistas();
        spnNumCabezas.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,8,1));
        spnBytesSector.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,1000,512));
        spnSectoresPista.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,1000,1));
        spnNumPistas.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,1000,numPistas));

    }


    @FXML
    public void onClickCalcular(){

        int tamano = (spnNumCabezas.getValue()*spnBytesSector.getValue()*spnSectoresPista.getValue()*spnNumPistas.getValue())/(1024*1024);

        tamanoDisco.setText(tamano+" Gb");

    }





}
