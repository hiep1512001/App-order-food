package com.nguyenhoanghiep.orderfood.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.nguyenhoanghiep.orderfood.DTO.BanDTO;
import com.nguyenhoanghiep.orderfood.DTO.MonanDTO;
import com.nguyenhoanghiep.orderfood.R;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterHienthibanan extends BaseAdapter {
    Activity context;
    int item_layout;
    List<BanDTO> list_banan;

    public AdapterHienthibanan(Activity context, int item_layout, List<BanDTO> list_banan) {
        this.context = context;
        this.item_layout = item_layout;
        this.list_banan = list_banan;
    }

    @Override
    public int getCount() {
        return list_banan.size();
    }

    @Override
    public Object getItem(int i) {
        return list_banan.get(i);
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
            holder.txtTenbanan=view.findViewById(R.id.txtTenban);
            view.setTag(holder);
        }
        else {
            holder= (viewHolder) view.getTag();
        }
        BanDTO p= list_banan.get(i);
        holder.txtTenbanan.setText(p.getTenBan());
        String trangthai=p.getDuocChon();
        if(trangthai.equals("true")){
            holder.txtTenbanan.setTextColor(Color.RED);
        }
        else {
            holder.txtTenbanan.setTextColor(Color.WHITE);
        }
        return view;
    }
    private static  class viewHolder{
        TextView txtTenbanan;
    }
}
