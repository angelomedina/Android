package estructuras.grafos.estructurasproyect.com.grafos.Singleton;

import estructuras.grafos.estructurasproyect.com.grafos.Mapas.MetodosGrafo;

/**
 * Created by Usuario on 4/6/2017.
 */

public class SingletonGrafo {

    public  MetodosGrafo metGrafo=new MetodosGrafo();//estoy instanciando la los metodos de grafo

    private static SingletonGrafo _instance;

    public static SingletonGrafo getInstance()
    {
        if (_instance == null)
        {
            _instance = new SingletonGrafo();
        }
        return _instance;
    }
}
