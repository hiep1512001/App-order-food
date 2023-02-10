package com.nguyenhoanghiep.orderfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class Trangchu extends AppCompatActivity {
    Button btnDangxuat;
ImageButton imbBanan, imbNhanvien, imbThucdon,imbThongke;
TextView txtTendangnhap,txtNhanvien,txtThongke;
private int maquyen=1;
private int manhanvien=0;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu);
        linkView();
        getData();
        addEvent();
    }

    private void getData() {
        SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);
        if(maquyen==2){
            imbNhanvien.setVisibility(View.INVISIBLE);
            txtNhanvien.setVisibility(View.INVISIBLE);
            txtThongke.setVisibility(View.INVISIBLE);
            imbThongke.setVisibility(View.INVISIBLE);
        }
        Intent intent= getIntent();
        manhanvien=intent.getIntExtra("manhanvien",0);
        String tendangnhap= intent.getStringExtra("tendn");
        txtTendangnhap.setText(tendangnhap);
    }

    private void addEvent() {
        imbThucdon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iLoaimonan = new Intent(Trangchu.this, Hienthiloaimonan.class); // chuyển đổi 2 activity
/*                iTrangChu.putExtra("tendn", edtTendangnhap.getText().toString());
                iTrangChu.putExtra("manhanvien", kiemtra);*/
                startActivity(iLoaimonan);
            }
        });
        imbBanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iBanan = new Intent(Trangchu.this, Hienthibanan.class); // chuyển đổi 2 activity
/*                iTrangChu.putExtra("tendn", edtTendangnhap.getText().toString());
                iTrangChu.putExtra("manhanvien", kiemtra);*/
                startActivity(iBanan);
            }
        });
        btnDangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent= new Intent(Trangchu.this, DangNhapActivity.class);
                startActivity(intent);
                finish();

            }
        });
        imbNhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iNhanvien = new Intent(Trangchu.this, Hienthinhanvien.class); // chuyển đổi 2 activity
/*                iTrangChu.putExtra("tendn", edtTendangnhap.getText().toString());
                iTrangChu.putExtra("manhanvien", kiemtra);*/
                startActivity(iNhanvien);
            }
        });
        imbThongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent=new Intent(Trangchu.this,Thongke.class);
            startActivity(intent);
            }
        });
    }

    private void linkView() {
        txtThongke=findViewById(R.id.txtThongke);
        imbThongke=findViewById(R.id.imbThongke);
        btnDangxuat=findViewById(R.id.btnDangxuat);
        imbBanan=findViewById(R.id.imbBanan);
        imbThucdon=findViewById(R.id.imbThucdon);
        imbNhanvien=findViewById(R.id.imbNhanvien);
        txtTendangnhap=findViewById(R.id.txtTendangnhap);
        txtNhanvien=findViewById(R.id.txtNhanvien);
    }
}