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
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ciro_.mltecnica.Controller.BancoController;
import com.example.ciro_.mltecnica.Model.Banco;
import com.example.ciro_.mltecnica.R;
import com.example.ciro_.mltecnica.Util.ResultListener;
import com.example.ciro_.mltecnica.View.Adapters.SpinnerAdapterBanco;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BancoFragment extends Fragment {

    private Float monto;
    private String metodoDePagoID;
    private List<Banco> listaDeBancos;

    private Banco bancoSeleccionado;
    private String bancoSeleccionadoID;
    private String getBancoSeleccionadoNombre;

    private TextView textViewMonto;
    private Spinner spinnerBancos;
    private ArrayAdapter arrayAdapter;
    private Button botonNext;

    private BancoController bancoController;

    private List<String>listaParaElSpinner;

    ComunicacionDataBanco comunicacionDataBanco;


    public BancoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.comunicacionDataBanco = (ComunicacionDataBanco) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_banco, container, false);

        spinnerBancos = view.findViewById(R.id.spinnerBanco);
        botonNext = view.findViewById(R.id.cmdNextBanco);
        textViewMonto = view.findViewById(R.id.textViewMontoBanco);

        Bundle bundle = new Bundle();
        bundle = getArguments();
        monto = bundle.getFloat("monto");
        metodoDePagoID = bundle.getString("id");
        textViewMonto.setText(monto.toString());

        bancoController = new BancoController();
        bancoController.getBancos(new ResultListener<List<Banco>>() {
            @Override
            public void finish(List<Banco> resultado) {
                listaDeBancos = resultado;
                setearSpinner(listaDeBancos);
                /*arrayAdapter = new ArrayAdapter(BancoActivity.this,android.R.layout.simple_dropdown_item_1line,listaDeBancos);
                spinnerBancos.setAdapter(arrayAdapter);*/
            }
        },metodoDePagoID);


        spinnerBancos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bancoSeleccionado = listaDeBancos.get(position);
                bancoSeleccionadoID = bancoSeleccionado.getId();
                getBancoSeleccionadoNombre = bancoSeleccionado.getName();


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
                bundle.putString("nombreBanco",getBancoSeleccionadoNombre);
                CuotasFragment cuotasFragment = new CuotasFragment();
                cuotasFragment.setArguments(bundle);
                comunicacionDataBanco.comunicarDataBanco(getBancoSeleccionadoNombre,cuotasFragment);
            }
        });


        return view;
    }

    private void setearSpinner(List<Banco> listaDeBancos){
        listaParaElSpinner = new ArrayList<>();
        for (Banco deBancos : listaDeBancos) {
            listaParaElSpinner.add(deBancos.getName());
        }

        SpinnerAdapterBanco spinnerAdapterBanco = new SpinnerAdapterBanco(listaParaElSpinner,getActivity(),listaDeBancos);
        spinnerBancos.setAdapter(spinnerAdapterBanco);

    }

    public interface ComunicacionDataBanco{
        void comunicarDataBanco(String bancoNombre,Fragment fragment);

    }

}
