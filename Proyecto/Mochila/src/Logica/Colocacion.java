package Logica;

import Dominio.Item;
import Dominio.Mochila;

import java.util.ArrayList;
import java.util.List;

public class Colocacion {

    Mochila mochila;
    private List<Item> items;
    private int cantidadItems;
    private int escogidos[];
    private int orden[];
    private double probEs;
    private char modo;

    public Colocacion(Mochila mochila, List<Item> items, int[] escogidos, int[] orden, char modo){
        this.mochila = mochila;
        this.items = items;
        this.cantidadItems = items.size();
        this.escogidos = escogidos;
        this.orden = orden;
        this.modo = modo;
        int totalItems = 0;
        for(int i = 0; i < cantidadItems; i++)
            totalItems += items.get(i).puntos();
        this.probEs = (mochila.getTotal()+0.0)/(totalItems+0.0);
        this.probEs = 0.5;
    }

    // Genera arrays de escogidos y orden aleatorios;
    public Colocacion(Mochila mochila, List<Item> items){

        this.mochila = mochila;
        int max = mochila.getTotal();
        this.items = items;
        this.cantidadItems = items.size();
        escogidos = new int[cantidadItems];
        orden = new int[cantidadItems];
        int totalItems = 0;
        for(int i = 0; i < cantidadItems; i++)
            totalItems += items.get(i).puntos();
        this.probEs = (mochila.getTotal()+0.0)/(totalItems+0.0);

        double n;

        n = Math.random();

        if(n<0.25)
            this.modo = 'a';
        else if(n<0.5)
            this.modo = 'b';
        else if(n<0.75)
            this.modo = 'r';
        else
            this.modo = 'z';

        for(int i = 0; i < cantidadItems; i++){
            this.escogidos[i] = 0;
        }

        List<Integer> numeros = new ArrayList<Integer>();
        for(int i = 0; i < cantidadItems; i++)
            numeros.add(i);

        //System.out.println(numeros.toString());
        int j;
        // String ordenString = "[";
        for(int i = 0; i < cantidadItems; i++){
            j = (int)Math.floor((numeros.size()*Math.random()));
            orden[i] = numeros.get(j);
            numeros.remove(j);
            //System.out.println(numeros.toString());
            //ordenString += orden[i]+",";
            //System.out.println(ordenString);
        }
    }

    public Colocacion(Colocacion col){
        this.mochila = col.getMochila();
        this.items = col.getItems();
        this.cantidadItems = items.size();
        this.escogidos = col.getEscogidos();
        this.orden = col.getOrden();
        this.modo = col.getModo();
    }

    public char getModo() {
        return modo;
    }

