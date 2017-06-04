package estructuras.grafos.estructurasproyect.com.grafos.Mapas;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Admi on 29/5/2017.
 */

public class Vertice {
    public String nombre;
    public boolean marca;
    public LatLng ubicacion;
    public Vertice sigVertice;
    public Arco sigA;

    public Vertice(String nombre, LatLng ubicacion) {
        this.nombre = nombre;
        this.marca = false;
        this.ubicacion = ubicacion;
        this.sigVertice = null;
        this.sigA = null;
    }
}
