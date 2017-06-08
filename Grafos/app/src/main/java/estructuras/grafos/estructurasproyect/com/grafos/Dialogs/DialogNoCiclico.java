package estructuras.grafos.estructurasproyect.com.grafos.Dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import estructuras.grafos.estructurasproyect.com.grafos.R;

/**
 * Created by Admi on 1/6/2017.
 */

public class DialogNoCiclico extends DialogFragment implements Button.OnClickListener {

    public Button agregaArcoBtn;
    public TextView ciclico_texto;

    public DialogNoCiclico() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_no_ciclico, container);
        ciclico_texto = (TextView) view.findViewById(R.id.output_ciclico_txt);
        agregaArcoBtn = (Button) view.findViewById(R.id.ciclico_aceptar_btn);
        agregaArcoBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        this.dismiss();
    }
}
