package com.nguyenhoanghiep.orderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.nguyenhoanghiep.orderfood.Adapter.AdapterHienthithanhtoan;
import com.nguyenhoanghiep.orderfood.DAO.BanDAO;
import com.nguyenhoanghiep.orderfood.DAO.GoimonDAO;
import com.nguyenhoanghiep.orderfood.DTO.ChitietgoimonDTO;
import com.nguyenhoanghiep.orderfood.DTO.ThanhtoanDTO;

import java.util.List;

public class Thanhtoan extends AppCompatActivity {
GridView gvThanhtoan;
Button btnThanhtoan, btnThoat;
TextView txtTongtien;
    long tongtien = 0;
    int maban=0;
    GoimonDAO goimonDAO;
    AdapterHienthithanhtoan adapter;
    ChitietgoimonDTO chitietgoimonDTO;
    List<ThanhtoanDTO> list_thanhtoan;
    BanDAO banDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);
        banDAO= new BanDAO(Thanhtoan.this);
        goimonDAO= new GoimonDAO(Thanhtoan.this);
        linkView();
        getData();
        loadData();
        addEvent();
    }

    private void addEvent() {
        btnThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean kiemrabanan = banDAO.CapNhatTinhTrangBan(maban, "false");
                boolean kiemtragoimom = goimonDAO.CapNhatTrangThaiGoiMonTheoMaBan(maban, "true");
                if(kiemrabanan && kiemtragoimom){
                    Toast.makeText(Thanhtoan.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                    txtTongtien.setText("Tổng tiền: ");
                    loadData();
                }else
                    Toast.makeText(Thanhtoan.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadData() {
        int magoimon = (int) goimonDAO.LayMaGoiMonTheoMaBan(maban, "false");
        list_thanhtoan = goimonDAO.LayDanhSachMonAnTheoMaGoiMon(magoimon);
        adapter= new AdapterHienthithanhtoan(Thanhtoan.this,R.layout.custom_hienthithanhtoan,list_thanhtoan);
        gvThanhtoan.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getData() {
        maban = getIntent().getIntExtra("mabanthanhtoan", 0);
        if (maban != 0){
            int magoimon = (int) goimonDAO.LayMaGoiMonTheoMaBan(maban, "false");
            list_thanhtoan = goimonDAO.LayDanhSachMonAnTheoMaGoiMon(magoimon);
            for (int i = 0; i < list_thanhtoan.size(); i++){
                int soluong = list_thanhtoan.get(i).getSoLuong();
                int giatien = list_thanhtoan.get(i).getGiatien();
                tongtien += (soluong * giatien);
            }
            txtTongtien.setText( "Tổng cộng: " + tongtien);
        }
    }

    private void linkView() {
        btnThanhtoan=findViewById(R.id.btnThanhToan);
        btnThoat=findViewById(R.id.btnThoatThanhToan);
        txtTongtien=findViewById(R.id.txtTongTien);
        gvThanhtoan=findViewById(R.id.gvThanhToan);
    }
}