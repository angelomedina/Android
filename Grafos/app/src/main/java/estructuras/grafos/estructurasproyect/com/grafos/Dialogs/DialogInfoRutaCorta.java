package estructuras.grafos.estructurasproyect.com.grafos.Dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import estructuras.grafos.estructurasproyect.com.grafos.R;

/**
 * Created by Admi on 1/6/2017.
 */

public class DialogInfoRutaCorta extends DialogFragment
        implements
        Button.OnClickListener
        {

    public Spinner lista;
    public EditText destinoTxt;
    public EditText origenTxt;
    public Button recorrerBtn;
    public String[] datos_lista = {"Peso","Saltos"};
    public String tipo;


            public interface DialogListener{
        void FinalizaDialogoNewArco(String origen, String destino, String tipo);
    }

    public DialogInfoRutaCorta() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_info_ruta_corta, container);

        lista = (Spinner) view.findViewById(R.id.spinner_tipo);
        ArrayAdapter adaptadorLista = new ArrayAdapter<String>(this.getActivity(),android.R.layout.select_dialog_item, datos_lista);
        lista.setAdapter(adaptadorLista);

        destinoTxt = (EditText) view.findViewById(R.id.input_vertice_destino_txt);
        origenTxt = (EditText) view.findViewById(R.id.input_vertice_origen_txt);
        recorrerBtn = (Button) view.findViewById(R.id.trazar_ruta_btn);

        recorrerBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        ObtenerTipoRecorrido();
        DialogListener activity = (DialogListener) getActivity();
        activity.FinalizaDialogoNewArco(origenTxt.getText().toString(), destinoTxt.getText().toString(), tipo);
        this.dismiss();
    }

    public void ObtenerTipoRecorrido(){
        int num = lista.getSelectedItemPosition();

        switch (num) {
            case 0:
                tipo = "Peso";
                break;
            case 1:
                tipo = "Saltos";
        }
    }

}
