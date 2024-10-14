package Logica;

import Dominio.Item;
import Dominio.Mochila;

import java.util.ArrayList;
import java.util.List;

public class Posicion {
    private int x;
    private int y;
    private boolean girado;

    public Posicion(int x, int y, boolean girado){
        this.x = x;
        this.y = y;
        this.girado = girado;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isGirado() {
        return girado;
    }

    public static List<Posicion> posicionesPosibles(Mochila mochila, Item item){

        List<Posicion> posiciones = new ArrayList<Posicion>();

        for(int i = 0; i < mochila.getSizeX(); i++){
            for(int j = 0; j < mochila.getSizeY(); j++){
                if(mochila.cabe(i,j,item))
                    posiciones.add(new Posicion(i,j,false));
                if(mochila.cabeGirado(i,j,item))
                    posiciones.add(new Posicion(i,j,true));
            }
        }

        return posiciones;
    }

    public static Posicion primeraPosicionPosible(Mochila mochila, Item item){

        for(int i = 0; i < mochila.getSizeX(); i++){
            for(int j = 0; j < mochila.getSizeY(); j++){
                if(mochila.cabe(i,j,item))
                    return new Posicion(i, j, false);
                else if(mochila.cabeGirado(i,j,item))
                    return new Posicion(i, j, true);
            }
        }

        return null;
    }

    public static Posicion ultimaPosicionPosible(Mochila mochila, Item item){

        for(int i = mochila.getSizeX() - 1; i >= 0; i--){
            for(int j = mochila.getSizeY() - 1; j >= 0 ; j--){
                if(mochila.cabe(i,j,item))
                    return new Posicion(i, j, false);
                else if(mochila.cabeGirado(i,j,item))
                    return new Posicion(i, j, true);
            }
        }

        return null;
    }

    public static Posicion randomPosicionPosible(Mochila mochila, Item item){
        return posicionesPosibles(mochila, item).get((int) Math.floor(Math.random()*posicionesPosibles(mochila, item).size()));
    }
}
