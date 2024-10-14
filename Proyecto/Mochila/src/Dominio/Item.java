package Dominio;

public class Item {

    private int alto;
    private int ancho;

    public Item(int alto, int ancho){
        this.alto = alto;
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public int getAncho() {
        return ancho;
    }

    public int puntos(){
        return alto*ancho;
    }
}
