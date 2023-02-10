package com.nguyenhoanghiep.orderfood.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nguyenhoanghiep.orderfood.DTO.BanDTO;
import com.nguyenhoanghiep.orderfood.DTO.NhanvienDTO;
import com.nguyenhoanghiep.orderfood.Hienthinhanvien;
import com.nguyenhoanghiep.orderfood.R;

import java.util.List;

public class AdapterHienthinhanvien extends BaseAdapter {
    Hienthinhanvien context;
    int item_layout;
    List<NhanvienDTO> list_nhanvien;

    public AdapterHienthinhanvien(Hienthinhanvien context, int item_layout, List<NhanvienDTO> list_nhanvien) {
        this.context = context;
        this.item_layout = item_layout;
        this.list_nhanvien = list_nhanvien;
    }

    @Override
    public int getCount() {
        return list_nhanvien.size();
    }

    @Override
    public Object getItem(int i) {
        return list_nhanvien.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder holder= new viewHolder();
        if(view==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= inflater.inflate(item_layout,null);
            holder.txtTennhanvien=view.findViewById(R.id.txtTennhanvien);
            holder.txtCmnd=view.findViewById(R.id.txtCmnd);
            holder.imvSua= view.findViewById(R.id.imvSua);
            holder.imvXoa= view.findViewById(R.id.imvXoa);
            view.setTag(holder);
        }
        else {
            holder= (viewHolder) view.getTag();
        }
        NhanvienDTO p= list_nhanvien.get(i);
        holder.txtTennhanvien.setText(p.getTENDANGNHAP());
        holder.txtCmnd.setText(String.valueOf(p.getCMND()));
        holder.imvXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.Xoanhanvien(p.getMANV());
            }
        });
        holder.imvSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            context.Suanhanvien(Integer.valueOf(p.getMANV()));
            }
        });
        return view;
    }
    private static class viewHolder{
        TextView txtTennhanvien, txtCmnd;
        ImageView imvXoa, imvSua;
    }
}
