package com.grupo8.algoritmos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SCAN extends AbstractAlgoritmo{
    private List<Integer> derecha;
    private List<Integer> izquierda;
    private ArrayList<Integer> peticionesProcesadas = new ArrayList<>();
    private int ultimaPista;


    public SCAN(ArrayList<Integer> peticiones,int ultimaPista) {

        super(peticiones);
        this.ultimaPista = ultimaPista;
    }

    @Override
    public void procesar(){



        int peticionInicial = listaPeticiones.get(0);
        List<Integer> peticionesOrdenadas;

        peticionesOrdenadas = listaPeticiones.stream()
                .sorted()
                .filter(i -> i != ultimaPista)
                .toList();

        derecha = peticionesOrdenadas.stream()
                .filter(i -> i > peticionInicial)
                .toList();

        izquierda = peticionesOrdenadas.stream()
                .filter(i -> i < peticionInicial)
                .collect(Collectors.toCollection(ArrayList::new));

        Collections.reverse(izquierda);

        peticionesProcesadas.add(peticionInicial);
        peticionesProcesadas.addAll(derecha);
        if(ultimaPista > 0) peticionesProcesadas.add(ultimaPista);

        peticionesProcesadas.addAll(izquierda);

    }


    @Override
    public ArrayList<Integer> getListaPeticionesProcesadas(){
        return peticionesProcesadas;
    }
}
