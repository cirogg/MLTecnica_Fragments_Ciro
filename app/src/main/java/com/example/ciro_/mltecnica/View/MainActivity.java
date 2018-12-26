package com.example.ciro_.mltecnica.View;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ciro_.mltecnica.Model.MetodoDePago;
import com.example.ciro_.mltecnica.R;
import com.example.ciro_.mltecnica.View.Fragments.BancoFragment;
import com.example.ciro_.mltecnica.View.Fragments.CuotasFragment;
import com.example.ciro_.mltecnica.View.Fragments.MetodoDePagoFragment;
import com.example.ciro_.mltecnica.View.Fragments.MontoFragment;

public class MainActivity extends AppCompatActivity implements MontoFragment.ComunicacionDataMonto, MetodoDePagoFragment.ComunicacionDataMetodoDePago, BancoFragment.ComunicacionDataBanco, CuotasFragment.ComunicacionDataCuotas {

    private Float monto;
    private String metodoDePagoID;
    private String bancoNombre;
    private String cuotas;


    Button botonNextMonto;
    EditText editTextMonto;

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        MontoFragment montoFragment = new MontoFragment();
        cargarFragment(montoFragment);

        //botonNextMonto = findViewById(R.id.cmdNextMonto);
        //editTextMonto = findViewById(R.id.editTextMonto);

        /*botonNextMonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(editTextMonto.getText().toString().isEmpty())){
                    monto = Float.parseFloat(editTextMonto.getText().toString());
                    nextActivity();
                }
            }
        });*/

    }

    private void nextActivity(){
        Intent intent = new Intent(this,MetodoDePagoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putFloat("monto",monto);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void cargarFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.animation_slide_in,R.anim.animation_slide_out);
        fragmentTransaction.replace(R.id.layoutContenedorFragments,fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }


    @Override
    public void comunicarDataMonto(Float monto, Fragment fragment) {
        this.monto = monto;
        Toast.makeText(this, monto.toString(), Toast.LENGTH_SHORT).show();
        cargarFragment(fragment);

    }

    @Override
    public void comunicarDataMetodoDePago(String metodoDePagoID, Fragment fragment) {
        this.metodoDePagoID = metodoDePagoID;
        Toast.makeText(this, metodoDePagoID, Toast.LENGTH_SHORT).show();
        cargarFragment(fragment);
    }

    @Override
    public void comunicarDataBanco(String bancoNombre, Fragment fragment) {
        this.bancoNombre = bancoNombre;
        Toast.makeText(this, bancoNombre, Toast.LENGTH_SHORT).show();
        cargarFragment(fragment);
    }

    @Override
    public void comunicarDataCuotas(String cuotas, Fragment fragment) {
        this.cuotas = cuotas;
        Toast.makeText(this, cuotas, Toast.LENGTH_SHORT).show();

        for (Fragment fragment1:getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment1).commit();
        }
    }
}