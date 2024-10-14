package Launcher;
import Dominio.Mochila;
import Dominio.Item;
import Logica.AlgoritmoGenetico;
import Logica.Colocacion;
import Logica.Generacion;
import Logica.Posicion;

import java.util.ArrayList;
import java.util.List;

public class Principal {

    public static void main(String[] args){

        Mochila mochila = new Mochila(10,10);
        mochila.mostrar();

        Colocacion colocacion;

        Item p1 = new Item(1,1);
        Item p2 = new Item(2,2);
        Item p3 = new Item(1,2);
        Item p4 = new Item(3,1);
        Item p5 = new Item(2,2);

        Item q1 = new Item(4,2);
        Item q2 = new Item(3,3);
        Item q3 = new Item(4,3);
        Item q4 = new Item(5,1);
        Item q5 = new Item(5,1);

        List<Item> items = new ArrayList<Item>();

        items.add(p1);
        items.add(p2);
        items.add(p3);
        items.add(p4);
        items.add(p5);

        items.add(q1);
        items.add(q2);
        items.add(q3);
        items.add(q4);
        items.add(q5);

        items.add(q1);
        items.add(q2);
        items.add(q3);
        items.add(q4);
        items.add(q5);

        items.add(q1);
        items.add(q2);
        items.add(q3);
        items.add(q4);
        items.add(q5);

        items.add(p1);
        items.add(q5);
        items.add(p2);
        items.add(q4);
        items.add(p3);


        List<Colocacion> colocaciones = new ArrayList<Colocacion>();
        for(int i = 0; i < 10; i++){
            colocaciones.add(new Colocacion(mochila, items));
        }

        Generacion generacion = new Generacion(colocaciones);

        AlgoritmoGenetico alg = new AlgoritmoGenetico(generacion);

        for(int i = 0; i < 100; i++){
            alg.evolucionar();
            System.out.println("Iteracion "+i+": "+alg.logPorcentajeGenActual());
        }

        System.out.println(alg.logS());
        System.out.println(alg.logPuntos());
        System.out.println(alg.getMejorColocacion().logS());
        System.out.println(alg.logMejorSolucion());
    }
}
