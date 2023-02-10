package com.nguyenhoanghiep.orderfood.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nguyenhoanghiep.orderfood.DTO.BanDTO;
import com.nguyenhoanghiep.orderfood.DTO.ThanhtoanDTO;
import com.nguyenhoanghiep.orderfood.R;

import java.util.List;

public class AdapterHienthithanhtoan extends BaseAdapter {
    Activity context;
    int item_layout;
    List<ThanhtoanDTO>list_thanhtoan;

    public AdapterHienthithanhtoan(Activity context, int item_layout, List<ThanhtoanDTO> list_thanhtoan) {
        this.context = context;
        this.item_layout = item_layout;
        this.list_thanhtoan = list_thanhtoan;
    }

    @Override
    public int getCount() {
        return list_thanhtoan.size();
    }

    @Override
    public Object getItem(int i) {
        return list_thanhtoan.get(i);
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
            holder.txtTenmonan=view.findViewById(R.id.txtTenMonAnThanToan);
            holder.txtGiatien=view.findViewById(R.id.txtGiaTienThanhToan);
            holder.txtSoluong=view.findViewById(R.id.txtSoLuongThanhToan);
            view.setTag(holder);
        }
        else {
            holder= (viewHolder) view.getTag();
        }
            ThanhtoanDTO p= list_thanhtoan.get(i);
        holder.txtTenmonan.setText(p.getTenMonAn());
        holder.txtGiatien.setText(String.valueOf(p.getGiatien()));
        holder.txtSoluong.setText(String.valueOf(p.getSoLuong()));
        return view;
    }
    private static class viewHolder{
        TextView txtTenmonan, txtSoluong, txtGiatien;
    }
}
