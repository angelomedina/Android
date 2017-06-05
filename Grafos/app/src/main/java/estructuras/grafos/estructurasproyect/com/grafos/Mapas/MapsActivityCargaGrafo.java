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
    //Ruta Corta
    public ArrayList<String> recorridos = new ArrayList<>();
    public ArrayList<String> recorridosRespaldo = new ArrayList<>();

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
                if(grafo == null){
                    return;
                }
                Vertice auxVer = grafo;
                while(auxVer != null){
                    if(recorridosRespaldo.contains(auxVer.nombre)){
                        mMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_run_icon))
                                .position(auxVer.ubicacion)
                                .title(auxVer.nombre));
                        Arco auxAr = auxVer.sigA;
                        while(auxAr != null){
                            if(recorridosRespaldo.contains(auxAr.destino.nombre)){
                                mMap.addPolyline(auxAr.arcoGrafico.color(Color.BLUE));
                                auxAr = auxAr.sigArco;
                            } else {
                                mMap.addPolyline(auxAr.arcoGrafico);
                                auxAr = auxAr.sigArco;
                            }
                        }
                        auxVer = auxVer.sigVertice;
                    } else {
                        mMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon))
                                .position(auxVer.ubicacion)
                                .title(auxVer.nombre));
                        Arco auxAr = auxVer.sigA;
                        while(auxAr != null){
                            if(recorridosRespaldo.contains(auxAr.destino.nombre)){
                                mMap.addPolyline(auxAr.arcoGrafico.color(Color.BLUE));
                                auxAr = auxAr.sigArco;
                            } else {
                                mMap.addPolyline(auxAr.arcoGrafico);
                                auxAr = auxAr.sigArco;
                            }
                        }
                        auxVer = auxVer.sigVertice;
                    }
                }
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

            public void TrazarRutaCortaPeso(Vertice actual, String destino, int peso){
                recorridos.add(actual.nombre);

                if(actual.nombre.equals(destino)){
                    if(pesoSaltoMenor == 0 || peso < pesoSaltoMenor){
                        pesoSaltoMenor = peso;
                        RespaldarArray();
                        LimpiarArray();
                        return;
                    }
                    LimpiarArray();
                    return;
                }
                if(actual.marca == true){
                    return;
                }
                actual.marca = true;

                Arco aux = actual.sigA;
                while(aux != null){
                    TrazarRutaCortaPeso(aux.destino, destino, peso + aux.peso);
                    LimpiarMarcas();
                    aux = aux.sigArco;
                }
            }

            public void TrazarRutaCortaSalto(Vertice actual, String destino, int peso){
                recorridos.add(actual.nombre);

                if(actual.nombre.equals(destino)){
                    if(pesoSaltoMenor == 0 || peso < pesoSaltoMenor){
                        pesoSaltoMenor = peso;
                        RespaldarArray();
                        LimpiarArray();
                        return;
                    }
                    LimpiarArray();
                    return;
                }
                if(actual.marca == true){
                    return;
                }
                actual.marca = true;

                Arco aux = actual.sigA;
                while(aux != null){
                    TrazarRutaCortaSalto(aux.destino, destino, peso + 1);
                    LimpiarMarcas();
                    aux = aux.sigArco;
                }
            }

            public void RespaldarArray(){
                recorridosRespaldo.clear();
                for(int x = 0; x < recorridos.size(); x++){
                    recorridosRespaldo.add(recorridos.get(x));
                }
            }

            public void LimpiarArray(){
                String primero = recorridos.get(0);
                recorridos.clear();
                recorridos.add(primero);
            }

            public void LimpiarMarcas(){
                if(grafo == null){
                    return;
                }
                Vertice aux = grafo;
                while(aux != null){
                    aux.marca = false;
                    aux = aux.sigVertice;
                }
            }

            public void ResetVariables(){
                recorridos.clear();
                recorridosRespaldo.clear();
                pesoSaltoMenor = 0;
                LimpiarMarcas();
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

                if(vertices_existentes == true && !origen.equals(destino)){
                    if(tipo == "Saltos"){
                        TrazarRutaCortaSalto(buscarVertice(origen),destino, 0);
                        CargarGrafo();
                        ResetVariables();
                    }else{
                        TrazarRutaCortaPeso(buscarVertice(origen), destino, 0);
                        CargarGrafo();
                        ResetVariables();
                    }
                } else {
                    Toast.makeText(MapsActivityCargaGrafo.this, "No existe uno o ambos vertices", Toast.LENGTH_SHORT).show();
                }
            }
        }
