package com.grupo8.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DistanciaEntrePistasTest {

    @Test
    void procesarPistas() {
        List<Integer> lista = List.of(45,23,67,76,213,67);

        DistanciaEntrePistas.procesarPistas(lista).forEach(n -> System.out.println(n.pistaProperty().get()+" Distancia: "+n.distanciaSigProperty().get()));
    }
}