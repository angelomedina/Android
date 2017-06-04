package estructuras.grafos.estructurasproyect.com.grafos.Graficos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import estructuras.grafos.estructurasproyect.com.grafos.Arbol.Arbol;
import estructuras.grafos.estructurasproyect.com.grafos.R;
import estructuras.grafos.estructurasproyect.com.grafos.Singleton.SingletonArbol;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        Button registro=(Button)findViewById(R.id.button_registro);
        Button login=(Button)findViewById(R.id.button_Login);
        final String[] archivos = fileList(); // esta es una libreria propia para entrar a todos los txt de Android


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

                if (verifica)
                {
                    if (name.equals("Fifa") && cedula == 123)
                    {
                        verifica=false; // esto es para que los demas usuarios no puedad entrar sin verificar
                        //limpio los textos
                        nombre.setText("");
                        contraseña.setText("");

                        cargarArbol();
                        imprimirB(SingletonArbol.getInstance().metArbol.raiz);
                        //SingletonArbol.getInstance().metArbol.imprimeXniveles(SingletonArbol.getInstance().metArbol.raiz, -1); // esto es para imprimir el arbol

                        Intent intent = new Intent(Login.this, Menu_Administrador.class); // con esto paso a las ventanas
                        startActivity(intent);//inicia la activit

                    }
                    else
                    {
                        //limpio los textos
                        nombre.setText("");
                        contraseña.setText("");

                        cargarArbol();
                        imprimirB(SingletonArbol.getInstance().metArbol.raiz);

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

        });

    }
}
