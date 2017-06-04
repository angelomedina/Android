package estructuras.grafos.estructurasproyect.com.grafos.Mapas;

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

public class DialogNewArco extends DialogFragment implements Button.OnClickListener {

    public EditText pesoArcoTxt;
    public Button agregaArcoBtn;



    public interface DialogListener{
        void FinalizaDialogoNewArco(int texto);
    }

    public DialogNewArco() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_arco, container);
        pesoArcoTxt = (EditText) view.findViewById(R.id.input_peso_arco_txt);
        agregaArcoBtn = (Button) view.findViewById(R.id.agrega_arco_nuevo_btn);
        agregaArcoBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        DialogListener activity = (DialogListener) getActivity();
        activity.FinalizaDialogoNewArco(Integer.parseInt(pesoArcoTxt.getText().toString()));
        this.dismiss();
    }
}
