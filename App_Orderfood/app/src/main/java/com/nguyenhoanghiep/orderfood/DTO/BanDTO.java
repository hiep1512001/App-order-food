package com.nguyenhoanghiep.orderfood.DTO;

public class BanDTO {
    private int MaBan;
    private String TenBan;
    private String DuocChon;


    public void setMaBan(int maBan) {
        MaBan = maBan;
    }

    public void setTenBan(String tenBan) {
        TenBan = tenBan;
    }

    public void setDuocChon(String duocChon) {
        DuocChon = duocChon;
    }

    public int getMaBan() {
        return MaBan;
    }

    public String getTenBan() {
        return TenBan;
    }

    public String getDuocChon() {
        return DuocChon;
    }
}
