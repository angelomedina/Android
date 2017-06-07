package estructuras.grafos.estructurasproyect.com.grafos.Graficos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import estructuras.grafos.estructurasproyect.com.grafos.Arbol.Arbol;
import estructuras.grafos.estructurasproyect.com.grafos.Mapas.Arco;
import estructuras.grafos.estructurasproyect.com.grafos.Mapas.Vertice;
import estructuras.grafos.estructurasproyect.com.grafos.R;
import estructuras.grafos.estructurasproyect.com.grafos.Singleton.SingletonArbol;
import estructuras.grafos.estructurasproyect.com.grafos.Singleton.SingletonGrafo;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button registro=(Button)findViewById(R.id.button_registro);
        Button login=(Button)findViewById(R.id.button_Login);
        Button guardar=(Button)findViewById(R.id.Button_Guardar_Seccion);
        final String[] archivos = fileList(); // esta es una libreria propia para entrar a todos los txt de Android


        guardar.setOnClickListener(new View.OnClickListener() // vaya a registrar
        {
            @Override
            public void onClick(View v) { //vaya a registrar

                // con esto paso a las ventanas

                if(SingletonArbol.getInstance().metArbol.raiz != null)
                {
                    limpiar();
                    //agrego el admi por defecto
                    EscribirUsuario("Fifa",123); //String file,String tex
                    recorreArbol(SingletonArbol.getInstance().metArbol.raiz);
                }
                else
                {
                    Toast.makeText(Login.this, "Error primero debes iniciar seccion", Toast.LENGTH_LONG).show();

                }

                //seteo el arbol
                //SingletonArbol.getInstance().metArbol.raiz=null;

//                //Limpio los grafos
//                Vertice grafoCreado=null;
//                MetodosGrafo arbolUsuarioActual=null;
            }
            /// //////////////////////////////////////Guardar en el Archivo/////////////////////////////////////////////

            //imprimri  preOrden

            public void limpiar() //String file,String text
            {
                try
                {
                    OutputStreamWriter archivo = new OutputStreamWriter((openFileOutput("UsuariosArchivos.txt", Registro.MODE_PRIVATE)));//MODE_APPEND es indeipensable para escribir en el archivo
                    archivo.close();
                    Toast.makeText(Login.this, "Se respaldo la informacion!", Toast.LENGTH_LONG).show();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Login.this, "Error al sobreescribir el archivo", Toast.LENGTH_LONG).show();
                }
            }

            public void recorreArbol(Arbol aux)
            {
                if (aux == null)
                {
                    return;
                }
                if(aux != null)
                {

                    EscribirUsuario(aux.nombre,aux.cedula); // agrego el Usuario
                    if(aux.grafo != null)
                    {
                        recorreGrafo(aux);
                    }

                }
                //Toast.makeText(Login.this,aux.nombre, Toast.LENGTH_SHORT).show();
                recorreArbol(aux.izq);
                recorreArbol(aux.der);
            }

            public void recorreGrafo(Arbol aux)
            {
                //Vertice vertice=SingletonGrafo.getInstance().metGrafo.buscarVertice(aux.nombre);
                Vertice vertice=aux.grafo;
                while(vertice != null) // esntro a trodos los vertices
                {
                    if(vertice.sigA!= null)
                    {
                        Arco arco=vertice.sigA;
                        while (arco != null) // entro a los arcos de cada vertice
                        {

                            String langitudOrigen = String.valueOf(vertice.ubicacion.latitude);
                            String longitudOrigen = String.valueOf(vertice.ubicacion.longitude);

                            String langitudDestino = String.valueOf(arco.destino.ubicacion.latitude);
                            String longitudDestino = String.valueOf(arco.destino.ubicacion.longitude);
                            //aqui escribo el arco

                            EscribirArco(vertice.nombre,langitudOrigen,longitudOrigen,arco.destino.nombre,langitudDestino,longitudDestino,arco.peso);
                            arco=arco.sigArco;
                        }

                    }
                    else
                    {

                        String langitudOrigen = String.valueOf(vertice.ubicacion.latitude);
                        String longitudOrigen = String.valueOf(vertice.ubicacion.longitude);

                        //añado solo el vertice
                        EscribirVertice(vertice.nombre,langitudOrigen,longitudOrigen);
                    }
                    vertice=vertice.sigVertice;
                }


            }

            public void EscribirUsuario(String nombre, int id) //String file,String text
            {
                try {
                    OutputStreamWriter archivo = new OutputStreamWriter((openFileOutput("UsuariosArchivos.txt", Registro.MODE_APPEND)));//MODE_APPEND es indeipensable para escribir en el archivo
                    archivo.write(id + "," + nombre+"\n");// agrego estos datos al archivo
                    archivo.flush();
                    archivo.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Login.this, "Error al sobreescribir el archivo", Toast.LENGTH_LONG).show();
                }
            }

            public void EscribirArco(String verticeOrigenNombre,String latitudOrigen,String longitudOrigen,String verticeDestinoNombre,String latidudDestino,String longidudDestino,int peso) //String file,String text
            {
                try {
                    OutputStreamWriter archivo = new OutputStreamWriter((openFileOutput("UsuariosArchivos.txt", Registro.MODE_APPEND)));//MODE_APPEND es indeipensable para escribir en el archivo
                    archivo.write(verticeOrigenNombre+","+latitudOrigen+","+longitudOrigen+","+verticeDestinoNombre+","+latidudDestino+","+longidudDestino+","+peso+":"+"\n");// agrego estos datos al archivo
                    archivo.flush();
                    archivo.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Login.this, "Error al sobreescribir el archivo", Toast.LENGTH_LONG).show();
                }
            }

            public void EscribirVertice(String verticeOrigenNombre,String latitud,String longitud) //String file,String text
            {
                try {
                    OutputStreamWriter archivo = new OutputStreamWriter((openFileOutput("UsuariosArchivos.txt", Registro.MODE_APPEND)));//MODE_APPEND es indeipensable para escribir en el archivo
                    archivo.write(verticeOrigenNombre+","+latitud+","+longitud+":"+"\n");// agrego estos datos al archivo
                    archivo.flush();
                    archivo.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Login.this, "Error al sobreescribir el archivo", Toast.LENGTH_LONG).show();
                }
            }

        });

        registro.setOnClickListener(new View.OnClickListener() // vaya a registrar
        {
            @Override
            public void onClick(View v) { //vaya a registrar

                // con esto paso a las ventanas
                Intent intent = new Intent(Login.this,Registro.class);
                startActivity(intent);//inicia la activity
            }});

        login.setOnClickListener(new View.OnClickListener() //este es el botton de login
        {
            @Override
            public void onClick(View v) {

                EditText nombre = (EditText) findViewById(R.id.usuarioText);
                EditText contraseña = (EditText) findViewById(R.id.contraseñaText2);

                int cedula = Integer.parseInt(contraseña.getText().toString());
                String name = nombre.getText().toString();

                verifica=false; // este verifica es para que se recorran todos los usuarios en el txt
                LeerTxt(name,cedula);

                nombre.setText("");
                contraseña.setText("");

                Carga_Arbol_Grafo_I_Parte(); // cargo el arbol con su respectivo grafo

                if (verifica)
                {

                    if (name.equals("Fifa") && cedula == 123)
                    {
                        verifica=false; // esto es para que los demas usuarios no puedad entrar sin verificar
                        //limpio los textos
                        nombre.setText("");
                        contraseña.setText("");

                        //cargarArbol();
                        imprimirB(SingletonArbol.getInstance().metArbol.raiz);
                        //SingletonArbol.getInstance().metArbol.imprimeXniveles(SingletonArbol.getInstance().metArbol.raiz, -1); // esto es para imprimir el arbol

                        Intent intent = new Intent(Login.this, Menu_Administrador.class); // con esto paso a las ventanas
                        startActivity(intent);//inicia la activit

                    }
                    else
                    {

                        //SingletonGrafo.getInstance().metGrafo.arbolUsuarioActual=null;
                        SingletonGrafo.getInstance().metGrafo.arbolUsuarioActual=null;

                        //limpio los textos
                        nombre.setText("");
                        contraseña.setText("");

                        ///cargarArbol();
                        imprimirB(SingletonArbol.getInstance().metArbol.raiz);

                        /// con esto busco el usuario actual y lo guardo en Metodos Grafo
                        SingletonGrafo.getInstance().metGrafo.arbolUsuarioActual=SingletonArbol.getInstance().metArbol.buscar(SingletonArbol.getInstance().metArbol.raiz,cedula);

                        Intent intent = new Intent(Login.this, Menu_Usuario.class); // con esto paso a las ventanas//aqui es menu Usuario
                        startActivity(intent);//inicia la activity
                    }

                }
                else
                {
                    verifica=false;
                    Toast.makeText(Login.this, "No se encontro al usuario", Toast.LENGTH_SHORT).show();
                }
            }

            boolean verifica=false;
            public void LeerTxt(String nombre,int cedula) //lo que hace es leer un txt en memoria interna
            {
                try
                {
                    String usuario=Integer.toString(cedula)+","+nombre;

                    if (existe(archivos,"UsuariosArchivos.txt"))
                    {
                        InputStreamReader archivo = new InputStreamReader(openFileInput("UsuariosArchivos.txt"));
                        BufferedReader br=new BufferedReader(archivo);
                        String linea = br.readLine();
                        String todo = "";
                        while(linea != null)
                        {
                            todo=todo + linea +"\n";
                            linea=br.readLine();

                            if(todo.toString().equals(usuario+"\n")) // esto es para verificar si los usuarios coninciden
                            {
                                verifica=true;
                            }
                            todo="";
                        }
                        br.close();
                        archivo.close();
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    Toast.makeText(Login.this,"Error de lectura de Archivo",Toast.LENGTH_LONG).show();
                }
            }

            // este metodo lo que hace es verficar si existe el archivo
            private boolean existe(String[] archivos, String archbusca) {
                for (int f = 0; f < archivos.length; f++) {
                    if (archbusca.equals(archivos[f])) {
                        return true;
                    }

                }
                return false;
            }

            public void cargarArbol()
            {
                try
                {
                    if (existe(archivos,"UsuariosArchivos.txt"))
                    {
                        InputStreamReader archivo = new InputStreamReader(openFileInput("UsuariosArchivos.txt"));
                        BufferedReader br=new BufferedReader(archivo);
                        String linea = br.readLine();
                        String todo = "";

                        while(linea != null)
                        {
                            todo=todo + linea +"\n";
                            linea=br.readLine();

                            String dato=todo;
                            Optiene(dato);//llamo al metodo que lee el archivo hace las conversiones y crea el arbol
                            todo="";
                        }
                        br.close();
                        archivo.close();
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    Toast.makeText(Login.this,"Error de lectura de Archivo",Toast.LENGTH_LONG).show();
                }
            }

            public void Optiene(String line)
            {
                //Toast.makeText(Login.this,"Estoy en obtiene",Toast.LENGTH_LONG).show();
                String[] parts = line.split(",");
                int cedula=Integer.parseInt(parts[0]);
                String nombre=parts[1];
                SingletonArbol.getInstance().metArbol.insertarC(SingletonArbol.getInstance().metArbol.raiz, cedula, nombre);


            }

            //imprimri  preOrden
            public void imprimirB(Arbol aux)
            {
                if (aux == null)
                {
                    return;
                }
                imprimirB(aux.izq);
                Toast.makeText(Login.this, "numero: "+Integer.toString(aux.cedula), Toast.LENGTH_SHORT).show();
                //imprimirB(aux.izq);
                imprimirB(aux.der);
            }


            ////////////////////////////////Cargar el Arbol//////////////////////////////////////////////////////////////
            public void Carga_Arbol_Grafo_I_Parte()
            {
                try
                {
                    if (existe(archivos,"UsuariosArchivos.txt"))
                    {
                        InputStreamReader archivo = new InputStreamReader(openFileInput("UsuariosArchivos.txt"));
                        BufferedReader br=new BufferedReader(archivo);
                        String linea = br.readLine();
                        String todo = "";

                        while(linea != null)
                        {
                            todo=todo + linea +"\n";
                            linea=br.readLine();

                            String dato=todo;
                            if(todo.equals(""+"\n"))
                            {
                                //Si todos esta nulo ni lo vea
                            }
                            else
                            {
                                Carga_Arbol_Grafo_II_Parte(dato);//llamo al metodo que lee el archivo hace las conversiones y crea el arbol
                            }
                            todo="";
                        }
                        br.close();
                        archivo.close();
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    Toast.makeText(Login.this,"Error de lectura de Archivo",Toast.LENGTH_LONG).show();
                }
            }

            Arbol temportalUsuarioArbol; //esto es para que cuando aparesca una linea de usuario todo lo que vaya hacia abajo se agregre a ese usuario
            public void Carga_Arbol_Grafo_II_Parte(String line) // este metodo agrega la informacion de los usuarios y lo guarda en un arbol
            {

                String[] parts = line.split(",");
                int cantidadSplits=parts.length;

                //Toast.makeText(Login.this,cantidadSplits, Toast.LENGTH_SHORT).show();

                if(cantidadSplits==2)
                {
                    if (isNumeric(parts[0]) == true && isNumeric(parts[1]) == false) // aqui es para validar que la primer linea se la cedula de un usuario
                    {
                        // Limpiar el Usuario
                        temportalUsuarioArbol = null;
                        // Casting
                        int cedula = Integer.parseInt(parts[0]);
                        String nombre = parts[1];
                        // Instanciacion
                        SingletonArbol.getInstance().metArbol.insertarC(SingletonArbol.getInstance().metArbol.raiz, cedula, nombre);
                        // Igualar: para tener un usuario global
                        temportalUsuarioArbol = SingletonArbol.getInstance().metArbol.buscar(SingletonArbol.getInstance().metArbol.raiz, cedula);// esto es para que agregue el grafo a un usuario en especifico
                        SingletonGrafo.getInstance().metGrafo.grafo=temportalUsuarioArbol.grafo;
                    }

                }
                if(cantidadSplits==7) {


                    //EscribirArco(String verticeOrigenNombre,String latitudOrigen,String longitudOrigen,String verticeDestinoNombre,String latidudDestino,String longidudDestino,int peso) //String file,String text

                        //Casting Origen
                        String nombreOrigen = parts[0].toString();  //nombre
                        double latitudeO = Double.parseDouble(parts[1]);
                        double longitudeO = Double.parseDouble(parts[2]);
                        LatLng locationO = new LatLng(latitudeO, longitudeO);// latitud

                        //Casting destino
                        String nombreDestino = parts[3].toString();  //nombre
                        double latitudeD = Double.parseDouble(parts[4]);
                        double longitudeD = Double.parseDouble(parts[5]);
                        LatLng locationD = new LatLng(latitudeO, longitudeD);// latitud

                        // polyLineOption
                        GoogleMap map;
                        PolylineOptions polyLine = new PolylineOptions(); // se añade el polyLine porque para crear un arco se necesita la posicion del destino
                        polyLine.add(locationD);

                        //Casting del pesoString
                        String[] separated = parts[6].split(":");

                        int peso = Integer.parseInt(separated[0]);
                        Vertice origen = SingletonGrafo.getInstance().metGrafo.buscarVertice(nombreOrigen);
                        Vertice destino = SingletonGrafo.getInstance().metGrafo.buscarVertice(nombreDestino);



                        if (origen == null && destino == null)  // este es en el caso de que ambos fuesen nulos
                        {
                            //Agrego los vertice Origen
                            SingletonGrafo.getInstance().metGrafo.InsertarVertice(nombreOrigen, locationO);
                            //Agrego vertice destino
                            SingletonGrafo.getInstance().metGrafo.InsertarVertice(nombreDestino, locationD);
                            //los busco de nuevo
                            Vertice O = SingletonGrafo.getInstance().metGrafo.buscarVertice(nombreOrigen);
                            Vertice D = SingletonGrafo.getInstance().metGrafo.buscarVertice(nombreDestino);
                            //Agrego el arco
                            SingletonGrafo.getInstance().metGrafo.insertA(O, D, polyLine, peso);
                        }
                        if (origen != null && destino != null) {
                            //Agrego el arco
                            SingletonGrafo.getInstance().metGrafo.insertA(origen, destino, polyLine, peso);
                        }
                        if (origen != null && destino == null) {
                            //Agrego vertice destino
                            SingletonGrafo.getInstance().metGrafo.InsertarVertice(nombreDestino, locationD);
                            //los busco de nuevo
                            Vertice D = SingletonGrafo.getInstance().metGrafo.buscarVertice(nombreDestino);
                            //Agrego el arco
                            SingletonGrafo.getInstance().metGrafo.insertA(origen, D, polyLine, peso);

                        }
                        if(origen == null && destino != null)
                        {
                            //Agrego vertice destino
                            SingletonGrafo.getInstance().metGrafo.InsertarVertice(nombreOrigen, locationO);
                            //los busco de nuevo
                            Vertice O = SingletonGrafo.getInstance().metGrafo.buscarVertice(nombreOrigen);
                            //Agrego el arco
                            SingletonGrafo.getInstance().metGrafo.insertA(O, destino, polyLine, peso);
                        }


                }
                if(cantidadSplits==3) // carga solo un Verice
                {
                        // si no solo va ser un vertice
                        //Casting Origen
                        String nombreOrigen = parts[0].toString();  //nombre

                        double latitudeO = Double.parseDouble(parts[1]);
                        String[] separated = parts[2].split(":");
                        double longitudeO = Double.parseDouble(separated[0]);
                        LatLng locationO = new LatLng(latitudeO, longitudeO);// latitu

                        Vertice origen = SingletonGrafo.getInstance().metGrafo.buscarVertice(nombreOrigen);
                        if (origen == null)
                        {
                            //Agrego vertice
                            SingletonGrafo.getInstance().metGrafo.InsertarVertice(nombreOrigen, locationO);
                        }
                }


            }

            //esto es para verificar si los datos en el texto son numericos
            public boolean isNumeric(String cadena){
                try {
                    Integer.parseInt(cadena);
                    return true;
                } catch (NumberFormatException nfe){
                    return false;
                }
            }

        });




    }
}
