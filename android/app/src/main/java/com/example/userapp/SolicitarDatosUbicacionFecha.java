package com.example.userapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.userapp.objects.CitaSolicitud;
import com.example.userapp.objects.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SolicitarDatosUbicacionFecha extends AppCompatActivity implements View.OnClickListener {

    private Button datePicker, hourPicker, next;
    private TextView name;
    private FirebaseDatabase db;
    private int tHour, tMinute;
    private DatePickerDialog datePickerDialog;
    private String date, hour, id, tipo, nombre, correo, telefono, descripcionProblema, direccion, piso, descripDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_datos_ubicacion_fecha);
        initDatePicker();

        db = FirebaseDatabase.getInstance();

       id = getIntent().getExtras().getString("id");
        tipo = getIntent().getExtras().getString("tipo");
        correo = getIntent().getExtras().getString("correo");
        nombre = getIntent().getExtras().getString("nombre");
        telefono = getIntent().getExtras().getString("telefono");
        descripcionProblema = getIntent().getExtras().getString("descripcion");
        piso = getIntent().getExtras().getString("piso");
        direccion = getIntent().getExtras().getString("direccion");
        descripDir = getIntent().getExtras().getString("descripcionPiso");

        name = findViewById(R.id.usuarioNameCita3);

        db.getReference().child("clientes").child(id).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot data) {
                        User user = data.getValue(User.class);
                        name.setText(user.nombre);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );

        datePicker = findViewById(R.id.datePickerButton);
        hourPicker = findViewById(R.id.hourPickerButton);
        next = findViewById(R.id.siguiente3);


        hourPicker.setOnClickListener(this);
        datePicker.setOnClickListener(this);
        next.setOnClickListener(this);
        datePicker.setText(getTodaysDate());
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month - 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                date = makeDateString(day, month, year);
                datePicker.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, dateSetListener, year, month, day);


    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " / " + day + " / " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1) {
            return "ENE";
        }
        if (month == 2) {
            return "FEB";
        }
        if (month == 3) {
            return "MAR";
        }
        if (month == 4) {
            return "ABR";
        }
        if (month == 5) {
            return "MAY";
        }
        if (month == 6) {
            return "JUN";
        }
        if (month == 7) {
            return "JUL";
        }
        if (month == 8) {
            return "AGO";
        }
        if (month == 9) {
            return "SEP";
        }
        if (month == 10) {
            return "OCT";
        }
        if (month == 11) {
            return "NOV";
        }
        if (month == 12) {
            return "DIC";
        }

        return "ENE";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.datePickerButton:
                datePickerDialog.show();
                break;
            case R.id.hourPickerButton:
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        this, android.R.style.Theme_Holo_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tHour = hourOfDay;
                        tMinute = minute;

                        String time = tHour + ":" + tMinute;

                        SimpleDateFormat f24Hours = new SimpleDateFormat(
                                "HH:mm"
                        );
                        try {
                            Date date = f24Hours.parse(time);
                            SimpleDateFormat f12Hours = new SimpleDateFormat(
                                    "hh:mm aa"
                            );
                            hourPicker.setText(f12Hours.format(date));
                            hour = f12Hours.format(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, 12, 0, false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(tHour, tMinute);
                timePickerDialog.show();
                break;

            case R.id.siguiente3:
                if (date == null || hour == null){
                    Toast.makeText(this, "No ha seleccionado una fecha o una hora", Toast.LENGTH_SHORT).show();
                } else {
                    CitaSolicitud cita = new CitaSolicitud(id,nombre,correo,telefono,tipo,descripcionProblema,direccion,piso,descripDir,date,hour);
                    db.getReference().child("citaSolicitudes").child(id).child(tipo).setValue(cita);
                    Intent i = new Intent(this, mensajeFinal.class);
                    i.putExtra("id", id);
                    startActivity(i);
                    finish();
                }
                break;
        }
    }
}