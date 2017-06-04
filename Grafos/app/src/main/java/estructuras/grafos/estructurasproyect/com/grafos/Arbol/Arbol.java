package estructuras.grafos.estructurasproyect.com.grafos.Arbol;

/**
 * Created by Usuario on 3/6/2017.
 */

public class Arbol {

    public int    cedula;
    public String nombre;
    public Arbol izq;
    public Arbol der;
    //puntero tipo grafo

    public Arbol(int cedula,String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
        izq = der = null;
    }
}
