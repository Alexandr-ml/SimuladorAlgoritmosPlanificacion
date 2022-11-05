package com.grupo8.algoritmos;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public  class AbstractAlgoritmo {
    protected ArrayList<Integer> listaPeticiones;

    protected AbstractAlgoritmo(ArrayList<Integer> peticiones){
        listaPeticiones = peticiones;
    }

    //Debe ser implementado
    public void procesar(){}



    public  ArrayList<Integer> getListaPeticiones(){
        return listaPeticiones;
    }


    //Debe ser sobreescrito
    public ArrayList<Integer> getListaPeticionesProcesadas(){

        return null;
    }

}
