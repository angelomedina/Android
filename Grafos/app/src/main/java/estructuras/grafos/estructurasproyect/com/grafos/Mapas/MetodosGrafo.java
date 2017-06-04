package estructuras.grafos.estructurasproyect.com.grafos.Mapas;

import java.util.ArrayList;

/**
 * Created by Usuario on 4/6/2017.
 */

public class MetodosGrafo {



    //Grafo
    public Vertice grafoCreado;
    public ArrayList<String> lista_vertices_agregados = new ArrayList<>();
    public boolean vertice_existente;


    public Vertice buscarVertice(String nombre){
        if(grafoCreado == null){
            return null;
        }
        Vertice aux = grafoCreado;

        while(aux != null){
            if(aux.nombre.equals(nombre)){
                return aux;
            }
            aux = aux.sigVertice;
        }
        return null;
    }

    public void InsertarVertice(Vertice nuevoV){
        if (grafoCreado == null){
            grafoCreado = nuevoV;
            //Toast.makeText(MapsActivityCreaGrafo.this, "Agregado (Raiz)", Toast.LENGTH_SHORT).show();
            return;
        }
        Vertice aux = grafoCreado;
        while (aux != null){
            if (aux.sigVertice == null){
                aux.sigVertice = nuevoV;
                //.makeText(MapsActivityCreaGrafo.this, "Agregado!", Toast.LENGTH_SHORT).show();
                return;
            }
            aux = aux.sigVertice;
        }
    }

    public void InsertarArco(String nombre, Arco nuevoA){
        if (grafoCreado == null){
            return;
        }
        Vertice auxV = grafoCreado;

        while(auxV != null){
            if(auxV.nombre.equals(nombre)){
                if(auxV.sigA == null){
                    auxV.sigA = nuevoA;
                    //Toast.makeText(MapsActivityCreaGrafo.this, "Arco primero", Toast.LENGTH_SHORT).show();
                    return;
                }

                Arco auxA = auxV.sigA;

                while (auxA != null){
                    if(auxA.sigArco == null){
                        auxA.sigArco = nuevoA;
                        //Toast.makeText(MapsActivityCreaGrafo.this, "Agregado!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    auxA = auxA.sigArco;
                }
            }
            auxV = auxV.sigVertice;
        }
    }



    //insertar vertce

    // modificar

     //eliminar

    //insertar arco

    // tipo de grafo

    // camino ma corto

    // conexo

    // ciclico

    // llegar de A a B

    // vertices sumideros

    // vertices fuentes

    // grado externo

    // grado interno

}
