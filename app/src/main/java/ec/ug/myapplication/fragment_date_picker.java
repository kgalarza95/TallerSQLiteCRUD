package ec.ug.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_date_picker#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_date_picker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private DatePickerDialog.OnDateSetListener escuchador;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_date_picker() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_date_picker.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_date_picker newInstance(String param1, String param2) {
        fragment_date_picker fragment = new fragment_date_picker();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_picker, container, false);
    }

    public static fragment_date_picker newInstance(DatePickerDialog.OnDateSetListener listener) {
        fragment_date_picker fragment = new fragment_date_picker();
        fragment.setEscuchador(listener); // se mantenga escuchando el evneto
        return fragment; // retornar el fragment
    }
    //**********************************************************************************************\\
    //** GETTERS Y SETTERS **\\
    public void setEscuchador(DatePickerDialog.OnDateSetListener listener){
        this.escuchador = listener; // actaliza lo escuchado
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar fecha = Calendar.getInstance(); //Api Calendar

        int year = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), escuchador, year, mes, dia); // retorna nueva instancia
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day){

    }
}