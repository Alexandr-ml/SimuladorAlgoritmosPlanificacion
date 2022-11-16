package com.grupo8.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class DistanciaEntrePistas {



    public static  ObservableList<Par> procesarPistas(List<Integer> pistas){
        ObservableList<Par> listaPares = FXCollections.observableArrayList();
        Par par;
        int actual,anterior = 0,distancia = 0;

        for(int i = 0;i < pistas.size();i++){


            actual = pistas.get(i);

            if(i == 0){
                par = new Par(actual,0);
            } else if ( i-1 == pistas.size()) {
                par = new Par(actual,0);
            }else {
                distancia = Math.abs(actual-anterior);
                par =  new Par(actual,distancia);
            }

            anterior = actual;

            listaPares.add(par);

        }


        return listaPares;
    }

}
