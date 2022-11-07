package com.grupo8.simuladorplanificacion;

import com.grupo8.algoritmos.*;
import javafx.animation.FadeTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class PrincipalController implements Initializable{
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

        if(txtListadoPeticiones.getText().isBlank() || cbAlgoritmos.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Rellene todos los campos necesarios.",ButtonType.CLOSE).showAndWait();
            return;
        }
        if(comprobacion()){
            TaskAlgoritmos taskAlgoritmos = new TaskAlgoritmos(listaPeticiones());
            new Thread(taskAlgoritmos).run();
        }

        txtListadoPeticiones.setDisable(false);
    }


    public boolean esListaNumeros(){
        return txtListadoPeticiones.getText().matches("^\\d+(?:[ \\t]*,[ \\t]*\\d+)+$");
    }

    public boolean estaDentroDeIntervaloPeticiones(){
        return  Arrays.stream(txtListadoPeticiones.getText().split(","))
                .mapToInt(Integer::parseInt)
                .allMatch(i -> i > -1 && i < numPistas);
    }

    public ArrayList<Integer> listaPeticiones(){
        return Arrays.stream(txtListadoPeticiones.getText().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void deseleccionarAlgoritmo(){
        cbAlgoritmos.getSelectionModel().clearSelection();

    }


    public boolean comprobacion(){
        boolean esListaNumeros = esListaNumeros();
        boolean estaDentroIntervaloPeticiones = estaDentroDeIntervaloPeticiones();

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

        return  esListaNumeros && estaDentroIntervaloPeticiones;
    }

    class TaskAlgoritmos extends Task<Map<String, AbstractAlgoritmo>> {
        ArrayList<Integer> peticiones;

        public TaskAlgoritmos(ArrayList<Integer> peticiones){
            this.peticiones = peticiones;
            peticiones.forEach(System.out::println);
        }

        @Override
        protected Map<String, AbstractAlgoritmo> call() throws Exception {
            Map<String,AbstractAlgoritmo> algoritmos = new HashMap<>();


            FCFS fcfs = new FCFS(peticiones);

            SSTF sstf = new SSTF(peticiones);
            SCAN scan = new SCAN(peticiones,numPistas-1);
            CSCAN cscan = new CSCAN(peticiones,false,numPistas-1);
            SCAN LOOK = new SCAN(peticiones,-1);
            CSCAN CLOOK = new CSCAN(peticiones,true,-1);

            fcfs.procesar();
            sstf.procesar();
            scan.procesar();
            cscan.procesar();
            LOOK.procesar();
            CLOOK.procesar();

            algoritmos.put("FCFS",fcfs);
            algoritmos.put("SSTF",sstf);
            algoritmos.put("SCAN",scan);
            algoritmos.put("C-SCAN",cscan);
            algoritmos.put("LOOK",LOOK);
            algoritmos.put("C-LOOK",CLOOK);

            return algoritmos;
        }

        @Override
        protected void done() {

            try {
               get().entrySet().stream().forEach(a -> System.out.println("Clave: "+a.getKey()+"\n Valor: "+a.getValue().getListaPeticionesProcesadas()));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

        }
    }


}