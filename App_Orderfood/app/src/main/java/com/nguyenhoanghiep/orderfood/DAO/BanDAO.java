package com.nguyenhoanghiep.orderfood.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nguyenhoanghiep.orderfood.DTO.BanDTO;
import com.nguyenhoanghiep.orderfood.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class BanDAO {
    private final SQLiteDatabase database;

    public BanDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }
    public boolean ThemBanAn(String tenban){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TENBAN, tenban);
        contentValues.put(CreateDatabase.TB_BANAN_TINHTRANG, "false");

        long kiemtra = database.insert(CreateDatabase.TB_BANAN, null, contentValues);
        return kiemtra != 0;
    }
    @SuppressLint({"Recycle", "Range"})
    public List<BanDTO> LayTatCaBanAn(){
        List<BanDTO> banAnDTOList = new ArrayList<>();
        String truyvan= "SELECT * FROM " + CreateDatabase.TB_BANAN;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            BanDTO banAnDTO = new BanDTO();
            banAnDTO.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_BANAN_MABAN)));
            banAnDTO.setTenBan(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_BANAN_TENBAN)));
            banAnDTO.setDuocChon(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_BANAN_TINHTRANG)));
            banAnDTOList.add(banAnDTO);
            cursor.moveToNext();
        }
        return banAnDTOList;
    }
    @SuppressLint({"Recycle", "Range"})
    public String LayTinhTrangBan(int maban){
        String tinhtrang = "";
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_BANAN + " WHERE " + CreateDatabase.TB_BANAN_MABAN + " = '" + maban + "'";
        Cursor cursor = database.rawQuery(truyvan, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tinhtrang = cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_BANAN_TINHTRANG));
            cursor.moveToNext();
        }
        return tinhtrang;
    }
    public boolean CapNhatTinhTrangBan(int maban, String tinhtrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TINHTRANG, tinhtrang);

        long kiemtra = database.update(CreateDatabase.TB_BANAN, contentValues, CreateDatabase.TB_BANAN_MABAN + " = '" + maban + "'", null);
        return kiemtra != 0;
    }
    public boolean CapNhatTenBan(int maban, String tenban){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TENBAN, tenban);

        long kiemtra = database.update(CreateDatabase.TB_BANAN, contentValues, CreateDatabase.TB_BANAN_MABAN + " = '" + maban + "'", null);
        return kiemtra != 0;
    }
/*    public void XoaBanAn(int maban){
        long kiemtra = database.delete(CreateDatabase.TB_BANAN, CreateDatabase.TB_BANAN_MABAN + " = " + maban, null);
    }*/
    public boolean XoaBanAn(int maban){
        long kiemtra = database.delete(CreateDatabase.TB_BANAN, CreateDatabase.TB_BANAN_MABAN + " = " + maban, null);
        return kiemtra != 0;
    }
}
