package com.nguyenhoanghiep.orderfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nguyenhoanghiep.orderfood.Adapter.AdapterHienthibanan;
import com.nguyenhoanghiep.orderfood.DAO.BanDAO;
import com.nguyenhoanghiep.orderfood.DTO.BanDTO;
import com.nguyenhoanghiep.orderfood.DTO.MonanDTO;

import java.util.ArrayList;
import java.util.List;

public class Hienthibanan extends AppCompatActivity {
    ImageButton imbThembanan,imbThoathienthibanan;
    GridView gvHienthibanan;
    Button btnDongy, btnThoat,btnThanhtoan,btnGoimon,btnAn;
    EditText edtTenbanan;
    BanDAO bananDAO;
    TextView txtTenbanduocchon;
    List<BanDTO> list_ban,list_bansosanh;
    BanDTO selectbanan=null;
    AdapterHienthibanan adapter;
    private int maquyen=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hienthibanan);
        bananDAO= new BanDAO(Hienthibanan.this);
        linkView();
        getData();
        loadData();
        addEvent();

    }

    private void loadData() {
        list_ban= new ArrayList<>();
        list_ban.clear();
        list_ban=bananDAO.LayTatCaBanAn();
        adapter= new AdapterHienthibanan(Hienthibanan.this,R.layout.custom_hienthibanan,list_ban);
        gvHienthibanan.setAdapter(adapter);
    }

    private void getData() {
        SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);
        if(maquyen==2){
            imbThembanan.setVisibility(View.INVISIBLE);
        }
    }
int Kiemtra(){
        int t=1;
        list_bansosanh=bananDAO.LayTatCaBanAn();
        for(int i=0; i<list_bansosanh.size(); i++){
            BanDTO p= list_bansosanh.get(i);
                if(p.getTenBan().equals(edtTenbanan.getText().toString())){
                    t=0;
                    break;
                }
        }
        return t;
}
    private void addEvent() {
        imbThoathienthibanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        gvHienthibanan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectbanan= (BanDTO) adapter.getItem(i);
                if(maquyen==2){
                    Dialog dialog= new Dialog(Hienthibanan.this);
                    dialog.setContentView(R.layout.custom_dialogchondichvu);
                    dialog.setCanceledOnTouchOutside(false);
                    btnThanhtoan=dialog.findViewById(R.id.btnThanhToan);
                    txtTenbanduocchon=dialog.findViewById(R.id.txtTenbanduocchon);
                    txtTenbanduocchon.setText("Chọn dịch vụ bàn: "+selectbanan.getTenBan());
                    btnGoimon=dialog.findViewById(R.id.btnGoimon);
                    btnAn=dialog.findViewById(R.id.btnAn);
                    btnGoimon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent= new Intent(Hienthibanan.this,Hienthiloaimonan.class);
                            intent.putExtra("maban",selectbanan.getMaBan());

                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    btnThanhtoan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent= new Intent(Hienthibanan.this,Thanhtoan.class);
                            intent.putExtra("mabanthanhtoan",selectbanan.getMaBan());
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    btnAn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });
        gvHienthibanan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectbanan= (BanDTO) adapter.getItem(i);
                if(maquyen!=2){
                    AlertDialog.Builder builder= new AlertDialog.Builder(Hienthibanan.this);
                    builder.setTitle("Xóa bàn ăn");
                    builder.setMessage("Bạn muốn xóa bàn ăn?");
                    builder.setIcon(android.R.drawable.ic_delete);
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            boolean kiemtra = bananDAO.XoaBanAn(selectbanan.getMaBan());
                            if(kiemtra){
                                loadData();
                                Toast.makeText(Hienthibanan.this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(Hienthibanan.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
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
                return false;
            }
        });

        imbThembanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog= new Dialog(Hienthibanan.this);
                dialog.setContentView(R.layout.custom_dialogthembanan);
                dialog.setCanceledOnTouchOutside(false);
                btnDongy=dialog.findViewById(R.id.btnDongy);
                edtTenbanan=dialog.findViewById(R.id.edtTenbanan);
                btnDongy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!edtTenbanan.getText().toString().equals("")&&Kiemtra()==1){
                            bananDAO.ThemBanAn(edtTenbanan.getText().toString());
                            Toast.makeText(Hienthibanan.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            loadData();
                        }
                        else {
                            Toast.makeText(Hienthibanan.this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    protected void onResume() {
        loadData();
        super.onResume();
    }

    private void linkView() {
        imbThembanan=findViewById(R.id.imbThembanan);
        imbThoathienthibanan=findViewById(R.id.imbThoathienthibanan);
        gvHienthibanan= findViewById(R.id.gvHienthibanan);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu3, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.mnGoimon){
            Intent intent= new Intent(Hienthibanan.this,Hienthiloaimonan.class);
            intent.putExtra("maban",selectbanan.getMaBan());

            startActivity(intent);
        }else if(item.getItemId()==R.id.mnThanhtoan){
            Intent intent= new Intent(Hienthibanan.this,Thanhtoan.class);
            intent.putExtra("mabanthanhtoan",selectbanan.getMaBan());
            startActivity(intent);
        }
        return super.onContextItemSelected(item);
    }
}