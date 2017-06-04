package estructuras.grafos.estructurasproyect.com.grafos.Mapas;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import estructuras.grafos.estructurasproyect.com.grafos.R;

public class MapsActivityCargaGrafo extends FragmentActivity
        implements
        OnMapReadyCallback,
        DialogInfoRuta.DialogListener
        {


    //#################################### VARIABLES ##########################################################
    //Gmaps
    private GoogleMap mMap;
    //Dialogs / Validaciones
    public Vertice grafo = MapsActivityCreaGrafo.grafoCreado;
    public ArrayList<String> lista_vertices = new ArrayList<>();
    public boolean vertices_existentes;
    //Metodos Grafo
    public int pesoSaltoMenor = 0;
    public ArrayList<String> verticesRecorridos = new ArrayList<>();

    //#################################### CONSTRUCTOR ########################################################

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_carga_grafo);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        findViewById(R.id.navigation_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreaDialogoInfoRuta();
            }
        });

        findViewById(R.id.cargar_mapa_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarGrafo();
                Toast.makeText(MapsActivityCargaGrafo.this, "Grafo Cargado", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.save_button_cargaMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MapsActivityCargaGrafo.this, "Guardando..", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //################################### METODOS GMAPS ##########################################################

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    //################################## METODO CARGA EL GRAFO ######################################################

    public void setGrafo(Vertice grafo) {
        this.grafo = grafo;
    }

    public void CargarGrafo(){
        mMap.clear();
        lista_vertices.clear();

        if(grafo == null){
            return;
        }

        Vertice auxVer = grafo;

        while(auxVer != null){
            if(verticesRecorridos.contains(auxVer.nombre) == true){
                mMap.addMarker(new MarkerOptions().position(auxVer.ubicacion).title(auxVer.nombre).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_run_icon)));
                lista_vertices.add(auxVer.nombre);
                Arco auxAr = auxVer.sigA;
                while(auxAr != null){
                    if(verticesRecorridos.contains(auxAr.destino.nombre) == true){
                        mMap.addPolyline(auxAr.arcoGrafico).setColor(Color.BLUE);
                        auxAr = auxAr.sigArco;
                    }
                    else{
                        mMap.addPolyline(auxAr.arcoGrafico);
                        auxAr = auxAr.sigArco;
                    }
                }
                auxVer = auxVer.sigVertice;

            }else{

                mMap.addMarker(new MarkerOptions().position(auxVer.ubicacion).title(auxVer.nombre).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon)));
                lista_vertices.add(auxVer.nombre);
                Arco auxAr = auxVer.sigA;
                while(auxAr != null){
                    if(verticesRecorridos.contains(auxAr.destino.nombre) == true){
                        mMap.addPolyline(auxAr.arcoGrafico).setColor(Color.BLUE);
                        auxAr = auxAr.sigArco;
                    }
                    else {
                        mMap.addPolyline(auxAr.arcoGrafico);
                        auxAr = auxAr.sigArco;
                    }
                }
                auxVer = auxVer.sigVertice;
            }
        }
        verticesRecorridos.clear();
        pesoSaltoMenor = 0;
    }

//################################## VALIDACION VERTICES ########################################

            public void ValidarExistenciaVertice(String origen, String destino){
                if(lista_vertices.contains(origen) == true && lista_vertices.contains(destino) == true){
                    vertices_existentes = true;
                }
            }

