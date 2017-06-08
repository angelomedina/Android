package estructuras.grafos.estructurasproyect.com.grafos.Mapas;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;

import estructuras.grafos.estructurasproyect.com.grafos.Dialogs.DialogCiclico;
import estructuras.grafos.estructurasproyect.com.grafos.Dialogs.DialogComprobada;
import estructuras.grafos.estructurasproyect.com.grafos.Dialogs.DialogInfoRutaComprobar;
import estructuras.grafos.estructurasproyect.com.grafos.Dialogs.DialogInfoRutaCorta;
import estructuras.grafos.estructurasproyect.com.grafos.Dialogs.DialogNoCiclico;
import estructuras.grafos.estructurasproyect.com.grafos.Dialogs.DialogNoComprobada;
import estructuras.grafos.estructurasproyect.com.grafos.R;
import estructuras.grafos.estructurasproyect.com.grafos.Singleton.SingletonGrafo;

public class MapsActivityCargaGrafo extends FragmentActivity
        implements
        OnMapReadyCallback,
        DialogInfoRutaCorta.DialogListener,
        DialogInfoRutaComprobar.DialogListener
        {


    //#################################### VARIABLES ##########################################################
    //Gmaps
    private GoogleMap mMap;
    public ArrayList<Polyline> polylineas = new ArrayList<>();
    public ArrayList<Marker> marcadores = new ArrayList<>();
    //Dialogs / Validaciones
    public Vertice grafo = SingletonGrafo.getInstance().metGrafo.arbolUsuarioActual.grafo;// MapsActivityCreaGrafo.grafoCreado;
    public ArrayList<String> lista_vertices = new ArrayList<>();
    public boolean vertices_existentes;
    //Metodos Grafo
    public int pesoSaltoMenor = 0;
    //Ruta Corta
    public ArrayList<String> recorridos = new ArrayList<>();
    public ArrayList<String> recorridosRespaldo = new ArrayList<>();
    String puntoPartida = "";
    String puntoLlegada = "";
    //Ciclico
    public ArrayList<String> lista = new ArrayList<>();
    public boolean ciclo=false;
    //Comprobar Ruta
    public boolean hayRuta = false;

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
                CreaDialogoInfoRutaCorta();
            }
        });

        findViewById(R.id.cargar_mapa_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarGrafo();
                Toast.makeText(MapsActivityCargaGrafo.this, "Grafo Cargado", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.ciclico_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComprobarCiclico();
            }
        });
        findViewById(R.id.comprobar_ruta_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreaDialogoInfoRutaComprobar();
            }
        });
    }

    //################################### METODOS GMAPS ##########################################################

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                BuscarInfoPoly(polyline);
            }
        });

    }

    //################################## METODO CARGA EL GRAFO ######################################################

    public void setGrafo(Vertice grafo) {
        this.grafo = grafo;
    }

            public void CargarRutaGrafoPeso(){
                //Se limpia el mapa
                LimpiarPolys();
                this.mMap.clear();
                lista_vertices.clear();
                int suma = 0;
                //Si el grafo está nulo no se coloca nada
                if(grafo == null){
                    return;
                }
                //En caso de no ser nulo se recorre
                Vertice auxVer = grafo;
                //Recorrido
                while(auxVer != null){
                    lista_vertices.add(auxVer.nombre);
                    if(recorridosRespaldo.contains(auxVer.nombre)){
                        mMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_run_icon))
                                .position(auxVer.ubicacion)
                                .title(auxVer.nombre));
                        Arco auxAr = auxVer.sigA;

                        while(auxAr != null){

                            if(recorridosRespaldo.contains(auxAr.destino.nombre)
                                    && !auxAr.destino.nombre.equals(puntoPartida)
                                    && !auxAr.destino.nombre.equals(puntoLlegada)){
                                    suma += auxAr.peso;
                                    Polyline poly = this.mMap.addPolyline(auxAr.arcoGrafico.color(Color.BLUE));
                                    poly.setClickable(true);
                                    polylineas.add(poly);
                                    auxAr = auxAr.sigArco;

                            }else if(recorridosRespaldo.contains(auxAr.destino.nombre)
                                    && !auxAr.destino.nombre.equals(puntoPartida)
                                    && auxAr.destino.nombre.equals(puntoLlegada)
                                    && (suma + auxAr.peso) == pesoSaltoMenor){
                                    suma += auxAr.peso;
                                    Polyline poly = this.mMap.addPolyline(auxAr.arcoGrafico.color(Color.BLUE));
                                    poly.setClickable(true);
                                    polylineas.add(poly);
                                    auxAr = auxAr.sigArco;

                            }else {
                                Polyline poly = this.mMap.addPolyline(auxAr.arcoGrafico);
                                polylineas.add(poly);
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
                        while (auxAr != null) {
                            Polyline poly = this.mMap.addPolyline(auxAr.arcoGrafico);
                            poly.setClickable(true);
                            polylineas.add(poly);
                            auxAr = auxAr.sigArco;
                        }
                        auxVer = auxVer.sigVertice;
                    }
                }
            }

            public void CargarRutaGrafoSalto(){
                //Se limpia el mapa
                LimpiarPolys();
                this.mMap.clear();
                lista_vertices.clear();
                int suma = 0;
                //Si el grafo está nulo no se coloca nada
                if(grafo == null){
                    return;
                }
                //En caso de no ser nulo se recorre
                Vertice auxVer = grafo;
                //Recorrido
                while(auxVer != null){
                    lista_vertices.add(auxVer.nombre);
                    if(recorridosRespaldo.contains(auxVer.nombre)){
                        mMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_run_icon))
                                .position(auxVer.ubicacion)
                                .title(auxVer.nombre));
                        Arco auxAr = auxVer.sigA;

                        while(auxAr != null){

                            if(recorridosRespaldo.contains(auxAr.destino.nombre)
                                    && !auxAr.destino.nombre.equals(puntoPartida)
                                    && !auxAr.destino.nombre.equals(puntoLlegada)){
                                suma += 1;
                                Polyline poly = this.mMap.addPolyline(auxAr.arcoGrafico.color(Color.BLUE));
                                poly.setClickable(true);
                                polylineas.add(poly);
                                auxAr = auxAr.sigArco;

                            }else if(recorridosRespaldo.contains(auxAr.destino.nombre)
                                    && !auxAr.destino.nombre.equals(puntoPartida)
                                    && auxAr.destino.nombre.equals(puntoLlegada)
                                    && (suma + 1) == pesoSaltoMenor){
                                suma += 1;
                                Polyline poly = this.mMap.addPolyline(auxAr.arcoGrafico.color(Color.BLUE));
                                poly.setClickable(true);
                                polylineas.add(poly);
                                auxAr = auxAr.sigArco;

                            }else {
                                Polyline poly = this.mMap.addPolyline(auxAr.arcoGrafico);
                                polylineas.add(poly);
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
                        while (auxAr != null) {
                            Polyline poly = this.mMap.addPolyline(auxAr.arcoGrafico);
                            poly.setClickable(true);
                            polylineas.add(poly);
                            auxAr = auxAr.sigArco;
                        }
                        auxVer = auxVer.sigVertice;
                    }
                }
            }

            public void BuscarInfoPoly(Polyline polyline){
                if(grafo == null){
                    return;
                }
                Vertice aux = grafo;

                while(aux != null){
                    Arco var = aux.sigA;
                    while(var != null){
                        if(var.arcoGrafico.getPoints().equals(polyline.getPoints())){
                            Toast.makeText(MapsActivityCargaGrafo.this, "Destino: " + var.destino.nombre + " / Peso: " + var.peso, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        var = var.sigArco;
                    }
                    aux = aux.sigVertice;
                }
                Toast.makeText(MapsActivityCargaGrafo.this, "Arco", Toast.LENGTH_SHORT).show();
            }

        public void CargarGrafo(){
            //Se limpia el mapa
            LimpiarPolys();
            this.mMap.clear();
            lista_vertices.clear();
            //Si el grafo está nulo no se coloca nada
            if(grafo == null){
                return;
            }
            //En caso de no ser nulo se recorre
            Vertice auxVer = grafo;
            //Recorrido
            while(auxVer != null){
                lista_vertices.add(auxVer.nombre);
                Marker mark = mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon))
                        .position(auxVer.ubicacion)
                        .title(auxVer.nombre));

                Arco auxAr = auxVer.sigA;
                while (auxAr != null) {
                    Polyline poly = this.mMap.addPolyline(auxAr.arcoGrafico.color(Color.YELLOW));
                    poly.setClickable(true);
                    polylineas.add(poly);
                    auxAr = auxAr.sigArco;
                }
                auxVer = auxVer.sigVertice;
            }
        }

    public void LimpiarPolys(){
        for(Polyline line : polylineas){
            line.remove();
        }
        polylineas.clear();
    }

    public void PrintRuta(){
        String ruta = "";
        for(int x = 0; x < recorridosRespaldo.size(); x++){
            ruta += recorridosRespaldo.get(x) + " - ";
        }
        Toast.makeText(MapsActivityCargaGrafo.this, "Ruta: " + ruta, Toast.LENGTH_SHORT).show();
    }