    public Mochila getMochila() {
        return mochila;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getCantidadItems() {
        return cantidadItems;
    }

    public int[] getEscogidos() {
        return escogidos;
    }

    public int[] getOrden() {
        return orden;
    }

    public boolean valida(){

        int pos = 0;
        mochila.vaciar();

        if(modo == 'a'){
            for(int i = 0; i < cantidadItems; i++){
                pos = 0;
                for(int j = 0; orden[j] != i; j++){
                    pos += 1;
                }
                if(escogidos[pos] == 1){
                    if(Posicion.primeraPosicionPosible(mochila, items.get(pos)) == null) {
                        return false;
                    }
                    else{
                        mochila.colocarItem(Posicion.primeraPosicionPosible(mochila, items.get(pos)), items.get(pos));
                    }
                }
            }
        }
        else if(modo == 'b'){
            for(int i = 0; i < cantidadItems; i++){
                pos = 0;
                for(int j = 0; orden[j] != i; j++){
                    pos += 1;
                }
                if(escogidos[pos] == 1){
                    if(Posicion.ultimaPosicionPosible(mochila, items.get(pos)) == null) {
                        return false;
                    }
                    else{
                        mochila.colocarItem(Posicion.ultimaPosicionPosible(mochila, items.get(pos)), items.get(pos));
                    }
                }
            }
        }
        else if(modo == 'r'){
            for(int i = 0; i < cantidadItems; i++){
                pos = 0;
                for(int j = 0; orden[j] != i; j++){
                    pos += 1;
                }
                if(escogidos[pos] == 1){
                    if(Posicion.primeraPosicionPosible(mochila, items.get(pos)) == null) {
                        return false;
                    }
                    else{
                        mochila.colocarItem(Posicion.randomPosicionPosible(mochila, items.get(pos)), items.get(pos));
                    }
                }
            }
        }
        else if(modo == 'z') {
            boolean alter = true;
            for(int i = 0; i < cantidadItems; i++){
                pos = 0;
                for(int j = 0; orden[j] != i; j++){
                    pos += 1;
                }
                if(escogidos[pos] == 1){
                    if(Posicion.primeraPosicionPosible(mochila, items.get(pos)) == null) {
                        return false;
                    }
                    else{
                        if(alter)
                            mochila.colocarItem(Posicion.primeraPosicionPosible(mochila, items.get(pos)), items.get(pos));
                        else
                            mochila.colocarItem(Posicion.ultimaPosicionPosible(mochila, items.get(pos)), items.get(pos));
                        alter = !alter;
                    }
                }
            }
        }

        return true;
    }

    public String mochilaLlena(){

        this.valida();
        return mochila.mostrar();
    }

    public void mutarEscogidos(double prob){

        double n;
        for(int i = 0; i < cantidadItems; i++){
            n = Math.random();
            if(n < prob){
                if(escogidos[i] == 1)
                    escogidos[i] = 0;
                else
                    escogidos[i] = 1;
            }
        }
    }

    public void mutarOrden(double prob){
        //System.out.println("MUTACION DEL ORDEN: ");
        //System.out.println(this.logS());
        double n;
        int aux;
        int pos;
        for(int i = 0; i < cantidadItems; i++){
            n = Math.random();
            //System.out.println("n: "+ n);
            if(n < prob){
                n = Math.random()*cantidadItems;
                //System.out.println("n*tot: "+ n);
                pos = (int)Math.floor(n);
                //.out.println("pos: "+ pos);
                aux = orden[pos];
                orden[pos] = orden[i];
                orden[i] = aux;
                //System.out.println(this.logS());
            }
        }
    }

    public void mutarModo(double prob){
        double n = Math.random();
        if(n < prob){
            n = Math.random();

            if(n<0.25)
                this.modo = 'a';
            else if(n<0.5)
                this.modo = 'b';
            else if(n<0.75)
                this.modo = 'r';
            else
                this.modo = 'z';
        }
    }

    public void mutar(double probE, double probO, double probM){
        this.mutarEscogidos(probE);
        this.mutarOrden(probO);
        this.mutarModo(probM);
    }

    public int puntuacion(){

        int puntos = 0;

        if(this.valida()){
            for(int i = 0; i < cantidadItems; i++){
                if(escogidos[i] == 1)
                    puntos += items.get(i).puntos();
            }
            return puntos;
        }
        else
            return 0;
    }

    public int puntuacionProvisional(){

        int puntos = 0;
        for(int i = 0; i < escogidos.length; i++) {
            if (escogidos[i] == 1)
                puntos += items.get(i).puntos();
        }
        return puntos;
    }

    public String log(){
        String log = "Col: valida: "+ this.valida()+" , puntos: "+this.puntuacion() + " , [";
        for(int i = 0; i < cantidadItems; i++) {
            log += escogidos[i];
            if (i+1 < cantidadItems)
                log += ",";
        }
        log += "] , [";
        for(int i = 0; i < cantidadItems; i++) {
            log += orden[i];
            if (i+1 < cantidadItems)
                log += ",";
        }
        log += "]\n" + this.mochilaLlena();

        return log;
    }

    public String logS(){
        String log = "Col: [" + this.modo + "] valida: "+ this.valida()+" , puntos: "+this.puntuacion() + " , [";
        for(int i = 0; i < cantidadItems; i++) {
            log += escogidos[i];
            if (i+1 < cantidadItems)
                log += ",";
        }
        log += "] , [";
        for(int i = 0; i < cantidadItems; i++) {
            log += orden[i];
            if (i+1 < cantidadItems)
                log += ",";
        }
        log += "]\n";

        return log;
    }
}
