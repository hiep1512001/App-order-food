package com.nguyenhoanghiep.orderfood;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.nguyenhoanghiep.orderfood.Adapter.AdapterHienthinhanvien;
import com.nguyenhoanghiep.orderfood.DAO.NhanvienDAO;
import com.nguyenhoanghiep.orderfood.DTO.LoaimonanDTO;
import com.nguyenhoanghiep.orderfood.DTO.NhanvienDTO;

import java.util.ArrayList;
import java.util.List;

public class Hienthinhanvien extends AppCompatActivity {
ImageButton imbThemnhanven,imbThoathienthinhanvien;
ListView lvHienthinhanvien;
AdapterHienthinhanvien adapter;
NhanvienDAO nhanvienDAO;
List<NhanvienDTO> list_nhanvien;
int maquyen=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hienthinhanvien);
        nhanvienDAO= new NhanvienDAO(Hienthinhanvien.this);
        linkView();
        getData();
        loadData();
        addEvent();
    }

    private void getData() {
        SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);
        if(maquyen==2){
            imbThemnhanven.setVisibility(View.INVISIBLE);
        }
    }

    private void loadData() {
        list_nhanvien= new ArrayList<>();
        list_nhanvien.clear();
        list_nhanvien=nhanvienDAO.LayDanhSachNhanVien();
        adapter= new AdapterHienthinhanvien(Hienthinhanvien.this,R.layout.cutom_hienthinhanvien,list_nhanvien);
        lvHienthinhanvien.setAdapter(adapter);
    }

    private void addEvent() {
        imbThemnhanven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Hienthinhanvien.this, Dangky.class);
                startActivity(intent);
            }
        });
        imbThoathienthinhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        loadData();
        super.onResume();
    }

    private void linkView() {
        imbThemnhanven= findViewById(R.id.imbThemnhanvien);
        imbThoathienthinhanvien=findViewById(R.id.imbThoathienthinhanvien);
        lvHienthinhanvien=findViewById(R.id.lvHienthinhanvien);

    }
    public void Xoanhanvien(int manv){
        AlertDialog.Builder builder= new AlertDialog.Builder(Hienthinhanvien.this);
        builder.setTitle("Xóa nhân viên");
        builder.setMessage("Bạn muốn xóa nhân viên ?");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                nhanvienDAO.XoaNhanVien(manv);
                Toast.makeText(Hienthinhanvien.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                loadData();
            }
        });
        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }
    public void Suanhanvien(int manv){
        Intent intent= new Intent(Hienthinhanvien.this, Dangky.class);
        intent.putExtra("manhanvien",manv);
        startActivity(intent);
    }
}