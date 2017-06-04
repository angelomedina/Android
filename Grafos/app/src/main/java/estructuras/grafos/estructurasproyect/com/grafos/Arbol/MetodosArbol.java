package estructuras.grafos.estructurasproyect.com.grafos.Arbol;

import android.util.Log;

/**
 * Created by Usuario on 3/6/2017.
 */

public class MetodosArbol {

    public Arbol raiz;

    public boolean izquierda;
    public Arbol anterior, encontrado;
    public  String mensaje;

    public int contador;
    public int contador2;// para contar la cantidad de arboles por nivel


    public String insertarB(Arbol aux, int cedula,String nombre) // este se usa para el gregidtro
    {
        Arbol verifica=buscar(raiz,cedula);


        if(verifica == null && cedula != 123)
        {
            Arbol nuevo = new Arbol(cedula,nombre);
            if (raiz == null)
            {
                raiz = aux = nuevo;
                return mensaje = "Registrado";
            }
            if (cedula < aux.cedula)
            {
                if (aux.izq == null) {
                    aux.izq = nuevo;
                    return mensaje =  "Registrado";
                }
                else
                {
                    insertarB(aux.izq, cedula,nombre);
                }
            }
            else
            if (cedula > aux.cedula)
            {
                if (aux.der == null) {
                    aux.der = nuevo;
                    return mensaje = "Registrado";
                }
                else
                {
                    insertarB(aux.der, cedula,nombre);
                }
            }
            else
            if (cedula == aux.cedula)
            {
                return mensaje = "No se puede repetir cedulas";
            }
            return mensaje;
        }
        else
        {
            return mensaje = "No se puede repetir cedulas";
        }
    }

    public String insertarC(Arbol aux, int cedula,String nombre) // este se usa para cargar los datos
    {
        Arbol nuevo = new Arbol(cedula,nombre);
        if (raiz == null)
        {
            raiz = aux = nuevo;
            return mensaje = "Registrado";
        }
        if (cedula < aux.cedula)
        {
            if (aux.izq == null) {
                aux.izq = nuevo;
                return mensaje =  "Registrado";
            }
            else
            {
                insertarB(aux.izq, cedula,nombre);
            }
        }
        else
        if (cedula > aux.cedula)
        {
            if (aux.der == null) {
                aux.der = nuevo;
                return mensaje = "Registrado";
            }
            else
            {
                insertarB(aux.der, cedula,nombre);
            }
        }
        else
        if (cedula == aux.cedula)
        {
            return mensaje = "No se puede repetir cedulas";
        }
        return mensaje;


    }



    public Arbol buscar(Arbol aux, int cedula)//busca persona
    {
        if (aux == null)
        {
            return encontrado = null;
        }
        if (cedula < aux.cedula)
        {
            izquierda = true;//solo se utiliza en la eliminacion del personal
            anterior = aux;//solo se utiliza en la eliminacion del personal
            buscar(aux.izq, cedula);
        }
        else
        if (cedula > aux.cedula)
        {
            izquierda = false;//solo se utiliza en la eliminacion del personal
            anterior = aux;//solo se utiliza en la eliminacion del personal
            buscar(aux.der, cedula);
        }
        else
        if (cedula == aux.cedula)
        {
            return encontrado = aux;
                       	/*imprimir que lo encontro*/
        }
        return encontrado;
    }


    int alt;
    /*imprimir nivel de personas*/
    public void niveles(Arbol aux, int h)//h se recibe como -1 xq son niveles
    {
        if (raiz == null)//no haga nada sino hay nada en el arbol
        {
            return;
        }
        if (aux == null)
        {
            return;
        }
        h++;
        niveles(aux.izq, h);
        if (alt < h)
        {
            alt = h;
        }
        niveles(aux.der, h);
    }

    public int returnNiveles()
    {
        if(raiz!=null)
        {
            niveles(raiz, -1);
            return alt;
        }else
            return 0;

    }

    public void imprimeXniveles(Arbol aux, int h)
    {
        int cantNiveles=returnNiveles();
        int cont=0;

        while(cont <= cantNiveles)
        {
            if(cont <=cantNiveles)
            {
                //System.out.println("Nivel: "+cont);
                Log.d("Nivel: ",Integer.toString(cont));
                imprimeNivles(raiz, h,cont);
            }
            cont++;
        }
    }

    public void imprimeNivles(Arbol aux, int h,int nivel)
    {

        if (aux == null)//no haga nada sino hay nada en el arbol
        {
            return;
        }
        h++;
        if (nivel == h )
        {
            //System.out.println(aux.cedula);
            Log.d("",Integer.toString(aux.cedula));

        }
        imprimeNivles(aux.izq, h,nivel);
        imprimeNivles(aux.der, h,nivel);

    }


}
