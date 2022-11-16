package com.grupo8.simuladorplanificacion;

import com.grupo8.algoritmos.*;
import com.grupo8.util.DistanciaEntrePistas;
import com.grupo8.util.Par;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.Inet4Address;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;


public class EstadisticasController implements Initializable {
    public Map<String,AbstractAlgoritmo> algoritmos;
    @FXML
    TextField txtListPeticiones, txtnumpistas, txtresultado;
    @FXML
    TableView<Par> tableFCFS;
    @FXML
    TableColumn<Par,String> colPeticionActFCFS;
    @FXML
    TableColumn<Par,String> colRecorridoFCFS;

    @FXML
     TableView<Par> tableSCAN ;
    @FXML
    TableColumn<Par,String> colPeticionActSCAN;
    @FXML
    TableColumn<Par,String> colRecorridoSCAN;

    @FXML
    TableView<Par> tableCSCAN;
    @FXML
    TableColumn<Par,String> colPeticionActCSCAN;
    @FXML
    TableColumn<Par,String> colRecorridoCSCAN;

    @FXML
    TableView<Par> tableLOOK;
    @FXML
    TableColumn<Par,String> colPeticionActLOOK;
    @FXML
    TableColumn<Par,String> colRecorridoLOOK;

    @FXML
    TableView<Par> tableCLOOK;
    @FXML
    TableColumn<Par,String> colPeticionActCLOOK;
    @FXML
    TableColumn<Par,String> colRecorridoCLOOK;

    @FXML
    TableView<Par> tableSSTF;
    @FXML
    TableColumn<Par,String> colPeticionActSSTF;
    @FXML
    TableColumn<Par,String> colRecorridoSSTF;

    @FXML
    TextField txtFCFS, txtSSTF, txtSCAN, txtCSCAN, txtLOOK, txtCLOOK;





    @Override
    public void initialize(URL location, ResourceBundle resources) {

        EstadisticasTask estadisticasTask = new EstadisticasTask(PrincipalController.getMapaPeticiones());

        new Thread(estadisticasTask).start();



    }


    class Wrapper implements ObservableValue<Integer>{
    private int valor;

    public  Wrapper(int valor){
        this.valor = valor;
    }

        @Override
        public void addListener(ChangeListener<? super Integer> listener) {

        }

        @Override
        public void removeListener(ChangeListener<? super Integer> listener) {

        }

        @Override
        public Integer getValue() {
            return valor;
        }

        @Override
        public void addListener(InvalidationListener listener) {

        }

        @Override
        public void removeListener(InvalidationListener listener) {

        }
    }


    class EstadisticasTask extends Task<Map<String,ObservableList<Par>>> {
        Map<String,Double> avgAlgoritmos;
        private Map<String,AbstractAlgoritmo> algoritmosMap;
        public EstadisticasTask(Map<String,AbstractAlgoritmo> algoritmosMap){
            this.algoritmosMap = algoritmosMap;


        }

        @Override
        protected Map<String, ObservableList<Par>> call() throws Exception {
            Map<String,ObservableList<Par>> pares = new HashMap<>();
            avgAlgoritmos = new HashMap<>();
            double avg;
            for(var tupla : algoritmosMap.entrySet()){
                String clave = tupla.getKey();
                List<Integer> pistasProcesadas = tupla.getValue().getListaPeticionesProcesadas();
                ObservableList<Par> pista_y_dist = DistanciaEntrePistas.procesarPistas(pistasProcesadas);

                avg = pista_y_dist.stream().filter(a -> !a.distanciaSigProperty().get().isBlank())
                        .mapToInt(a -> Integer.parseInt(a.distanciaSigProperty().get()))
                        .average()
                        .getAsDouble();

                avgAlgoritmos.put(clave,redondear(avg));

                pares.put(clave,pista_y_dist);


            }


            return pares;
        }


        @Override
        public void done(){
            Map<String,ObservableList<Par>> data;
            try {
                data = get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }


            ObservableList<Par> FCFSlist = data.get("FCFS");
            ObservableList<Par> SSTFlist = data.get("SSTF");
            ObservableList<Par> SCANlist = data.get("SCAN");
            ObservableList<Par> CSCANlist = data.get("C-SCAN");
            ObservableList<Par> LOOKlist = data.get("LOOK");
            ObservableList<Par> CLOOKlist = data.get("C-LOOK");

            tableFCFS.setItems(FCFSlist);
            colPeticionActFCFS.setCellValueFactory(param -> param.getValue().pistaProperty());
            colRecorridoFCFS.setCellValueFactory(param -> param.getValue().distanciaSigProperty());

            tableSSTF.setItems(SSTFlist);
            colPeticionActSSTF.setCellValueFactory(param -> param.getValue().pistaProperty());
            colRecorridoSSTF.setCellValueFactory(param -> param.getValue().distanciaSigProperty());

            tableSCAN.setItems(SCANlist);
            colPeticionActSCAN.setCellValueFactory(param -> param.getValue().pistaProperty());
            colRecorridoSCAN.setCellValueFactory(param -> param.getValue().distanciaSigProperty());

            tableCSCAN.setItems(CSCANlist);
            colPeticionActCSCAN.setCellValueFactory(param -> param.getValue().pistaProperty());
            colRecorridoCSCAN.setCellValueFactory(param -> param.getValue().distanciaSigProperty());

            tableLOOK.setItems(LOOKlist);
            colPeticionActLOOK.setCellValueFactory(param -> param.getValue().pistaProperty());
            colRecorridoLOOK.setCellValueFactory(param -> param.getValue().distanciaSigProperty());

            tableCLOOK.setItems(CLOOKlist);
            colPeticionActCLOOK.setCellValueFactory(param -> param.getValue().pistaProperty());
            colRecorridoCLOOK.setCellValueFactory(param -> param.getValue().distanciaSigProperty());

            String tmd = "Tiempo promedio: ";

            txtFCFS.setText(tmd+ avgAlgoritmos.get("FCFS"));
            txtSSTF.setText(tmd+ avgAlgoritmos.get("SSTF"));
            txtSCAN.setText(tmd+avgAlgoritmos.get("SCAN"));
            txtCSCAN.setText(tmd+avgAlgoritmos.get("C-SCAN"));
            txtLOOK.setText(tmd+avgAlgoritmos.get("LOOK"));
            txtCLOOK.setText(tmd+avgAlgoritmos.get("C-LOOK"));


            Map.Entry<String,Double> algoEficiente = avgAlgoritmos.entrySet().stream()
                            .min(new Comparator<Map.Entry<String, Double>>() {
                                @Override
                                public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                                    return (int )(o1.getValue() - o2.getValue());
                                }
                            }).get();

            txtresultado.setText("El algoritmo recomendado es: "+algoEficiente.getKey()+" con tiempo promedio: "+algoEficiente.getValue());


        }

        public double redondear(double avg){

            return BigDecimal.valueOf(avg).setScale(2,RoundingMode.HALF_UP).doubleValue();
        }

    }
}

