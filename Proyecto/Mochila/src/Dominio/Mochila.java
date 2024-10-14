package Dominio;

import Logica.Posicion;

public class Mochila {

    private int espacio[][];
    private int sizeX;
    private int sizeY;

    public Mochila(int sizeX, int sizeY){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.espacio = new int[sizeX][sizeY];
        for(int i = 0; i < sizeX; i++){
            for(int j = 0; j < sizeY; j++){
                espacio[i][j] = 0;
            }
        }
    }

    public void vaciar(){
        for(int i = 0; i < sizeX; i++){
            for(int j = 0; j < sizeY; j++){
                espacio[i][j] = 0;
            }
        }
    }

    public boolean libre(int x, int y){
        return espacio[x][y] == 0;
    }

    public boolean cabe(int x, int y, Item item){

        int ancho = item.getAncho();
        int alto = item.getAlto();

        if(x < 0 || y < 0 || alto < 1 || ancho < 1) {
            System.out.println("Parámetros no válidos en cabe().");
            return false;
        }

        if(x + alto > sizeX || y + ancho > sizeY)
            return false;

        for(int i = x; i < x + alto; i++){
            for(int j = y; j < y + ancho; j++){
                if(espacio[i][j] != 0)
                    return false;
            }
        }

        return true;
    }

    public boolean cabeGirado(int x, int y, Item item){

        int alto = item.getAncho();
        int ancho = item.getAlto();

        if(x < 0 || y < 0 || alto < 1 || ancho < 1) {
            System.out.println("Parámetros no válidos en cabe().");
            return false;
        }

        if(x + alto > sizeX || y + ancho > sizeY)
            return false;

        for(int i = x; i < x + alto; i++){
            for(int j = y; j < y + ancho; j++){
                if(espacio[i][j] != 0)
                    return false;
            }
        }

        return true;
    }

    public void colocarItem(Posicion posicion, Item item){

        int x = posicion.getX();
        int y = posicion.getY();

        if(posicion.isGirado()){
            if(this.cabeGirado(x,y,item)){
                int alto = item.getAncho();
                int ancho = item.getAlto();

                for(int i = x; i < x + alto; i++){
                    for(int j = y; j < y + ancho; j++){
                        espacio[i][j] = 1;
                    }
                }
            }
        }

        else{
            if(this.cabe(x,y,item)){
                int ancho = item.getAncho();
                int alto = item.getAlto();

                for(int i = x; i < x + alto; i++){
                    for(int j = y; j < y + ancho; j++){
                        espacio[i][j] = 1;
                    }
                }
            }
        }
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getTotal(){
        return sizeX*sizeY;
    }

    public String mostrar(){
        String s = "+";
        for(int i = 0; i < sizeY; i++){
            s += "-";
        }
        s += "+";
        for(int i = 0; i < sizeX; i++){
            s += "\n|";
            for(int j = 0; j < sizeY; j++){
                s += espacio[i][j];
            }
            s += "|";
        }
        s += "\n+";
        for(int i = 0; i < sizeY; i++){
            s += "-";
        }
        s += "+";

        return s;
    }
}
