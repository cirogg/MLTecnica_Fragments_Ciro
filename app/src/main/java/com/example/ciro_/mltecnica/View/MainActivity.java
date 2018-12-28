package com.example.ciro_.mltecnica.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.ciro_.mltecnica.Model.MetodoDePago;
import com.example.ciro_.mltecnica.R;
import com.example.ciro_.mltecnica.View.Fragments.BancoFragment;
import com.example.ciro_.mltecnica.View.Fragments.CuotasFragment;
import com.example.ciro_.mltecnica.View.Fragments.FinalFragment;
import com.example.ciro_.mltecnica.View.Fragments.MetodoDePagoFragment;
import com.example.ciro_.mltecnica.View.Fragments.MontoFragment;

public class MainActivity extends AppCompatActivity implements MontoFragment.ComunicacionDataMonto, MetodoDePagoFragment.ComunicacionDataMetodoDePago, BancoFragment.ComunicacionDataBanco, CuotasFragment.ComunicacionDataCuotas,FinalFragment.ComunicacionDataFinal {

    private Float monto;
    private String metodoDePagoID;
    private String bancoNombre;
    private String cuotas;

    Toolbar toolbar;


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

        cargarFragment(fragment);

    }

    @Override
    public void comunicarDataMetodoDePago(String metodoDePagoID, Fragment fragment) {
        this.metodoDePagoID = metodoDePagoID;

        cargarFragment(fragment);
    }

    @Override
    public void comunicarDataBanco(String bancoNombre, Fragment fragment) {
        this.bancoNombre = bancoNombre;

        cargarFragment(fragment);
    }

    @Override
    public void comunicarDataCuotas(String cuotas, Fragment fragment) {
        this.cuotas = cuotas;

        for (Fragment fragment1:getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment1).commit();
        }

        cargarFragment(fragment);
    }

    @Override
    public void comunicarDataFinal() {



        for (Fragment fragment1:getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment1).commit();
        }
        MontoFragment montoFragment = new MontoFragment();
        cargarFragment(montoFragment);

        //alerta();

        monto = null;
        metodoDePagoID = null;
        bancoNombre = null;
        cuotas = null;
    }

    private void alerta(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Monto: $" + monto + "\n Metodo de pago: " + metodoDePagoID + "\n Banco: "+ bancoNombre + "\n Cuotas: " + cuotas)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
