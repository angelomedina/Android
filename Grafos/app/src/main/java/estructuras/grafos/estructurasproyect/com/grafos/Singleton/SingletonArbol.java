package estructuras.grafos.estructurasproyect.com.grafos.Singleton;

import estructuras.grafos.estructurasproyect.com.grafos.Arbol.MetodosArbol;

/**
 * Created by Usuario on 3/6/2017.
 */

public class SingletonArbol {

    public MetodosArbol metArbol=new MetodosArbol();//estoy instanciando la los metodos de

    private static SingletonArbol _instance;

    public static SingletonArbol getInstance()
    {
        if (_instance == null)
        {
            _instance = new SingletonArbol();
        }
        return _instance;
    }

}
