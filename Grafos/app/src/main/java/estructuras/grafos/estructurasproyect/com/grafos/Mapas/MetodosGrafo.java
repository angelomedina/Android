package estructuras.grafos.estructurasproyect.com.grafos.Mapas;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import estructuras.grafos.estructurasproyect.com.grafos.Arbol.Arbol;

/**
 * Created by Usuario on 4/6/2017.
 */

public class MetodosGrafo {


    public Vertice grafo;
    public Arbol arbolUsuarioActual;

    public Vertice buscarVertice(String nombre){
        if(grafo == null){
            return null;
        }
        Vertice aux = grafo;

        while(aux != null){
            if(aux.nombre.equals(nombre)){
                return aux;
            }
            aux = aux.sigVertice;
        }
        return null;
    }

    public void InsertarVertice(String nombre, LatLng ubicacion)
    {
        Vertice nuevo=new Vertice(nombre,ubicacion);
        if (grafo == null)
            grafo =nuevo;
        else
        {
            nuevo.sigVertice = grafo;
            grafo =nuevo;
        }
    }

    public void insertA(Vertice origen, Vertice destino, PolylineOptions arcoGrafico, int peso)// metodo para insertar los arcos al final
    {
        if (grafo == null)
        {
            // no haga nada
        }
        if (origen.nombre.equals(destino.nombre)) // el origrn y el destino no puden ser iguales
        {
            // no se permiten bucles
        }
        else
        {
            Arco temp = null;
            Arco nuevo = new Arco(peso,arcoGrafico,destino); // este es la sublista
            nuevo.destino = destino;
            if (origen.sigA == null)
            {
                origen.sigA = nuevo;//apuntando de vertice al incio de la sublista
            }
            else
            {
                Arco anterior = null;
                temp = origen.sigA;
                while ((temp != null) && (temp.destino.nombre != destino.nombre)) //para que no apunte dos veces al mismo destino
                {
                    anterior=temp;
                    temp = temp.sigArco;
                }
                if (temp == null)
                {
                    anterior.sigArco = nuevo;
                }
                else
                {
                    // no se pueden repetir
                }
            }
        }
    }


}
