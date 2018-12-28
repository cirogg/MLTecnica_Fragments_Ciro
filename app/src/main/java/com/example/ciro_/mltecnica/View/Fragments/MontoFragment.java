package com.example.ciro_.mltecnica.View.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ciro_.mltecnica.R;
import com.example.ciro_.mltecnica.View.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MontoFragment extends Fragment {

    Float monto;

    Button botonNextMonto;
    EditText editTextMonto;
    ComunicacionDataMonto comunicacionDataMonto;

    public MontoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.comunicacionDataMonto = (ComunicacionDataMonto) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monto, container, false);

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                    Toast.makeText(getActivity(), "OLA", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        botonNextMonto = view.findViewById(R.id.cmdNextMonto);
        editTextMonto = view.findViewById(R.id.editTextMonto);

        botonNextMonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(editTextMonto.getText().toString().isEmpty())){
                    monto = Float.parseFloat(editTextMonto.getText().toString());

                    MetodoDePagoFragment metodoDePagoFragment = new MetodoDePagoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putFloat("monto",monto);
                    metodoDePagoFragment.setArguments(bundle);
                    comunicacionDataMonto.comunicarDataMonto(monto,metodoDePagoFragment);

                    //nextActivity();
                }
            }
        });

        return view;
    }

    public interface ComunicacionDataMonto{
        void comunicarDataMonto(Float monto,Fragment fragment);

    }



}
