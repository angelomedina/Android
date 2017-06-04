package estructuras.grafos.estructurasproyect.com.grafos.Graficos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import estructuras.grafos.estructurasproyect.com.grafos.R;
import estructuras.grafos.estructurasproyect.com.grafos.Singleton.SingletonArbol;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Button mostrar=(Button)findViewById(R.id.button_imprimir);
        Button limpiar=(Button)findViewById(R.id.button_limpiar_archivo);//este boton lo que hace es limpiar los arcvivos esto es para as pruebas
        Button registrar = (Button) findViewById(R.id.button_guardar);//este boton es para registrar al usuario
        final String[] archivos = fileList(); // esta es una libreria propia para entrar a todos los txt de Android


        mostrar.setOnClickListener(new View.OnClickListener() // este es el botton para imprimir a los usuarios
        {

            @Override
            public void onClick(View v) // si doy click en registrar
            {

                LeerTxt();
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

            public void LeerTxt() //lo que hace es leer un txt en memoria interna
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
                            Toast.makeText(Registro.this,todo,Toast.LENGTH_LONG).show();
                            todo="";
                        }
                        br.close();
                        archivo.close();
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    Toast.makeText(Registro.this,"Error de lectura de Archivo",Toast.LENGTH_LONG).show();
                }
            }
        });

        limpiar.setOnClickListener(new View.OnClickListener() // este es el boton para limpiar a los usuarios
        {
            @Override
            public void onClick(View v) // si doy click en registrar
            {
                limpiar();
            }

            public void limpiar() //String file,String text
            {
                try
                {
                    OutputStreamWriter archivo = new OutputStreamWriter((openFileOutput("UsuariosArchivos.txt", Registro.MODE_PRIVATE)));//MODE_APPEND es indeipensable para escribir en el archivo
                    archivo.close();
                    Toast.makeText(Registro.this, "Se formateo el txt", Toast.LENGTH_LONG).show();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Registro.this, "Error al sobreescribir el archivo", Toast.LENGTH_LONG).show();
                }
            }

        });

        registrar.setOnClickListener(new View.OnClickListener() // este es el boton para registrar a los usuarios
        {
            @Override
            public void onClick(View v) // si doy click en registrar
            {

                EditText name = (EditText) findViewById(R.id.nombreText); // estos son las cajas de texto
                EditText id = (EditText) findViewById(R.id.cedulaText);
                try {
                    String nombre = name.getText().toString();
                    int cedula = Integer.parseInt(id.getText().toString());

                    //String insertado="Registrado";
                    String insertado = SingletonArbol.getInstance().metArbol.insertarB(SingletonArbol.getInstance().metArbol.raiz, cedula, nombre); // inserto el arbol
                    if (insertado.endsWith("Registrado")) {
                        name.setText("");
                        id.setText("");

                        //sobreescribir en el archo
                        EscribirTxt(nombre,cedula);//lo guardo en el archivo
                        Toast.makeText(Registro.this, "Se guardo en el Archivo", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        //imprimir
                        Toast.makeText(Registro.this,insertado, Toast.LENGTH_SHORT).show();
                    }

                } catch (ArithmeticException e) {
                    Toast.makeText(Registro.this, "Error al ingresar los datos", Toast.LENGTH_SHORT).show();
                }
            }

            public void EscribirTxt(String nombre, int id) //String file,String text
            {
                try {
                    OutputStreamWriter archivo = new OutputStreamWriter((openFileOutput("UsuariosArchivos.txt", Registro.MODE_APPEND)));//MODE_APPEND es indeipensable para escribir en el archivo
                    archivo.write(id + "," + nombre+"\n");// agrego estos datos al archivo
                    archivo.flush();
                    archivo.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Registro.this, "Error al sobreescribir el archivo", Toast.LENGTH_LONG).show();
                }
            }

        });


    }
}
