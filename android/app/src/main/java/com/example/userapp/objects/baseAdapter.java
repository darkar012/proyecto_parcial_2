package com.example.userapp.objects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.userapp.R;

import java.util.ArrayList;

public class baseAdapter extends BaseAdapter {

    private ArrayList<Cita> citas;

    public baseAdapter() {citas= new ArrayList<>();}

    public void clear(){
        citas.clear();
        notifyDataSetChanged();
    }
    public void addCitas(Cita cita){
        citas.add(cita);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return citas.size();
    }

    @Override
    public Object getItem(int position) {
        return citas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int pos, View renglon, ViewGroup lista) {
        LayoutInflater inflater = LayoutInflater.from(lista.getContext());
        View renglonView = inflater.inflate(R.layout.row, null);
        Cita cita = citas.get(pos);

        TextView fecha = renglonView.findViewById(R.id.fechaRow);
        TextView hora = renglonView.findViewById(R.id.horaRow);
        TextView nombreTecnico = renglonView.findViewById(R.id.nombreTecn);
        TextView telTecnico = renglonView.findViewById(R.id.telefonoTecn);
        TextView tipo = renglonView.findViewById(R.id.tipoRow);

        fecha.setText(cita.getFecha());
        hora.setText(cita.getHora());
        nombreTecnico.setText(cita.getTrabajadorNombre());
        telTecnico.setText(String.valueOf(cita.getTrabajadorTel()));
        tipo.setText(cita.getTipo());

        return renglonView;
    }
}
