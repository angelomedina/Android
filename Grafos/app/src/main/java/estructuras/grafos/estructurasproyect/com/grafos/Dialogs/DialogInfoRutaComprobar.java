package estructuras.grafos.estructurasproyect.com.grafos.Dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import estructuras.grafos.estructurasproyect.com.grafos.R;

/**
 * Created by Admi on 1/6/2017.
 */

public class DialogInfoRutaComprobar extends DialogFragment
        implements
        Button.OnClickListener
        {

    public EditText destinoTxt;
    public EditText origenTxt;
    public Button recorrerBtn;

    public interface DialogListener{
        void FinalizaDialogInfoRutaComprobar(String origen, String destino);
    }

    public DialogInfoRutaComprobar() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_info_ruta_comprobar, container);

        destinoTxt = (EditText) view.findViewById(R.id.input_destino_comprobar_ruta_txt);
        origenTxt = (EditText) view.findViewById(R.id.input_origen_comprobar_ruta_txt);
        recorrerBtn = (Button) view.findViewById(R.id.comprobar_ruta_btn);

        recorrerBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        DialogListener activity = (DialogListener) getActivity();
        activity.FinalizaDialogInfoRutaComprobar(origenTxt.getText().toString(), destinoTxt.getText().toString());
        this.dismiss();
    }

}