//##################################### METODOS GRAFO ##################################################

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

            public void CaminoCortoPorPeso(Vertice origen , Vertice destino)  // método que llama por boton
            {
                pesoSaltoMenor = 0;
                verticesRecorridos.clear();
                Arco aux = origen.sigA;  // recorre lista de arcos del origen
                while (aux != null  )
                {
                    ArrayList<String> ruta =  new ArrayList<>();
                    ruta.add(origen.nombre);
                    RecursivoCaminoPeso(aux,destino,aux.peso,ruta,origen);
                    aux=aux.sigArco;
                }

                CargarGrafo();
            }

            public void RecursivoCaminoPeso(Arco arco, Vertice destino, int contador, ArrayList<String> ruta, Vertice origen)
            {
                if (arco.destino == destino)  // si concuerda con el destino
                {
                    if(pesoSaltoMenor == 0 || contador < pesoSaltoMenor){
                        pesoSaltoMenor = contador;
                        verticesRecorridos.clear();
                        ClonarArray(ruta);
                    }
                }
                else  /////// si no concuerdda el destino
                {
                    // recorre lista de arcos del otro destino
                    if (arco.destino.sigA != null) // si el destino del arco  tiene mas  arcos
                    {
                        arco = arco.destino.sigA; // variable aux de los arco de destino
                        while (arco != null)
                        {
                            if (arco.destino != origen) // ademas si el arco a usar , el destino sea distinto al origen
                            {   /// condicion para evitar el bucle ABABABABABAC
                                ruta.add(destino.nombre);
                                RecursivoCaminoPeso( arco, destino, contador+arco.peso, ruta,origen);
                            }
                            arco=arco.sigArco;
                        }
                    }
                }
            }

            public void ClonarArray(ArrayList<String> original){
                for(int x = 0; x < original.size(); x++){
                    verticesRecorridos.add(original.get(x));
                }
            }

            public void CaminoCortoPorSalto(Vertice origen , Vertice destino)  // método que llama por boton
            {
                pesoSaltoMenor = 0;
                verticesRecorridos.clear();
                Arco aux = origen.sigA;  // recorre lista de arcos del origen
                while (aux != null  )
                {
                    ArrayList<String> ruta =  new ArrayList<>();
                    ruta.add(origen.nombre);
                    RecursivoCaminoSalto(aux,destino,1,ruta,origen);
                    aux=aux.sigArco;
                }
                CargarGrafo();
            }
            public void RecursivoCaminoSalto(Arco arco, Vertice destino, int contador, ArrayList<String> ruta, Vertice origen)
            {

                if (arco.destino == destino)  // si concuerda con el destino
                {
                    if(pesoSaltoMenor == 0 || contador < pesoSaltoMenor){
                        pesoSaltoMenor = contador;
                        verticesRecorridos.clear();
                        ClonarArray(ruta);
                    }
                }
                else  /////// si no concuerdda el destino
                {
                    // recorre lista de arcos del otro destino
                    if (arco.destino.sigA != null) // si el destino del arco  tiene mas  arcos
                    {
                        arco = arco.destino.sigA; // variable aux de los arco de destino
                        while (arco != null)
                        {
                            if (arco.destino != origen) // ademas si el arco a usar , el destino sea distinto al origen
                            {   /// condicion para evitar el bucle ABABABABABAC
                                ruta.add(destino.nombre);
                                RecursivoCaminoSalto( arco, destino, contador+1, ruta,origen);
                            }
                            arco=arco.sigArco;
                        }
                    }
                }
            }



//############################################# METODOS DE DIALOGS #################################################

    public void CreaDialogoInfoRuta(){
        DialogInfoRuta dialogoPersonalizado = new DialogInfoRuta();
        dialogoPersonalizado.show(getSupportFragmentManager(), "personalizado_arco");

        android.app.Fragment frag = getFragmentManager().findFragmentByTag("personalizado_arco");

        if (frag != null) {
            getFragmentManager().beginTransaction().remove(frag).commit();
        }
    }
            @Override
            public void FinalizaDialogoNewArco(String origen, String destino, String tipo) {
                ValidarExistenciaVertice(origen, destino);

                if(vertices_existentes == true && origen != destino){
                    if(tipo == "Saltos"){
                        CaminoCortoPorSalto(buscarVertice(origen),buscarVertice(destino));
                    }else{
                        CaminoCortoPorPeso(buscarVertice(origen),buscarVertice(destino));
                    }
                } else {
                    Toast.makeText(MapsActivityCargaGrafo.this, "No existe uno o ambos vertices", Toast.LENGTH_SHORT).show();
                }
            }
        }
