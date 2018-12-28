package com.example.ciro_.mltecnica.View.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.ciro_.mltecnica.Controller.MetodoDePagoController;
import com.example.ciro_.mltecnica.Model.MetodoDePago;
import com.example.ciro_.mltecnica.R;
import com.example.ciro_.mltecnica.Util.ResultListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MetodoDePagoFragment extends Fragment {

    private Float monto;
    private String metodoDePagoID;
    private List<MetodoDePago> listaMetodosDePago;

    private MetodoDePago metodoDePagoSeleccionado;

    private TextView textViewMonto;
    private Spinner spinnerMetodo;
    private ArrayAdapter arrayAdapter;
    private Button botonNext;
    //private ImageView imageViewMetodoDePago;

    private MetodoDePagoController metodoDePagoController;

    private List<String>listaParaElSpinner;

    ComunicacionDataMetodoDePago comunicacionDataMetodoDePago;


    public MetodoDePagoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.comunicacionDataMetodoDePago = (ComunicacionDataMetodoDePago) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_metodo_de_pago, container, false);

        spinnerMetodo = view.findViewById(R.id.spinnerMetodo);
        botonNext = view.findViewById(R.id.cmdNextMetodo);
        textViewMonto = view.findViewById(R.id.textViewMontoMetodo);

        botonNext.setEnabled(false);

        Bundle bundle = new Bundle();
        bundle = getArguments();
        monto = bundle.getFloat("monto");
        textViewMonto.setText(monto.toString());

        metodoDePagoController = new MetodoDePagoController();
        metodoDePagoController.getMetodosDePago(new ResultListener<List<MetodoDePago>>() {
            @Override
            public void finish(List<MetodoDePago> resultado) {
                listaMetodosDePago = resultado;
                /*arrayAdapter = new ArrayAdapter(MetodoDePagoActivity.this,android.R.layout.simple_dropdown_item_1line, listaMetodosDePago);
                spinnerMetodo.setAdapter(arrayAdapter);*/

                setearSpinner(listaMetodosDePago);
            }
        });

        spinnerMetodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                metodoDePagoSeleccionado = listaMetodosDePago.get(position);
                metodoDePagoID = metodoDePagoSeleccionado.getId();

                //Glide
                /*Glide.with(getApplicationContext())
                        .load(metodoDePagoSeleccionado.getThumbnail())
                        .into(imageViewMetodoDePago);
            */
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        botonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putFloat("monto",monto);
                bundle.putString("id",metodoDePagoID);
                BancoFragment bancoFragment = new BancoFragment();
                bancoFragment.setArguments(bundle);
                comunicacionDataMetodoDePago.comunicarDataMetodoDePago(metodoDePagoID,bancoFragment);
            }
        });

        return view;


    }

    private void setearSpinner(List<MetodoDePago> listaDeMetodosDePago){
        listaParaElSpinner = new ArrayList<>();
        for (MetodoDePago deMetodosDePago : listaDeMetodosDePago) {
            listaParaElSpinner.add(deMetodosDePago.getName());
        }

        SpinnerAdapter spinnerAdapter = new com.example.ciro_.mltecnica.View.Adapters.SpinnerAdapter(listaParaElSpinner,getActivity(),listaMetodosDePago);
        spinnerMetodo.setAdapter(spinnerAdapter);
        botonNext.setEnabled(true);

    }

    public interface ComunicacionDataMetodoDePago{
        void comunicarDataMetodoDePago(String metodoDePagoID,Fragment fragment);

    }



}
