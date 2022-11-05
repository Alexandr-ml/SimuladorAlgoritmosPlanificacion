package com.grupo8.algoritmos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SSTF extends AbstractAlgoritmo{
    private ArrayList<Integer> listaPeticionesProcesadas = new ArrayList<>();

    public SSTF(ArrayList<Integer> peticiones) {
        super(peticiones);
    }


    @Override
    public void procesar(){
        int peticionActual = listaPeticiones.get(0);
        listaPeticiones.remove(0);
        listaPeticionesProcesadas.add(peticionActual);
        int siguientePeticion = -1;

        while (!listaPeticiones.isEmpty()){
            siguientePeticion = siguientePeticionDistanciaMin(listaPeticiones,peticionActual);
            listaPeticionesProcesadas.add(siguientePeticion);
            peticionActual = siguientePeticion;

        }

    }


    public int siguientePeticionDistanciaMin(ArrayList<Integer> peticiones,int peticionActual){
        int siguientePeticion = -1;
        int distanciaMinima = Integer.MAX_VALUE;
        int indicePeticionConMenorDistancia = -1;

        for(int j = 0;j<peticiones.size();j++){
            int diff = Math.abs(peticionActual - peticiones.get(j));


            if(diff != 0 && diff < distanciaMinima){
                siguientePeticion = peticiones.get(j);
                indicePeticionConMenorDistancia = j;
                distanciaMinima = diff;
            }
        }

        peticiones.remove(indicePeticionConMenorDistancia);

        return siguientePeticion;
    }





    @Override
    public ArrayList<Integer> getListaPeticionesProcesadas(){
        return listaPeticionesProcesadas;
    }
}