//################################## VALIDACION VERTICES ########################################

            public void ValidarExistenciaVertice(String origen, String destino){
                vertices_existentes = false;
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

    public void ciclico(Vertice grafo){
        if (grafo == null){
            return;
        }
        Arco aux = grafo.sigA;
        lista.add(grafo.nombre);
        if(grafo.sigA != null){
            if (lista.contains(grafo.sigA.destino.nombre)){
                ciclo = true;
                return;
            }
        }
        while (aux != null){
            ciclico(aux.destino);
            aux = aux.sigArco;
        }
    }

    public void ComprobarCiclico(){
        lista.clear();
        ciclo = false;
        ciclico(this.grafo);
        if(ciclo == true){
            DialogCiclico dialogoPersonalizado = new DialogCiclico();
            dialogoPersonalizado.show(getSupportFragmentManager(), "personalizado_arco");

            android.app.Fragment frag = getFragmentManager().findFragmentByTag("personalizado_arco");

            if (frag != null) {
                getFragmentManager().beginTransaction().remove(frag).commit();
            }

        } else {
            DialogNoCiclico dialogoPersonalizado = new DialogNoCiclico();
            dialogoPersonalizado.show(getSupportFragmentManager(), "personalizado_arco");

            android.app.Fragment frag = getFragmentManager().findFragmentByTag("personalizado_arco");

            if (frag != null) {
                getFragmentManager().beginTransaction().remove(frag).commit();
            }
        }
    }

    public void ComprobarRuta(Vertice actual, String destino){
        if(actual.nombre.equals(destino)){
            hayRuta = true;
            return;
        }
        if(actual.marca == true){
            return;
        }
        actual.marca = true;
        Arco aux = actual.sigA;
        while(aux != null){
            ComprobarRuta(aux.destino, destino);
            LimpiarMarcas();
            aux = aux.sigArco;
        }
    }

    public void ResetVariables(){
        recorridos.clear();
        recorridosRespaldo.clear();
        pesoSaltoMenor = 0;
        LimpiarMarcas();
    }


//############################################# METODOS DE DIALOGS #################################################

    public void CreaDialogoInfoRutaCorta(){
        DialogInfoRutaCorta dialogoPersonalizado = new DialogInfoRutaCorta();
        dialogoPersonalizado.show(getSupportFragmentManager(), "personalizado_arco");

        android.app.Fragment frag = getFragmentManager().findFragmentByTag("personalizado_arco");

        if (frag != null) {
            getFragmentManager().beginTransaction().remove(frag).commit();
        }
    }

    @Override
    public void FinalizaDialogoNewArco(String origen, String destino, String tipo) {
        ValidarExistenciaVertice(origen, destino);
        puntoPartida = origen;
        puntoLlegada = destino;
        if(vertices_existentes == true && !origen.equals(destino)){
            if(tipo == "Saltos"){
                TrazarRutaCortaSalto(buscarVertice(origen),destino, 0);
                CargarRutaGrafoSalto();
                PrintRuta();
                Toast.makeText(MapsActivityCargaGrafo.this, "Saltos totales: " + pesoSaltoMenor, Toast.LENGTH_SHORT).show();
                ResetVariables();
            }else{
                TrazarRutaCortaPeso(buscarVertice(origen), destino, 0);
                CargarRutaGrafoPeso();
                PrintRuta();
                Toast.makeText(MapsActivityCargaGrafo.this, "Peso total: " + pesoSaltoMenor, Toast.LENGTH_SHORT).show();
                ResetVariables();
            }
        } else if (origen.equals(destino)) {
            Toast.makeText(MapsActivityCargaGrafo.this, "Error: Ambos vertices son iguales", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MapsActivityCargaGrafo.this, "Error: No existe uno o ambos vertices", Toast.LENGTH_SHORT).show();
        }
    }

    public void CreaDialogoInfoRutaComprobar(){
        DialogInfoRutaComprobar dialogoPersonalizado = new DialogInfoRutaComprobar();
        dialogoPersonalizado.show(getSupportFragmentManager(), "personalizado_arco");

        android.app.Fragment frag = getFragmentManager().findFragmentByTag("personalizado_arco");

        if (frag != null) {
            getFragmentManager().beginTransaction().remove(frag).commit();
        }
    }

    @Override
    public void FinalizaDialogInfoRutaComprobar(String origen, String destino) {
        ValidarExistenciaVertice(origen, destino);
        if(vertices_existentes == true && !origen.equals(destino)){
            hayRuta = false;
            ComprobarRuta(buscarVertice(origen), destino);

            if(hayRuta == true){
                DialogComprobada dialogoPersonalizado = new DialogComprobada();
                dialogoPersonalizado.show(getSupportFragmentManager(), "personalizado_arco");

                android.app.Fragment frag = getFragmentManager().findFragmentByTag("personalizado_arco");

                if (frag != null) {
                    getFragmentManager().beginTransaction().remove(frag).commit();
                }
            }else {
                DialogNoComprobada dialogoPersonalizado = new DialogNoComprobada();
                dialogoPersonalizado.show(getSupportFragmentManager(), "personalizado_arco");

                android.app.Fragment frag = getFragmentManager().findFragmentByTag("personalizado_arco");

                if (frag != null) {
                    getFragmentManager().beginTransaction().remove(frag).commit();
                }
            }
        } else if (origen.equals(destino)) {
            Toast.makeText(MapsActivityCargaGrafo.this, "Error: Ambos vertices son iguales", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MapsActivityCargaGrafo.this, "Error: No existe uno o ambos vertices", Toast.LENGTH_SHORT).show();
        }
    }
}