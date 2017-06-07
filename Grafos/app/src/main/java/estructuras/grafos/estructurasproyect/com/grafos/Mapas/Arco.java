package estructuras.grafos.estructurasproyect.com.grafos.Mapas;

import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by Admi on 29/5/2017.
 */

public class Arco {
    public int peso;
    public PolylineOptions arcoGrafico;
    public Vertice destino;
    public Arco sigArco;

    public Arco(int peso, PolylineOptions arcoGrafico, Vertice destino) {
        this.peso = peso;
        this.arcoGrafico = arcoGrafico;
        this.destino = destino;
        this.sigArco = null;
    }


}
