package com.nguyenhoanghiep.orderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.nguyenhoanghiep.orderfood.DAO.MonanDAO;
import com.nguyenhoanghiep.orderfood.DTO.MonanDTO;

import java.util.List;

public class Themmonan extends AppCompatActivity {
    public static int REQUEST_CODE_THEMLOAITHUCDON = 113;
    public static int REQUEST_CODE_MOHINH = 123;
Button btnThoat, btnDongy;
EditText edtTenmonan, edtGiatien;
ImageView imvHinhanh;
ImageButton imbThoatthemmonan;
MonanDAO monanDAO;
List<MonanDTO> list_monansosanh;
String sDuongdanhinh;
private  int maquyen=0;
private  int maloaimon=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themmonan);
        monanDAO= new MonanDAO(Themmonan.this);
        linkView();
        getData();
        addEvent();
    }
    public  int Kiemtra(){
        int t=1;
        list_monansosanh= monanDAO.LayDanhSachMonAnTheoLoai(maloaimon);
        for(int i=0; i<list_monansosanh.size(); i++){
            MonanDTO p =list_monansosanh.get(i);
            if(p.getTenMonAn().equals(edtTenmonan.getText().toString())){
                t=0;
                break;
            }
        }
        return t;
    }
    private void addEvent() {
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imbThoatthemmonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnDongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    int maloai = maloaimon;
                    String tenmonan = edtTenmonan.getText().toString();
                    String giatien = edtGiatien.getText().toString();
                    if (tenmonan != null && giatien != null && !tenmonan.equals("") && !giatien.equals("")){
                        if(Kiemtra()==1){
                            MonanDTO monAnDTO = new MonanDTO();
                            monAnDTO.setGiaTien(giatien);
                            monAnDTO.setHinhAnh(sDuongdanhinh);
                            monAnDTO.setMaLoai(maloai);
                            monAnDTO.setTenMonAn(tenmonan);
                            boolean kiemtra = monanDAO.ThemMonAn(monAnDTO);
                            if (kiemtra)
                                Toast.makeText(Themmonan.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(Themmonan.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(Themmonan.this, "Món ăn đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }else
                        Toast.makeText(Themmonan.this,"Lỗi thêm món ăn", Toast.LENGTH_SHORT).show();
                }
        });

        imvHinhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iMoHinh = new Intent();
                iMoHinh.setType("image/*");
                iMoHinh.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(iMoHinh,"Chọn hình thực đơn"), REQUEST_CODE_MOHINH);
            }
        });
    }

    private void getData() {
        SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);
        maloaimon=getIntent().getIntExtra("maloaimonan",0);
    }

    private void linkView() {
        imbThoatthemmonan= findViewById(R.id.imbThoatthemmonan);
        imvHinhanh=findViewById(R.id.imvHinhanhmonan);
        edtTenmonan=findViewById(R.id.edtTenmonan);
        edtGiatien=findViewById(R.id.edtGiatien);
        btnDongy=findViewById(R.id.btnDongy);
        btnThoat=findViewById(R.id.btnThoat);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_THEMLOAITHUCDON){
            if (resultCode == Activity.RESULT_OK ){
                Intent dulieu = data;
                boolean kiemtra = dulieu.getBooleanExtra("kiemtraloaithucdon", false);
                if (kiemtra){
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this,"Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        }else if (REQUEST_CODE_MOHINH == requestCode){
            if (resultCode == Activity.RESULT_OK && data != null){
                sDuongdanhinh = data.getData().toString();
                imvHinhanh.setImageURI(data.getData());
            }
        }
    }
}