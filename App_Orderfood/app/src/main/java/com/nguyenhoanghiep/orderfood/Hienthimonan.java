package com.nguyenhoanghiep.orderfood;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nguyenhoanghiep.orderfood.Adapter.AdapterHienthimonan;
import com.nguyenhoanghiep.orderfood.DAO.BanDAO;
import com.nguyenhoanghiep.orderfood.DAO.GoimonDAO;
import com.nguyenhoanghiep.orderfood.DAO.MonanDAO;
import com.nguyenhoanghiep.orderfood.DTO.ChitietgoimonDTO;
import com.nguyenhoanghiep.orderfood.DTO.GoimonDTO;
import com.nguyenhoanghiep.orderfood.DTO.MonanDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Hienthimonan extends AppCompatActivity {
    ListView lvHienthimonan;
    Button btnDongy, btnThoat;
    ImageButton imbThemmonan,imbThoahienthimonan;
    TextView txtTenmonangoi;
    MonanDAO monAnDAO;
    GoimonDAO goimonDAO;
    EditText edtSoluong;
    BanDAO banDAO;
    AdapterHienthimonan adapter;
    List<MonanDTO> list_monan;
    private int maquyen=1;
    private  int maloaimon=0;
    int maban=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hienthimonan);
        monAnDAO = new MonanDAO(this);
        banDAO= new BanDAO(Hienthimonan.this);
        goimonDAO= new GoimonDAO(Hienthimonan.this);
        linkView();

        getData();
        loadData();
        addEvent();
    }

    private void loadData() {
        list_monan= new ArrayList<>();
        list_monan.clear();
        if(maloaimon!=0){
            list_monan=monAnDAO.LayDanhSachMonAnTheoLoai(maloaimon);
            adapter= new AdapterHienthimonan(Hienthimonan.this,R.layout.custom_hienthimonan,list_monan);
            lvHienthimonan.setAdapter(adapter);
        }
    }

    private void getData() {
        SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);
        if(maquyen==2){
            imbThemmonan.setVisibility(View.INVISIBLE);
        }
        maloaimon = getIntent().getIntExtra("maloaimonan", 0);
        maban=getIntent().getIntExtra("maban",0);
    }

    private void addEvent() {
        imbThemmonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Hienthimonan.this, Themmonan.class);
                intent.putExtra("maloaimonan",maloaimon);
                startActivity(intent);
            }
        });
        imbThoahienthimonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lvHienthimonan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(maquyen!=2){
                    MonanDTO p = (MonanDTO) adapter.getItem(i);
                    AlertDialog.Builder builder= new AlertDialog.Builder(Hienthimonan.this);
                    builder.setTitle("X??a m??n ??n");
                    builder.setMessage("B???n mu???n x??a m??n ??n?");
                    builder.setIcon(android.R.drawable.ic_delete);
                    builder.setPositiveButton("?????ng ??", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            monAnDAO.XoaMonAn(p.getMaMonAn());
                            Toast.makeText(Hienthimonan.this, "X??a th??nh c??ng", Toast.LENGTH_SHORT).show();
                            loadData();
                        }
                    });
                    builder.setNegativeButton("Tho??t", new DialogInterface.OnClickListener() {
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
        lvHienthimonan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(maquyen==2 && maban!=0 ){
                    Dialog dialog= new Dialog(Hienthimonan.this);
                    dialog.setContentView(R.layout.custom_dialoggoimon);
                    dialog.setCanceledOnTouchOutside(false);
                    txtTenmonangoi=dialog.findViewById(R.id.txtTenmonangoi);
                    MonanDTO p= (MonanDTO) adapter.getItem(i);
                    int mamonan=p.getMaMonAn();
                    txtTenmonangoi.setText("M??n ??n: "+ p.getTenMonAn());
                    edtSoluong=dialog.findViewById(R.id.edtSoluong);
                    btnDongy=dialog.findViewById(R.id.btnDongy);
                    btnDongy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!edtSoluong.getText().toString().equals("")){
                                String tinhtrang = banDAO.LayTinhTrangBan(maban);
                                if (tinhtrang.equals("false")){
                                    // th???c hi???n code th??m b???ng g???i m??n va c???p nh???t l???i t??nh tr???ng b??n
                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    String ngaygoi= dateFormat.format(calendar.getTime());
                                    GoimonDTO goiMonDTO = new GoimonDTO();
                                    goiMonDTO.setMaBan(maban);
                                    goiMonDTO.setNgayGoi(ngaygoi);
                                    goiMonDTO.setTinhTrang("false");

                                    long kiemtra = goimonDAO.ThemGoiMon(goiMonDTO);
                                    banDAO.CapNhatTinhTrangBan(maban, "true");
                                    if (kiemtra == 0)
                                        Toast.makeText(Hienthimonan.this, "Th??m th???t b???i", Toast.LENGTH_SHORT).show();
                                }
                                int magoimon = (int) goimonDAO.LayMaGoiMonTheoMaBan(maban, "false");
                                boolean kiemtra = goimonDAO.KiemTraMonAnDaTonTai(magoimon, mamonan);
                                if (kiemtra){
                                    //ti???n h??nh c???p nh???t m??n ???? t???n t???i
                                    int soluongcu = goimonDAO.LaySoLuongMonAnTheoMaGoiMon(magoimon, mamonan);
                                    int soluongmoi = Integer.parseInt(edtSoluong.getText().toString());

                                    int tongsoluong = soluongcu + soluongmoi;

                                    ChitietgoimonDTO chiTietGoiMonDTO = new ChitietgoimonDTO();
                                    chiTietGoiMonDTO.setMaGoiMon(magoimon);
                                    chiTietGoiMonDTO.setMaMonAn(mamonan);
                                    chiTietGoiMonDTO.setSoLuong(tongsoluong);

                                    boolean kiemtracapnhat = goimonDAO.CapNhatSoLuong(chiTietGoiMonDTO);
                                    if (kiemtracapnhat)
                                        Toast.makeText(Hienthimonan.this,"Th??m th??nh c??ng", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(Hienthimonan.this, "Th??m th???t b???i", Toast.LENGTH_SHORT).show();
                                }else {
                                    //th??m m??n ??n
                                    int soluonggoi = Integer.parseInt(edtSoluong.getText().toString());

                                    ChitietgoimonDTO chiTietGoiMonDTO = new ChitietgoimonDTO();
                                    chiTietGoiMonDTO.setMaGoiMon(magoimon);
                                    chiTietGoiMonDTO.setMaMonAn(mamonan);
                                    chiTietGoiMonDTO.setSoLuong(soluonggoi);

                                    boolean kiemtrathem = goimonDAO.ThemChiTietGoiMon(chiTietGoiMonDTO);
                                    if (kiemtrathem)
                                        Toast.makeText(Hienthimonan.this, "Th??m th??nh c??ng", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(Hienthimonan.this, "Th??m th???t b???i", Toast.LENGTH_SHORT).show();
                                }
                                finish();
                            }
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
            }
        });
/*        imvThemsoluong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog= new Dialog(Hienthimonan.this);
                dialog.setContentView(R.layout.custom_dialoggoimon);
                dialog.setCanceledOnTouchOutside(false);
                btnDongy=dialog.findViewById(R.id.btnDongy);
                btnDongy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
        });*/
    }

    @Override
    protected void onResume() {
        loadData();
        super.onResume();
    }

    private void linkView() {
        imbThoahienthimonan=findViewById(R.id.imbThoathienthimonan);
        imbThemmonan= findViewById(R.id.imbThemmonan);
        lvHienthimonan=findViewById(R.id.lvHienthimonan);
    }
}