package com.nguyenhoanghiep.orderfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nguyenhoanghiep.orderfood.DAO.NhanvienDAO;

public class DangNhapActivity extends AppCompatActivity {
    Button btnDangnhap, btnDangky;
    EditText edtTendangnhap, edtMatkhau;
    private NhanvienDAO nhanVienDAO;
    private final int STORAGE_PERMISSION_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_nhap);
        linkView();
        nhanVienDAO = new NhanvienDAO(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            requestStoragePermission();// yêu cầu cấp quyền
        addEvent();
    }
    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Ứng dụng cần được cấp quyền")
                    .setMessage("Ứng dụng cần được cấp quyền truy cập bộ nhớ để có thể sử dụng ứng dụng tốt hơn!")
                    .setPositiveButton("Ok", (dialog, which) -> ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE))
                    .setNegativeButton("Hủy", (dialog, which) -> System.exit(0))
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Đã được cấp quyền!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ứng dụng bị từ chối cấp quyền!", Toast.LENGTH_SHORT).show();
                requestStoragePermission();
            }
        }
    }
    private void addEvent() {
        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(DangNhapActivity.this,Dangky.class);
                startActivity(intent);
            }
        });
        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dangnhap();
            }
        });
    }

    private void linkView() {
        btnDangky= findViewById(R.id.btnDangky);
        btnDangnhap= findViewById(R.id.btnDangnhap);
        edtTendangnhap= findViewById(R.id.edtTendangnhap);
        edtMatkhau=findViewById(R.id.edtMatkhau);
    }
    public void Dangnhap(){
        String sTenDangNhap = edtTendangnhap.getText().toString();
        String sMatKhau = edtMatkhau.getText().toString();
        int kiemtra = nhanVienDAO.KiemTraDangNhap(sTenDangNhap, sMatKhau);
        int maquyen = nhanVienDAO.LayQuyenNhanVien(kiemtra);
        if (kiemtra != 0){
            SharedPreferences sharedPreferences = getSharedPreferences("luuquyen",MODE_PRIVATE); // chỉ có ứng dụng này đc dùng
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("maquyen", maquyen);
            editor.apply();
            Intent iTrangChu = new Intent(DangNhapActivity.this, Trangchu.class); // chuyển đổi 2 activity
            iTrangChu.putExtra("tendn", edtTendangnhap.getText().toString());
            iTrangChu.putExtra("manhanvien", kiemtra);
            startActivity(iTrangChu);
            finish();
/*            overridePendingTransition(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);*/
        } else Toast.makeText(DangNhapActivity.this, "Đăng nhập thất bại!!", Toast.LENGTH_SHORT).show();
    }
}