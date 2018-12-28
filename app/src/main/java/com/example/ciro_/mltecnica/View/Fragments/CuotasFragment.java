package com.example.ciro_.mltecnica.View.Fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ciro_.mltecnica.Controller.CuotasController;
import com.example.ciro_.mltecnica.Model.Cuotas;
import com.example.ciro_.mltecnica.Model.PayerCost;
import com.example.ciro_.mltecnica.R;
import com.example.ciro_.mltecnica.Util.ResultListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CuotasFragment extends Fragment {

    private Float monto;
    private String metodoDePagoID;
    private String bancoSeleccionadoID;
    private String bancoSeleccionadoNombre;
    private List<Cuotas> listaDeCuotas;

    private Cuotas cuotaSeleccionada;
    private List<PayerCost> listaDePayerCost;
    private PayerCost payerCostSeleccionado;
    private String planSeleccionado;

    private TextView textViewMonto;
    private Spinner spinnerCuotas;
    private ArrayAdapter arrayAdapter;
    private Button botonNext;

    private Activity activity;

    private CuotasController cuotasController;

    ComunicacionDataCuotas comunicacionDataCuotas;

    public CuotasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.comunicacionDataCuotas = (ComunicacionDataCuotas) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cuotas, container, false);

        activity = getActivity();

        spinnerCuotas = view.findViewById(R.id.spinnerCuota);
        botonNext = view.findViewById(R.id.cmdNextCuota);
        textViewMonto = view.findViewById(R.id.textViewMontoCuota);

        botonNext.setEnabled(false);

        Bundle bundle = new Bundle();
        bundle = getArguments();

        monto = bundle.getFloat("monto");
        metodoDePagoID = bundle.getString("id");
        bancoSeleccionadoID = bundle.getString("idBanco");
        bancoSeleccionadoNombre = bundle.getString("nombreBanco");
        textViewMonto.setText(monto.toString());

        cuotasController = new CuotasController();
        cuotasController.getCuotas(monto.toString(),metodoDePagoID,bancoSeleccionadoID, new ResultListener<List<Cuotas>>() {
            @Override
            public void finish(List<Cuotas> resultado) {
                listaDeCuotas = resultado;
                obtenerPayerCost(listaDeCuotas);
                /*arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line, listaDePayerCost);
                spinnerCuotas.setAdapter(arrayAdapter);*/
            }
        });

        spinnerCuotas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                payerCostSeleccionado = listaDePayerCost.get(position);
                planSeleccionado = payerCostSeleccionado.getRecommendedMessage();
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
                bundle.putString("id", metodoDePagoID);
                bundle.putString("idBanco",bancoSeleccionadoID);
                bundle.putString("nombreBanco", bancoSeleccionadoNombre);
                bundle.putString("cuotas",planSeleccionado);
                FinalFragment finalFragment = new FinalFragment();
                finalFragment.setArguments(bundle);
                comunicacionDataCuotas.comunicarDataCuotas(planSeleccionado,finalFragment);

            }
        });

        return view;
    }

    private void obtenerPayerCost(List<Cuotas> listaDeCuotas){
        for (Cuotas listaDeCuota : listaDeCuotas) {
            listaDePayerCost = listaDeCuota.getPayerCosts();
        }
        arrayAdapter = new ArrayAdapter(activity,android.R.layout.simple_dropdown_item_1line, listaDePayerCost);
        spinnerCuotas.setAdapter(arrayAdapter);
        botonNext.setEnabled(true);
    }

    public interface ComunicacionDataCuotas{
        void comunicarDataCuotas(String cuotas,Fragment fragment);

    }

}
