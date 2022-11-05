package com.grupo8.algoritmos;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SSTFTest {

    @Test
    void procesar() {
        List<Integer> lista = List.of(100,95,500,90,80,50);
        ArrayList<Integer> listaP = new ArrayList<>();
        listaP.addAll(lista);


        SSTF sstf = new SSTF(listaP);
        sstf.procesar();

        sstf.getListaPeticionesProcesadas().forEach(System.out::println);


    }
}