package com.grupo8.util;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Par {
    private SimpleStringProperty pista;
    private SimpleStringProperty distanciaSig;


    public Par(int pista,int distanciaSig){
       String pistaStr = String.valueOf(pista);
       String distanciaSigStr ;

       if(distanciaSig == 0) distanciaSigStr = "";
       else distanciaSigStr = String.valueOf(distanciaSig);

       this.pista = new SimpleStringProperty(pistaStr);
       this.distanciaSig = new SimpleStringProperty(distanciaSigStr);

    }


    public String getPista() {
        return pista.get();
    }

    public SimpleStringProperty pistaProperty() {
        return pista;
    }

    public String getDistanciaSig() {
        return distanciaSig.get();
    }

    public SimpleStringProperty distanciaSigProperty() {
        return distanciaSig;
    }
}
