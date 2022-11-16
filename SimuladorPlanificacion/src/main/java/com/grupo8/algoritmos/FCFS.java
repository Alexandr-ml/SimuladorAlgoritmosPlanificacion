package com.grupo8.algoritmos;

import java.util.ArrayList;

public class FCFS extends AbstractAlgoritmo{

    public FCFS(ArrayList<Integer> peticiones) {
        super(peticiones);
    }

    @Override
    public ArrayList<Integer> getListaPeticionesProcesadas(){
        return listaPeticiones;
    }



}
