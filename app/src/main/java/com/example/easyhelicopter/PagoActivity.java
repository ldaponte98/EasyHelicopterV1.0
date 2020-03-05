package com.example.easyhelicopter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.easyhelicopter.Tools.Tools;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.card.payment.CardIOActivity;
import io.card.payment.CardType;
import io.card.payment.CreditCard;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PagoActivity extends AppCompatActivity {

    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //Hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    private TextView txtfecha;
    private TextView txthora;
    private ImageView btn_obtener_fecha;
    private ImageView btn_obtener_hora;
    private Spinner select_mes, select_year;
    private TextView txt_titular_tarjeta;
    private TextView txt_numero_tarjeta;
    private TextView txt_vence_tarjeta;
    private TextView txt_codigo_cvv;
    private ImageView imagen_tipo_tarjeta;

    private static final int SCAN_RESULT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);

        txtfecha = (TextView) findViewById(R.id.txt_fecha);
        txthora = (TextView) findViewById(R.id.txt_hora);
        txt_numero_tarjeta = (TextView) findViewById(R.id.label_numero_tarjeta);
        txt_vence_tarjeta = (TextView) findViewById(R.id.label_vence);
        //txt_codigo_cvv = (TextView) findViewById(R.id.label_codigo_cvv);
        btn_obtener_fecha = (ImageView) findViewById(R.id.btn_escojer_fecha);
        btn_obtener_hora = (ImageView) findViewById(R.id.btn_escojer_hora);
        imagen_tipo_tarjeta = (ImageView) findViewById(R.id.imagen_tipo_tarjeta);
        //llenarSpinners();

       // lista_detalle_viaje = (ListView) findViewById(R.id.lista_detalle_pago);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btn_obtener_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha();
            }
         });
        txtfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha();
            }
        });

        btn_obtener_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerHora();
            }
        });
        txthora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerHora();
            }
        });

        txtfecha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                llenardetalles();
            }
        });

        txthora.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                llenardetalles();
            }
        });
        llenardetalles();
    }

    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                final int mesActual = month + 1;

                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                txtfecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
            }
        },anio, mes, dia);

        recogerFecha.show();

    }

    private void obtenerHora(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String horaFormateada =  (hourOfDay < 9)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                String minutoFormateado = (minute < 9)? String.valueOf(CERO + minute):String.valueOf(minute);

                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }

                txthora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
            }

        }, hora, minuto, false);
        recogerHora.show();
    }

    private void llenardetalles(){
        TextView txt_valor_pago, txt_distancia,txt_impuesto, txt_origen,txt_destino, txt_fecha_salida, txt_hora_salida;

        txt_valor_pago = findViewById(R.id.txt_valor_a_pagar);
        txt_distancia = findViewById(R.id.txt_distancia);
        txt_impuesto = findViewById(R.id.txt_impuesto);
        txt_origen = findViewById(R.id.txt_origen);
        txt_destino = findViewById(R.id.txt_destino);
        txt_fecha_salida = findViewById(R.id.txt_fecha_salida);
        txt_hora_salida = findViewById(R.id.txt_hora_salida);
        double impuesto = 25000;
        double valor_x_km = 1000;
        double total_pagar = (MapaActivity.distancia_viaje * valor_x_km) + impuesto;
        Locale.setDefault(Locale.US);
        DecimalFormat num = new DecimalFormat("#.###");
        String i = num.format(total_pagar);
        String total = new Tools().convertirSinNotacionCientifica(total_pagar);

        double distancia = new Tools().redondearDecimales(MapaActivity.distancia_viaje, 0);
        txt_valor_pago.setText("$"+new Tools().ParseNumberWithPoints(total));
        txt_distancia.setText(new Tools().ParseNumberWithPoints(String.valueOf(distancia))+" Km");
        txt_impuesto.setText("$"+new Tools().ParseNumberWithPoints(String.valueOf(impuesto)));
        txt_origen.setText("("+MapaActivity.ciudad_origen.getNombre()+") "+MapaActivity.puerto_origen.getNombre());
        txt_destino.setText("("+MapaActivity.ciudad_destino.getNombre()+") "+MapaActivity.puerto_destino.getNombre());
        txt_fecha_salida.setText(new Tools().FechaFormato5(txtfecha.getText().toString()));
        txt_hora_salida.setText(txthora.getText());

    }

    public void llenarSpinners(){
        /*
        List<String> meses = new ArrayList<>();
        meses.add("Enero");
        meses.add("Febrero");
        meses.add("Marzo");
        meses.add("Abril");
        meses.add("Mayo");
        meses.add("Junio");
        meses.add("Julio");
        meses.add("Agosto");
        meses.add("Septiembre");
        meses.add("Octubre");
        meses.add("Noviembre");
        meses.add("Diciembre");

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,R.layout.my_spinner_desing, meses);
        select_mes.setAdapter(adapter);

        List<String> years = new ArrayList<>();
        Date fecha = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy");

        int year = Integer.parseInt(formateador.format(fecha));
        int max_year = year + 10;
        for (int i = year; i <= max_year; i++){
            years.add(String.valueOf(i));
        }
        ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter(this,R.layout.my_spinner_desing, years);
        select_year.setAdapter(adapter2);
    */

    }

    public void escanearTarjeta(View view){
        Intent intent = new Intent(this, CardIOActivity.class)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false);
        startActivityForResult(intent, SCAN_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SCAN_RESULT){
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)){
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                txt_numero_tarjeta.setText(scanResult.getRedactedCardNumber());
                //txt_codigo_cvv.setText(scanResult.cvv);
                if (scanResult.isExpiryValid()){
                    int month = scanResult.expiryMonth;
                    int year = scanResult.expiryYear;
                    txt_vence_tarjeta.setText(month+"/"+year);
                }
                establecer_img_tipo_tarjeta(scanResult);

            }
        }
    }

    public void establecer_img_tipo_tarjeta(CreditCard scanResult){
        switch (scanResult.getCardType()){
            case JCB:
                imagen_tipo_tarjeta.setImageDrawable(getResources().getDrawable(R.drawable.cio_ic_jcb));
                break;
            case AMEX:
                imagen_tipo_tarjeta.setImageDrawable(getResources().getDrawable(R.drawable.cio_ic_amex));
                break;
            case VISA:
                imagen_tipo_tarjeta.setImageDrawable(getResources().getDrawable(R.drawable.cio_ic_visa));
                break;
            case DISCOVER:
                imagen_tipo_tarjeta.setImageDrawable(getResources().getDrawable(R.drawable.cio_ic_discover));
                break;
            case DINERSCLUB:
                imagen_tipo_tarjeta.setImageDrawable(getResources().getDrawable(R.drawable.cio_card_io_logo));
                break;
            case MASTERCARD:
                imagen_tipo_tarjeta.setImageDrawable(getResources().getDrawable(R.drawable.cio_ic_mastercard));
                break;
            case UNKNOWN:
                imagen_tipo_tarjeta.setImageDrawable(getResources().getDrawable(R.drawable.cio_paypal_logo));
                break;
            case INSUFFICIENT_DIGITS:
                imagen_tipo_tarjeta.setImageDrawable(getResources().getDrawable(R.drawable.cio_ic_paypal_monogram));
                break;

        }
    }
}
