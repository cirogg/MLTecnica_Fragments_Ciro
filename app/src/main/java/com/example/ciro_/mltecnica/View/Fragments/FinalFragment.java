package com.example.ciro_.mltecnica.View.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ciro_.mltecnica.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinalFragment extends Fragment {

    TextView textViewMonto;
    TextView textViewMetodo;
    TextView textViewBanco;
    TextView textViewCuotas;

    Button botonFin;

    private Float monto;
    private String metodo;
    private String banco;
    private String cuotas;

    ComunicacionDataFinal comunicacionDataFinal;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.comunicacionDataFinal = (ComunicacionDataFinal) context;
    }

    public FinalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_final, container, false);

        textViewMonto = view.findViewById(R.id.textViewFinalMonto);
        textViewMetodo = view.findViewById(R.id.textViewFinalMetodo);
        textViewBanco = view.findViewById(R.id.textViewFinalMBanco);
        textViewCuotas = view.findViewById(R.id.textViewFinalMCuotas);

        botonFin = view.findViewById(R.id.cmdNextFinal);

        Bundle bundle = new Bundle();
        bundle = getArguments();
        monto = bundle.getFloat("monto");
        metodo = bundle.getString("id");
        banco = bundle.getString("nombreBanco");
        cuotas = bundle.getString("cuotas");

        textViewMonto.setText(monto.toString());
        textViewMetodo.setText(metodo);
        textViewBanco.setText(banco);
        textViewCuotas.setText(cuotas);

        botonFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicacionDataFinal.comunicarDataFinal();
            }
        });



        return view;
    }

    public interface ComunicacionDataFinal{
        void comunicarDataFinal();
    }

}
