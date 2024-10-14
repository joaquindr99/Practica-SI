package Logica;

import java.util.ArrayList;
import java.util.List;

public class AlgoritmoGenetico {

    private static final double ProbMutEscogidos = 0.005;
    private static final double ProbMutOrden = 0.005;
    private static final double ProbMutModo = 0.05;
    private List<Generacion> generaciones;
    private Generacion generacionActual;
    private int numeroGeneracion;
    private Colocacion mejorColocacion;

    public AlgoritmoGenetico(Generacion generacionInicial){
        this.generaciones = new ArrayList<Generacion>();
        this.generacionActual = generacionInicial;
        this.generaciones.add(generacionInicial);
        this.mejorColocacion = generacionActual.getColocaciones().get(0);
        this.actualizarMejorColocacion();
        numeroGeneracion = 1;
    }

    public void evolucionar(){
        List<Colocacion> hijos = new ArrayList<Colocacion>();
        Colocacion padre, madre;
        Colocacion hijo;

        while(hijos.size() < generacionActual.getPoblacion()){
            padre = generacionActual.superviviente();
            madre = generacionActual.superviviente();
            hijo = new Colocacion(Generacion.cruce(padre, madre));
            //System.out.println("PADRE: "+ padre.logS());
            //System.out.println("MADRE: "+ madre.logS());
            //System.out.println("HIJO SIN MUTAR: "+ hijo.logS());
            hijo.mutar(ProbMutEscogidos, ProbMutOrden, ProbMutModo);
            //System.out.println("HIJO: "+ hijo.logS());
            hijos.add(hijo);
        }

        Generacion generacionNueva = new Generacion(hijos);
        this.generaciones.add(generacionNueva);
        this.generacionActual = generacionNueva;
        this.numeroGeneracion++;

        this.actualizarMejorColocacion();

        //System.out.println(generacionActual.logS());
    }

    public Colocacion mejorColocacionGeneracionActual(){
        Colocacion mejor = generacionActual.getColocaciones().get(0);
        for(Colocacion col : generacionActual.getColocaciones()){
            if(col.puntuacion() > mejor.puntuacion()){
                mejor = col;
            }
        }
        return mejor;
    }

    private void actualizarMejorColocacion(){
        if(mejorColocacionGeneracionActual().puntuacion() > mejorColocacion.puntuacion())
            mejorColocacion = mejorColocacionGeneracionActual();
    }

    public Colocacion getMejorColocacion() {
        return mejorColocacion;
    }

    public String log(){
        String s = "";
        int iterador = 1;
        for(Generacion generacion : generaciones){
            s +=" -------- Generacion " + iterador + " --------\n";
            s += generacion.log();
            s += "Total generación: "+ generacion.sumaPuntos();
            s += "\n -------------------------------\n";
            iterador++;
        }

        return s;
    }

    public String logS(){
        String s = "\n";
        int iterador = 1;
        for(Generacion generacion : generaciones){
            s +=" -------- Generacion " + iterador + " --------\n";
            s += generacion.logS();
            s += "Total generación: "+ generacion.sumaPuntos();
            s += "\n -------------------------------\n\n";
            iterador++;
        }

        return s;
    }

    public String logPuntos(){
        String s = "\n";
        int iterador = 1;
        for(Generacion generacion : generaciones){
            s += "\nPuntos generación " + iterador +": "+ generacion.sumaPuntos() +
                    " (" + (int) Math.floor(100.0*(generacion.sumaPuntos()+0.0)/((generacion.getColocaciones().get(0).getMochila().getTotal()+0.0)*generacion.getPoblacion())) +
                    "%) || Mejor: "+ generacion.mejorPuntuacion()+" ("+ (int) Math.floor(100.0*(generacion.mejorPuntuacion()+0.0)/(generacion.getColocaciones().get(0).getMochila().getTotal()+0.0))+"%)";
            iterador++;
        }
        return s;
    }

    public String logPorcentajeGenActual(){
        return "(" +(int) Math.floor(100.0*(generacionActual.sumaPuntos()+0.0)/((generacionActual.getColocaciones().get(0).getMochila().getTotal()+0.0)*generacionActual.getPoblacion())) + "%) ("+
                (int) Math.floor(100.0*(generacionActual.mejorPuntuacion()+0.0)/(generacionActual.getColocaciones().get(0).getMochila().getTotal()+0.0))+"%)";
    }

    public String logMejorSolucion(){
        return this.mejorColocacion.mochilaLlena();
    }
}
