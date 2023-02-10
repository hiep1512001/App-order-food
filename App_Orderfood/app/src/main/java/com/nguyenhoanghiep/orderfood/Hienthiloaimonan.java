package com.nguyenhoanghiep.orderfood;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nguyenhoanghiep.orderfood.Adapter.AdaptertHienthiloaimonan;
import com.nguyenhoanghiep.orderfood.DAO.LoaimonanDAO;
import com.nguyenhoanghiep.orderfood.DTO.BanDTO;
import com.nguyenhoanghiep.orderfood.DTO.LoaimonanDTO;
import com.nguyenhoanghiep.orderfood.DTO.MonanDTO;

import java.util.ArrayList;
import java.util.List;

public class Hienthiloaimonan extends AppCompatActivity {
    ImageButton imbThemloaimonan, imbThoathienthiloaimonan;
    EditText edtTenloai;
    GridView gvHienthiloaimonan;
    Button btnDongy, btnThoat;
    AdaptertHienthiloaimonan adapter;
    List<LoaimonanDTO> list_loaimonan,list_loaimonansosanh;
    LoaimonanDAO loaiMonAnDAO;
    private int maloaimon;
    private int maquyen=1;
    int maban=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hienthiloaimonan);
        loaiMonAnDAO= new LoaimonanDAO(Hienthiloaimonan.this);
        linkView();
        getData();
        loadData();
        addEvent();
    }

    private void getData() {
        SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);
        if(maquyen==2){
            imbThemloaimonan.setVisibility(View.INVISIBLE);
        }
        maban=getIntent().getIntExtra("maban",0);
    }
    public  int Kiemtra(){
        int t=1;
        list_loaimonansosanh=loaiMonAnDAO.LayDanhSachLoaiMonAn();
        for(int i=0; i<list_loaimonansosanh.size();i++){
            LoaimonanDTO p= list_loaimonansosanh.get(i);
            if(p.getTenLoai().equals(edtTenloai.getText().toString())){
                t=0;
                break;
            }
        }
        return t;
    }
    private void addEvent() {
        imbThemloaimonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog= new Dialog(Hienthiloaimonan.this);
                dialog.setContentView(R.layout.custom_dialogthamloaimonan);
                dialog.setCanceledOnTouchOutside(false);
                btnDongy=dialog.findViewById(R.id.btnDongy);
                edtTenloai= dialog.findViewById(R.id.edtTenloaimonan);
                btnDongy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!edtTenloai.getText().toString().equals("")&&Kiemtra()==1){
                            loaiMonAnDAO.ThemLoaiMonAn(edtTenloai.getText().toString());
                            Toast.makeText(Hienthiloaimonan.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            loadData();
                        }
                        else {
                            Toast.makeText(Hienthiloaimonan.this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
                btnThoat=dialog.findViewById(R.id.btnThoat);
                btnThoat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        gvHienthiloaimonan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(maquyen!=2){
                    LoaimonanDTO selectloaimonan= (LoaimonanDTO) adapter.getItem(i);
                    AlertDialog.Builder builder= new AlertDialog.Builder(Hienthiloaimonan.this);
                    builder.setTitle("Xóa loại món ăn");
                    builder.setMessage("Bạn muốn xóa loại món ăn?");
                    builder.setIcon(android.R.drawable.ic_delete);
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            boolean kiemtra = loaiMonAnDAO.Xoaloaimonan(selectloaimonan.getMaLoai());
                            if(kiemtra){
                                loadData();
                                Toast.makeText(Hienthiloaimonan.this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                            }
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
                return true;
            }
        });
        imbThoathienthiloaimonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        gvHienthiloaimonan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(Hienthiloaimonan.this,Hienthimonan.class);
                LoaimonanDTO p= (LoaimonanDTO) adapter.getItem(i);
                intent.putExtra("maloaimonan",p.getMaLoai());
                intent.putExtra("maban",maban);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        list_loaimonan= new ArrayList<>();
        list_loaimonan.clear();
        list_loaimonan = loaiMonAnDAO.LayDanhSachLoaiMonAn();
        adapter = new AdaptertHienthiloaimonan(Hienthiloaimonan.this, R.layout.custom_hienthiloaimonan,list_loaimonan);
        gvHienthiloaimonan.setAdapter(adapter);
    }
    private void linkView() {
        gvHienthiloaimonan=findViewById(R.id.gvHienthiloaimonan);
        imbThoathienthiloaimonan=findViewById(R.id.imbLoaithucanBack);
        imbThemloaimonan=findViewById(R.id.imbThemloaimonan);
/*        gvHienthiloaimonan= findViewById(R.id.gvHienThiloaiThucDon);*/
    }
}