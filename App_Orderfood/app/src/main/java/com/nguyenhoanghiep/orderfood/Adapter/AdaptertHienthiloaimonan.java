package com.nguyenhoanghiep.orderfood.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nguyenhoanghiep.orderfood.DTO.BanDTO;
import com.nguyenhoanghiep.orderfood.DTO.LoaimonanDTO;
import com.nguyenhoanghiep.orderfood.Hienthiloaimonan;
import com.nguyenhoanghiep.orderfood.R;

import java.util.List;

public class AdaptertHienthiloaimonan extends BaseAdapter {
    private Activity context;
    private  int item_layout;
    private List<LoaimonanDTO> list_loaimonan;

    public AdaptertHienthiloaimonan(Activity context, int item_layout, List<LoaimonanDTO> loaiMonAnDTOList) {
        this.context = context;
        this.item_layout = item_layout;
        this.list_loaimonan = loaiMonAnDTOList;
    }

    @Override
    public int getCount() {
        return list_loaimonan.size();
    }

    @Override
    public Object getItem(int position) {
        return list_loaimonan.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list_loaimonan.get(position).getMaLoai();
    }


    private static  class viewHolder{
        TextView txtTenloai;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder holder= new viewHolder();
        if(view==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= inflater.inflate(item_layout,null);
            holder.txtTenloai=view.findViewById(R.id.txtTenloaimonan);
            view.setTag(holder);
        }
        else {
            holder= (viewHolder) view.getTag();
        }
        LoaimonanDTO p= list_loaimonan.get(i);
        holder.txtTenloai.setText(p.getTenLoai());
        return view;
    }
}
