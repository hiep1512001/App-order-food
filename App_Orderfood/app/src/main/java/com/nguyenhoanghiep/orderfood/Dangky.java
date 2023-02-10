package com.nguyenhoanghiep.orderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nguyenhoanghiep.orderfood.DAO.NhanvienDAO;
import com.nguyenhoanghiep.orderfood.DAO.QuyenDAO;
import com.nguyenhoanghiep.orderfood.DTO.NhanvienDTO;
import com.nguyenhoanghiep.orderfood.DTO.QuyenDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Dangky extends AppCompatActivity {

    private QuyenDAO quyenDAO;
    private List<QuyenDTO> quyenDTOList;
    private List<String> dataAdapter;
    private EditText edTenDangNhap, edMatKhau, edNgaySinh, edCMND;
    private Button btnDongY, btnThoat;
    private TextView txtTieuDeDangKy;
    private RadioGroup rgGioiTinh;
    private RadioButton rdNam, rdNu;
    private Spinner spinQuyen;

    private String sGioiTinh;

    private NhanvienDAO nhanVienDAO;
List<NhanvienDTO>list_nhanvien;
    private int manhanvien = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danng_ky);
        nhanVienDAO = new NhanvienDAO(this);
        quyenDAO = new QuyenDAO(this);
        manhanvien = getIntent().getIntExtra("manhanvien", 0);

        linkVIew();
        loadView();
        loadDataSpinner();
        addEvent();
    }
    int Kiemtra(){
        list_nhanvien=nhanVienDAO.LayDanhSachNhanVien();
        int t=1;
        for(int i=0; i<list_nhanvien.size();i++){
            NhanvienDTO p= list_nhanvien.get(i);
                if(p.getTENDANGNHAP().equals(edTenDangNhap.getText().toString())){
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
        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edTenDangNhap.getText().toString()!="" && edMatKhau.getText().toString()!="" && !edCMND.getText().toString().equals("") && edNgaySinh.getText().toString()!=""){
                    if (manhanvien != 0)
                        //Thực hiện code sửa nhân viên
                        SuaNhanVien();
                    else{
                        if(Kiemtra()==1){
                            //Thực hiện thêm mới nhân viên
                            DongYThemNhanVien();
                        }
                        else {
                            Toast.makeText(Dangky.this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                else{
                    Toast.makeText(Dangky.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }
        });
        edNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseDay();
            }
        });
    }
    void loadView(){
        if (manhanvien != 0){
            txtTieuDeDangKy.setText("Cập nhật nhân viên");
            NhanvienDTO nhanVienDTO = nhanVienDAO.LayDanhSachNhanVienTheoMa(manhanvien);
            edTenDangNhap.setText(nhanVienDTO.getTENDANGNHAP());
            edMatKhau.setText(nhanVienDTO.getMATKHAU());
            edCMND.setText(String.valueOf(nhanVienDTO.getCMND()));
            edNgaySinh.setText(nhanVienDTO.getNGAYSINH());
            String gioitinh = nhanVienDTO.getGIOITINH();
            if (gioitinh.equals("Nam")) rdNam.setChecked(true);
            else rdNu.setChecked(true);
        }
    }
    private void linkVIew() {
        spinQuyen= findViewById(R.id.spnQuyen);
        edTenDangNhap=findViewById(R.id.edtTendangnhap);
        edMatKhau=findViewById(R.id.edtMatkhau);
        edCMND=findViewById(R.id.edtCmnd);
        edNgaySinh=findViewById(R.id.edtNgaysinh);
        spinQuyen=findViewById(R.id.spnQuyen);
        btnDongY=findViewById(R.id.btnDongy);
        btnThoat=findViewById(R.id.btnThoat);
        rgGioiTinh=findViewById(R.id.rgGioiTinhDK);
        rdNam=findViewById(R.id.rdNam);
        rdNu=findViewById(R.id.rdNu);
        txtTieuDeDangKy=findViewById(R.id.txtTieuDeDangKy);
    }
    private void loadDataSpinner() {
        quyenDTOList = quyenDAO.LayDanhSachQuyen();
        dataAdapter = new ArrayList<>();

        for (int i = 0; i < quyenDTOList.size(); i ++){
            String tenquyen = quyenDTOList.get(i).getTenQuyen();
            dataAdapter.add(tenquyen);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataAdapter);
        spinQuyen.setAdapter(adapter);
    }
    private void DongYThemNhanVien(){
        String sTenDangNhap = edTenDangNhap.getText().toString();
        String sMatKhau = edMatKhau.getText().toString();

        switch (rgGioiTinh.getCheckedRadioButtonId()){
            case R.id.rdNam:
                sGioiTinh="Nam";
                break;
            case R.id.rdNu:
                sGioiTinh="Nữ";
                break;
        }

        String sNgaySinh = edNgaySinh.getText().toString();
        long sCMND = Long.parseLong(edCMND.getText().toString());
        if(sTenDangNhap == null || sTenDangNhap.equals(""))
            Toast.makeText(Dangky.this,"Vui lòng nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
        else if(sMatKhau==null || sMatKhau.equals(""))
            Toast.makeText(Dangky.this,"Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
        else {
            NhanvienDTO nhanVienDTO = new NhanvienDTO();
            nhanVienDTO.setTENDANGNHAP(sTenDangNhap);
            nhanVienDTO.setMATKHAU(sMatKhau);
            nhanVienDTO.setCMND(sCMND);
            nhanVienDTO.setNGAYSINH(sNgaySinh);
            nhanVienDTO.setGIOITINH(sGioiTinh);

            int vitri = spinQuyen.getSelectedItemPosition();
            int maquyen = quyenDTOList.get(vitri).getMaQuyen();
            nhanVienDTO.setMAQUYEN(maquyen);

            boolean kiemtra = nhanVienDAO.ThemNV(nhanVienDTO);
            if(kiemtra) Toast.makeText(Dangky.this,"Thêm thành công", Toast.LENGTH_SHORT).show();
            else Toast.makeText(Dangky.this,"Thêm thất bại", Toast.LENGTH_SHORT).show();
        }
    }
    private void SuaNhanVien() {
        String sTenDangNhap = edTenDangNhap.getText().toString();
        String sMatKhau = edMatKhau.getText().toString();
        String sNgaySinh = edNgaySinh.getText().toString();
        long sCMND = Long.parseLong(edCMND.getText().toString());
        switch (rgGioiTinh.getCheckedRadioButtonId()) {
            case R.id.rdNam:
                sGioiTinh = "Nam";
                break;
            case R.id.rdNu:
                sGioiTinh = "Nữ";
                break;
        }

        NhanvienDTO nhanVienDTO = new NhanvienDTO();
        nhanVienDTO.setMANV(manhanvien);
        nhanVienDTO.setTENDANGNHAP(sTenDangNhap);
        nhanVienDTO.setMATKHAU(sMatKhau);
        nhanVienDTO.setCMND(sCMND);
        nhanVienDTO.setNGAYSINH(sNgaySinh);
        nhanVienDTO.setGIOITINH(sGioiTinh);

        boolean kiemtra = nhanVienDAO.SuaNV(nhanVienDTO);
        if (kiemtra)
            Toast.makeText(Dangky.this,"Sửa thành công", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(Dangky.this,"Lỗi", Toast.LENGTH_SHORT).show();
    }
    public void ChooseDay(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            if (!edNgaySinh.getText().toString().equals(""))
                cal.setTime(sdf.parse(edNgaySinh.getText().toString()));
            else
                cal.getTime();

            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, (datePicker, yearSelected, monthSelected, daySelected) -> {
                monthSelected = monthSelected + 1;
                Date date = StringToDate(daySelected + "/" + monthSelected + "/" + yearSelected, "dd/MM/yyyy");
                edNgaySinh.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
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