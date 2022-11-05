package com.grupo8.algoritmos;

import java.util.ArrayList;
import java.util.List;

public class CSCAN extends AbstractAlgoritmo{
    private List<Integer> derecha;
    private List<Integer> izquierda;
    private ArrayList<Integer> peticionesProcesadas = new ArrayList<>();
    private boolean esCLOOK = false;
    public CSCAN(ArrayList<Integer> peticiones,boolean esCLOOK) {
        super(peticiones);
        this.esCLOOK = esCLOOK;
    }



    @Override
    public void procesar(){
        int peticionInicial = listaPeticiones.get(0);

        List<Integer> peticionesOrdenadas = listaPeticiones.stream().sorted().toList();

        derecha = peticionesOrdenadas.stream()
                .filter(i -> i > peticionInicial)
                .toList();

        izquierda = peticionesOrdenadas.stream()
                .filter(i -> i < peticionInicial)
                .toList();

        peticionesProcesadas.add(peticionInicial);
        peticionesProcesadas.addAll(derecha);

        if(!esCLOOK)peticionesProcesadas.add(0);

        peticionesProcesadas.addAll(izquierda);


    }

    @Override
    public ArrayList<Integer> getListaPeticionesProcesadas() {
        return peticionesProcesadas;
    }
}
