package com.nguyenhoanghiep.orderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.nguyenhoanghiep.orderfood.Adapter.AdapterHienthithanhtoan;
import com.nguyenhoanghiep.orderfood.DAO.BanDAO;
import com.nguyenhoanghiep.orderfood.DAO.GoimonDAO;
import com.nguyenhoanghiep.orderfood.DTO.ChitietgoimonDTO;
import com.nguyenhoanghiep.orderfood.DTO.ThanhtoanDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Thongke extends AppCompatActivity {
    GridView gvThongke;
    Button btnThoat,btnThongke;
    EditText edtNgay;
    TextView txtTongtien;
    long tongtien = 0;
    int maban=0;
    GoimonDAO goimonDAO;
    AdapterHienthithanhtoan adapter;
    ChitietgoimonDTO chitietgoimonDTO;
    List<ThanhtoanDTO> list_thanhtoan;
    List<ThanhtoanDTO> list_thongke;
    List<Integer>list_mamontheongay;
    String ngay="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke);
        goimonDAO= new GoimonDAO(Thongke.this);
        linkView();

        addEvent();
    }

    private void getData() {
            for (int i = 0; i < list_thongke.size(); i++){
                int soluong = list_thongke.get(i).getSoLuong();
                int giatien = list_thongke.get(i).getGiatien();
                tongtien += (soluong * giatien);
            }
            txtTongtien.setText( "Tổng cộng: " + tongtien);
    }

    private void loadData() {
        int magoimon = (int) goimonDAO.LayMaGoiMonTheoMaBan(maban, "false");
        list_thanhtoan= new ArrayList<>();
        list_thanhtoan.clear();
        list_thongke= new ArrayList<>();
        list_thongke.clear();
        for(int i=0; i<list_mamontheongay.size(); i++){
            List<ThanhtoanDTO> list = goimonDAO.LayDanhSachMonAnTheoMaGoiMon(list_mamontheongay.get(i));
            list_thanhtoan.addAll(list);
        }
        List<Integer>list_kiemtra=new ArrayList<>();
        for (int i=0; i<list_thanhtoan.size();i++){
            list_kiemtra.add(1);
        }
        for(int i=0; i<list_thanhtoan.size()-1; i++){
            if(list_kiemtra.get(i)==1){
                ThanhtoanDTO a= list_thanhtoan.get(i);
                for (int j=i+1; j<list_thanhtoan.size();j++){
                    ThanhtoanDTO b =list_thanhtoan.get(j);
                    if (a.getTenMonAn().equals(b.getTenMonAn()))
                    {
                        int soluong=a.getSoLuong()+b.getSoLuong();
                        a.setSoLuong(soluong);
                        list_kiemtra.set(j,0);
                    }
                }
                list_thongke.add(a);
            }
        }
        adapter= new AdapterHienthithanhtoan(Thongke.this,R.layout.custom_hienthithanhtoan,list_thongke);
        gvThongke.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        getData();
    }

    private void addEvent() {
        edtNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseDay();
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnThongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNgay.getText().toString()!=""){
                    list_mamontheongay= new ArrayList<>();
                    list_mamontheongay.clear();
                    ngay=edtNgay.getText().toString();
                    list_mamontheongay=goimonDAO.Laymagoimontheongay(ngay);
                    loadData();
                }
            }
        });
    }

    private void linkView() {
        btnThoat=findViewById(R.id.btnThoatThanhToan);
        txtTongtien=findViewById(R.id.txtTongTien);
        gvThongke=findViewById(R.id.gvThongke);
        edtNgay=findViewById(R.id.edtNgay);
        btnThongke=findViewById(R.id.btnThongke);
    }
    public void ChooseDay(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            if (!edtNgay.getText().toString().equals(""))
                cal.setTime(sdf.parse(edtNgay.getText().toString()));
            else
                cal.getTime();

            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, (datePicker, yearSelected, monthSelected, daySelected) -> {
                monthSelected = monthSelected + 1;
                Date date = StringToDate(daySelected + "/" + monthSelected + "/" + yearSelected, "dd/MM/yyyy");
                edtNgay.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
            }, year, month, day);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setTitle("Chọn ngày sinh");

            dialog.show();
        } catch (ParseException e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public Date StringToDate(String dob, String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(dob);
        } catch (ParseException e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}