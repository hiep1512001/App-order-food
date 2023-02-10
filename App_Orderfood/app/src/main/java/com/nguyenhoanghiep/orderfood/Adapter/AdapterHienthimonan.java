package com.nguyenhoanghiep.orderfood.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nguyenhoanghiep.orderfood.DTO.BanDTO;
import com.nguyenhoanghiep.orderfood.DTO.MonanDTO;
import com.nguyenhoanghiep.orderfood.R;

import java.util.List;

public class AdapterHienthimonan extends BaseAdapter {
    Activity context;
    int item_layout;
    List<MonanDTO>list_Monan;

    public AdapterHienthimonan(Activity context, int item_layout, List<MonanDTO> list_Monan) {
        this.context = context;
        this.item_layout = item_layout;
        this.list_Monan = list_Monan;
    }

    @Override
    public int getCount() {
        return list_Monan.size();
    }

    @Override
    public Object getItem(int i) {
        return list_Monan.get(i);
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
            holder.txtTenmonan=view.findViewById(R.id.txtTenmonan);
            holder.txtGiatien=view.findViewById(R.id.txtGiamonan);
            holder.imvAnh=view.findViewById(R.id.imvAnhmonan);
            view.setTag(holder);
        }
        else {
            holder= (viewHolder) view.getTag();
        }
        MonanDTO p= list_Monan.get(i);
        holder.txtTenmonan.setText(p.getTenMonAn());
        holder.txtGiatien.setText(p.getGiaTien());
        String hinhanh = p.getHinhAnh();

        if(hinhanh == null || hinhanh.equals(""))
            holder.imvAnh.setImageResource(R.drawable.anh_nen_load);
        else{
            Uri uri = Uri.parse(hinhanh);
            holder.imvAnh.setImageURI(uri);
        }
        return view;
    }
    private static class viewHolder{
        ImageView imvAnh;
        TextView txtTenmonan;
        TextView txtGiatien;
    }
}
