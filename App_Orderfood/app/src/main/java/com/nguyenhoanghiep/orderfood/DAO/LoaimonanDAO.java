package com.nguyenhoanghiep.orderfood.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nguyenhoanghiep.orderfood.DTO.LoaimonanDTO;
import com.nguyenhoanghiep.orderfood.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoaimonanDAO {
    private final SQLiteDatabase database;

    public LoaimonanDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }
    public boolean ThemLoaiMonAn(String tenloai){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_LOAIMONAN_TENLOAI, tenloai);

        long kiemtra = database.insert(CreateDatabase.TB_LOAIMONAN, null, contentValues);

        return kiemtra != 0;
    }

    @SuppressLint({"Recycle", "Range"})
    public List<LoaimonanDTO> LayDanhSachLoaiMonAn(){
        List<LoaimonanDTO> loaiMonAnDTOs = new ArrayList<>();

        String truyvan = "SELECT * FROM " + CreateDatabase.TB_LOAIMONAN;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            LoaimonanDTO loaiMonAnDTO = new LoaimonanDTO();
            loaiMonAnDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_LOAIMONAN_MALOAI)));
            loaiMonAnDTO.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_LOAIMONAN_TENLOAI)));

            loaiMonAnDTOs.add(loaiMonAnDTO);

            cursor.moveToNext();
        }

        return loaiMonAnDTOs;
    }

    @SuppressLint({"Recycle", "Range"})
    public String LayHinhLoaiMonAn(int maloai){
        String hinhanh = "";
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_MONAN + " WHERE " + CreateDatabase.TB_MONAN_MALOAI + " = '" + maloai + "' "
                + " AND " + CreateDatabase.TB_MONAN_HINHANH + " != '' ORDER BY " + CreateDatabase.TB_MONAN_MAMON + " LIMIT 1";

        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            hinhanh = cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_HINHANH));
            cursor.moveToNext();
        }
        return hinhanh;
    }
    public boolean Xoaloaimonan(int maloai){
        long kiemtra = database.delete(CreateDatabase.TB_LOAIMONAN, CreateDatabase.TB_LOAIMONAN_MALOAI + " = " + maloai, null);
        return kiemtra != 0;
    }
}
