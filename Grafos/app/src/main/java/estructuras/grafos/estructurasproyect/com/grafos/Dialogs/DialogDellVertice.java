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

public class DialogDellVertice extends DialogFragment implements Button.OnClickListener {

    public EditText nombreVerticeTxt;
    public Button agregaVerticeBtn;



    public interface DialogListener{
        void FinalizaDialogoDellVertice(String texto);
    }

    public DialogDellVertice() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_dell_vertice, container);
        nombreVerticeTxt = (EditText) view.findViewById(R.id.input_nombre_dell_vertice_txt);
        agregaVerticeBtn = (Button) view.findViewById(R.id.dell_vertice_btn);
        agregaVerticeBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        DialogListener activity = (DialogListener) getActivity();
        activity.FinalizaDialogoDellVertice(nombreVerticeTxt.getText().toString());
        this.dismiss();
    }

}
