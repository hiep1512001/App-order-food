package com.nguyenhoanghiep.orderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.nguyenhoanghiep.orderfood.DAO.NhanvienDAO;
import com.nguyenhoanghiep.orderfood.DAO.QuyenDAO;
import com.nguyenhoanghiep.orderfood.DTO.NhanvienDTO;
import com.nguyenhoanghiep.orderfood.Database.CreateDatabase;

public class Khoitao extends AppCompatActivity {
    private SharedPreferences mMoLanDau;
    private SharedPreferences.Editor editor;
    private QuyenDAO quyenDAO;
    private NhanvienDAO nhanVienDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoitao);
        @SuppressLint("CommitPrefEdits") Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                Log.d("kiemtra", e.getMessage());
            } finally {
                mMoLanDau = getSharedPreferences("SPR_MOLANDAU", 0);
                if (mMoLanDau != null) {
                    boolean firstOpen = mMoLanDau.getBoolean("MOLANDAU", true);
                    if (firstOpen){
                        CreateDatabase createDatabase = new CreateDatabase(this);
                        createDatabase.open();
                        quyenDAO = new QuyenDAO(this);
                        quyenDAO.ThemQuyen("Quản lý");
                        quyenDAO.ThemQuyen("Nhân viên");
                        nhanVienDAO = new NhanvienDAO(this);
                        NhanvienDTO nhanVienDTO = new NhanvienDTO();
                        nhanVienDTO.setTENDANGNHAP("admin");
                        nhanVienDTO.setCMND(111111111);
                        nhanVienDTO.setGIOITINH("Nam");
                        nhanVienDTO.setMATKHAU("admin");
                        nhanVienDTO.setNGAYSINH("01/01/1997");
                        nhanVienDTO.setMAQUYEN(1);
                        nhanVienDAO.ThemNV(nhanVienDTO);
                        editor = mMoLanDau.edit();
                        editor.putBoolean("MOLANDAU", false);
                        editor.apply();
                    }
                    Intent iDangNhap = new Intent(this, DangNhapActivity.class);
                    startActivity(iDangNhap);
                    finish();
                }
            }
        });
        thread.start();
    }
}