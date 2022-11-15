package com.grupo8.simuladorplanificacion;

import com.grupo8.algoritmos.AbstractAlgoritmo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class EstadisticasController implements Initializable {

    @FXML
    TextField txtListPeticiones, txtnumpistas, txtresultado;
    @FXML
    TableView tableFCFS, tableSSTF, tableSCAN, tableCSCAN, tableLOOK, tableCLOOK;
    @FXML
    TextField txtFCFS, txtSSTF, txtSCAN, txtCSCAN, txtLOOK, txtCLOOK;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}

