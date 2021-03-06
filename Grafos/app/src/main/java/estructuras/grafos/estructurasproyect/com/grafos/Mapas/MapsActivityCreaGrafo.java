package estructuras.grafos.estructurasproyect.com.grafos.Mapas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import estructuras.grafos.estructurasproyect.com.grafos.Dialogs.DialogDellVertice;
import estructuras.grafos.estructurasproyect.com.grafos.Dialogs.DialogNewArco;
import estructuras.grafos.estructurasproyect.com.grafos.Dialogs.DialogNewVertice;
import estructuras.grafos.estructurasproyect.com.grafos.R;
import estructuras.grafos.estructurasproyect.com.grafos.Singleton.SingletonGrafo;

public class MapsActivityCreaGrafo extends FragmentActivity
        implements
        OnMapReadyCallback,
        DialogNewVertice.DialogListener,
        DialogNewArco.DialogListener,
        DialogDellVertice.DialogListener
        {


        //####################################### VARIABLES ############################################################
        //Map
        private GoogleMap mMap;
        public ArrayList<Polyline> polylineas = new ArrayList<>();
        //Map variables
        public Marker marcador_origen_select;
        public Marker marcador_destino_select;
        //Variables Crear Vertice / Arco
        public LatLng coordenada_nuevo_vertice;
        public String nombre_nuevo_vertice;
        public int peso_nuevo_arco;
        //Botones varibles
        public boolean click_add_polilyne = false;
        public boolean click_add_marker = false;
        //Variables
        public int selects = 0;
        //Grafo
        public  Vertice grafoCreado=SingletonGrafo.getInstance().metGrafo.arbolUsuarioActual.grafo;
        public ArrayList<String> lista_vertices_agregados = new ArrayList<>();
        public boolean vertice_existente;
        //Botones


        //######################################## CONSTRUCTOR ##################################################

        @Override
        protected void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);


            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map_fragment);
            mapFragment.getMapAsync(this);

            findViewById(R.id.add_marker_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreaDialogoNuevoVertice();
                }
            });

            findViewById(R.id.add_polilyne_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreaDialogoNuevoArco();
                }
            });

            findViewById(R.id.dell_marker_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreaDialogoDellVertice();
                }
            });

            findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SingletonGrafo.getInstance().metGrafo.arbolUsuarioActual.grafo=grafoCreado;// aqui agrega los grafos creados al usuario actual

                    Toast.makeText(MapsActivityCreaGrafo.this,SingletonGrafo.getInstance().metGrafo.arbolUsuarioActual.nombre+", agregastes una Ruta!", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(MapsActivityCreaGrafo.this, "Guardando...", Toast.LENGTH_SHORT).show();
                    MapsActivityCargaGrafo sig = new MapsActivityCargaGrafo();
                    sig.setGrafo(SingletonGrafo.getInstance().metGrafo.arbolUsuarioActual.grafo);
                    Intent next = new Intent(MapsActivityCreaGrafo.this, sig.getClass());
                    startActivity(next);

                }
            });

        }

        //################################## VALIDACION VERTICES ########################################

        public void ValidarExistenciaVertice(String texto){
            for(int x = 0; x < lista_vertices_agregados.size(); x++){
                if(lista_vertices_agregados.get(x).equals(texto)){
                    vertice_existente = true;
                    return;
                }
            }
            vertice_existente = false;
        }

        //####################################### METODOS GMAPS #################################################

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    if (click_add_marker == true) {
                        //Se crea el nuevo vertice y se agrega al grafo
                        Vertice nuevoVer = new Vertice(nombre_nuevo_vertice, latLng);
                        InsertarVertice(nuevoVer);
                        //Se crea un marker en Gmap
                        mMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon))
                                .position(latLng).title(nombre_nuevo_vertice));
                        //Se cambia el estado del click en agregar vertice
                        click_add_marker = false;
                    }
                }
            });

            mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
                @Override
                public void onPolylineClick(Polyline polyline) {
                    BuscarInfoPoly(polyline);
                }
            });

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    if (click_add_polilyne == true) {
                        if (selects == 0) {
                            marcador_origen_select = marker;
                            selects = 1;
                            Toast.makeText(MapsActivityCreaGrafo.this, "Seleccione el destino", Toast.LENGTH_SHORT).show();

                        } else if (selects == 1) {
                            if (!marker.getPosition().equals(marcador_origen_select.getPosition())) {
                                selects = 0;
                                marcador_destino_select = marker;
                                creaPolilyne(marcador_origen_select, marcador_destino_select, peso_nuevo_arco);
                            } else {
                                Toast.makeText(MapsActivityCreaGrafo.this, "Destino no valido: Seleccione otro", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    return false;
                }
            });
        }

        public void creaPolilyne(Marker origen, Marker destino, int peso) {
            //Se crea una polyline grafica
            PolylineOptions POLILINEA = new PolylineOptions()
                    .add(origen.getPosition())
                    .add(destino.getPosition())
                    .width(4).color(Color.YELLOW)
                    .geodesic(true);
            //Se crea un arco logico y se le agrega la polyline
            Arco nuevoAr =  new Arco(peso, POLILINEA, buscarVertice(destino.getTitle()));
            InsertarArco(origen.getTitle(), nuevoAr);
            //Se muestra graficamente en el Map
            Polyline poly = this.mMap.addPolyline(POLILINEA);
            polylineas.add(poly);
            //Se cambia el estado Click agregar arco
            click_add_polilyne = false;
        }

        public void LimpiarPolys(){
            for(Polyline line : polylineas){
                line.remove();
            }
            polylineas.clear();
        }

        public void BuscarInfoPoly(Polyline polyline){
            if(grafoCreado == null){
                return;
            }
            Vertice aux = grafoCreado;

            while(aux != null){
                Arco var = aux.sigA;
                while(var != null){
                    if(var.arcoGrafico.getPoints().equals(polyline.getPoints())){
                        Toast.makeText(MapsActivityCreaGrafo.this, "Destino: " + var.destino.nombre + " / Peso: " + var.peso, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    var = var.sigArco;
                }
                aux = aux.sigVertice;
            }
            Toast.makeText(MapsActivityCreaGrafo.this, "Arco", Toast.LENGTH_SHORT).show();
        }

        public void CargarGrafo(){
            //Se limpia el mapa
            LimpiarPolys();
            this.mMap.clear();
            lista_vertices_agregados.clear();
            //Si el grafo está nulo no se coloca nada
            if(grafoCreado == null){
                return;
            }
            //En caso de no ser nulo se recorre
            Vertice auxVer = grafoCreado;
            //Recorrido
            while(auxVer != null){
                lista_vertices_agregados.add(auxVer.nombre);
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

            //####################################### METODOS GRAFO #################################################

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
                    Toast.makeText(MapsActivityCreaGrafo.this, "Agregado (Raiz)", Toast.LENGTH_SHORT).show();


                    return;
                }
                Vertice aux = grafoCreado;
                while (aux != null){
                    if (aux.sigVertice == null){
                        aux.sigVertice = nuevoV;
                        Toast.makeText(MapsActivityCreaGrafo.this, "Agregado!", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(MapsActivityCreaGrafo.this, "Arco primero", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Arco auxA = auxV.sigA;

                        while (auxA != null){
                            if(auxA.sigArco == null){
                                auxA.sigArco = nuevoA;
                                Toast.makeText(MapsActivityCreaGrafo.this, "Agregado!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            auxA = auxA.sigArco;
                        }
                    }
                    auxV = auxV.sigVertice;
                }
            }

            public void EliminarVertice(String nombre){
                if(grafoCreado == null){
                    return;
                }
                Vertice ant = grafoCreado;
                Vertice act = ant.sigVertice;
                while(act != null){
                    if(act.nombre.equals(nombre)){
                        ant.sigVertice = act.sigVertice;
                        act = ant.sigVertice;
                    } else {
                        ant = act;
                        act = act.sigVertice;
                    }
                }
                if(grafoCreado.nombre.equals(nombre)){
                    grafoCreado = grafoCreado.sigVertice;
                }

                EliminaArcosDeVertice(nombre);
            }

            public void EliminaArcosDeVertice(String nombre){
                Vertice auxV = grafoCreado;
                while(auxV != null){
                    if(auxV.sigA != null){
                        Arco ant = auxV.sigA;
                        Arco act = ant.sigArco;
                        while(act != null){
                            if(act.destino.nombre.equals(nombre)){
                                ant.sigArco = act.sigArco;
                                act = ant.sigArco;
                            } else {
                                ant = act;
                                act = act.sigArco;}
                        }
                        if(auxV.sigA.destino.nombre.equals(nombre)){
                            auxV.sigA = auxV.sigA.sigArco;
                        }
                        auxV = auxV.sigVertice;
                    } else {
                        auxV = auxV.sigVertice;
                    }
                }
            }

            //######################################### VENTANAS DE DIALOGO #####################################################################

        public void CreaDialogoNuevoVertice(){
            DialogNewVertice dialogNewVertice = new DialogNewVertice();
            dialogNewVertice.show(getSupportFragmentManager(), "personalizado_vertice");

            android.app.Fragment frag = getFragmentManager().findFragmentByTag("personalizado_vertice");

            if (frag != null) {
                getFragmentManager().beginTransaction().remove(frag).commit();
            }
        }

        @Override
        public void FinalizaDialogoNewVertice(String texto) {
            //Se obtiene el nombre del vertice
            nombre_nuevo_vertice = texto;
            //Se valida el vertice nuevo
            ValidarExistenciaVertice(texto);
            //Segun la validacion se crea el vertice
            if (vertice_existente == false){
                //Se pone la variable click(vertice nuevo) en true
                click_add_marker = true;
                lista_vertices_agregados.add(texto);
                Toast.makeText(MapsActivityCreaGrafo.this, "Posicione el nuevo vertice", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MapsActivityCreaGrafo.this, "El vertice ya existe", Toast.LENGTH_SHORT).show();
            }

        }

        public void CreaDialogoNuevoArco(){
            DialogNewArco dialogoPersonalizado = new DialogNewArco();
            dialogoPersonalizado.show(getSupportFragmentManager(), "personalizado_arco");

            android.app.Fragment frag = getFragmentManager().findFragmentByTag("personalizado_arco");

            if (frag != null) {
                getFragmentManager().beginTransaction().remove(frag).commit();
            }
        }

        @Override
        public void FinalizaDialogoNewArco(int texto) {
            peso_nuevo_arco = texto;
            click_add_polilyne = true;
            selects = 0;
            //Se notifica que se agregó
            Toast.makeText(MapsActivityCreaGrafo.this, "Seleccione el origen", Toast.LENGTH_SHORT).show();
        }

        public void CreaDialogoDellVertice(){
            DialogDellVertice dialogNewVertice = new DialogDellVertice();
            dialogNewVertice.show(getSupportFragmentManager(), "personalizado_vertice");

            android.app.Fragment frag = getFragmentManager().findFragmentByTag("personalizado_vertice");

            if (frag != null) {
                getFragmentManager().beginTransaction().remove(frag).commit();
            }
        }

            @Override
            public void FinalizaDialogoDellVertice(String texto) {
                //Se obtiene el nombre del vertice
                nombre_nuevo_vertice = texto;
                //Se valida el vertice nuevo
                ValidarExistenciaVertice(texto);
                //Segun la validacion se crea el vertice
                if (vertice_existente == true){
                    //Se pone la variable click(vertice nuevo) en true
                    EliminarVertice(texto);
                    CargarGrafo();
                    Toast.makeText(MapsActivityCreaGrafo.this, "Eliminado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MapsActivityCreaGrafo.this, "Error: El vertice NO existe", Toast.LENGTH_SHORT).show();
            }
            }
        }
