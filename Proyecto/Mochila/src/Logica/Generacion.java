package Logica;

import java.util.Collections;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Generacion {

    private List<Colocacion> colocaciones;
    private int poblacion;

    public Generacion(List<Colocacion> colocaciones){
        this.colocaciones = colocaciones;
        poblacion = colocaciones.size();
    }

    public List<Colocacion> colocacionesValidas(){
        List<Colocacion> validas = new ArrayList<Colocacion>();
        for(Colocacion col : colocaciones){
            if(col.valida())
                validas.add(col);
        }
        return validas;
    }

    public List<Colocacion> mitadMejores(){
        List<Colocacion> mejores = new ArrayList<Colocacion>();
        for(Colocacion col : colocaciones){
            if(col.puntuacion() >= this.medianaPuntos())
                mejores.add(col);
        }
        return mejores;
    }

    public int sumaPuntos(){
        int puntos = 0;
        for(Colocacion col : colocaciones)
            puntos += col.puntuacion();
        return puntos;
    }

    public int sumaPuntosMitadMejores(){
        int puntos = 0;
        for(Colocacion col : this.mitadMejores())
            puntos += col.puntuacion();
        return puntos;
    }

    public Colocacion superviviente(){
        double prob = 0;
        double n = Math.random();
        int pos = 0;

        for(Colocacion col : this.mitadMejores()){
            prob += (col.puntuacion()+0.0) / (this.sumaPuntosMitadMejores()+0.0);
            if(n > prob)
                pos++;
        }
        if(pos == colocaciones.size())
            pos--;
        return colocaciones.get(pos);
    }

    public List<Colocacion> getColocaciones() {
        return colocaciones;
    }

    public static Colocacion cruce(Colocacion padre, Colocacion madre){

        int cantidad = padre.getCantidadItems();
        int[] escogidos = new int[cantidad];
        int[] orden = new int[cantidad];
        char modo;

        double n = Math.random();

        for(int i = 0; i < cantidad; i++){
            n = Math.random();
            if(n<0.5)
                escogidos[i] = padre.getEscogidos()[i];
            else
                escogidos[i] = madre.getEscogidos()[i];
        }

        n = Math.random();

        if(n < 0.5){
            for(int i = 0; i < cantidad; i++){
                orden[i] = padre.getOrden()[i];
            }
        }
        else{
            for(int i = 0; i < cantidad; i++){
                orden[i] = madre.getOrden()[i];
            }
        }

        n = Math.random();

        if(n < 0.5){
            modo = padre.getModo();
        }
        else{
            modo = madre.getModo();
        }

        Colocacion hijo = new Colocacion(padre.getMochila(), padre.getItems(), escogidos, orden, modo);

        return hijo;
    }

    public int getPoblacion() {
        return poblacion;
    }

    public int mejorPuntuacion(){
        int max = 0;
        for(Colocacion col : colocaciones){
            if(col.puntuacion() > max)
                max = col.puntuacion();
        }
        return max;
    }

    private double medianaPuntos() {
        List<Integer> total = new ArrayList<Integer>();
        for(Colocacion col : colocaciones)
            total.add(col.puntuacion());
        double j = 0;
        Collections.sort(total);
        int size = total.size();
        if (size % 2 == 1) {
            j = total.get((size - 1) / 2);
        } else {
            j = (total.get(size / 2 - 1) + total.get(size / 2) + 0.0) / 2;
        }
        return j;
    }

    public String log(){
        String s = "";
        for(Colocacion col : colocaciones){
            s += col.log()+"\n";
        }

        return s;
    }

    public String logS(){
        String s = "";
        for(Colocacion col : colocaciones){
            s += col.logS()+"\n";
        }

        return s;
    }
}
