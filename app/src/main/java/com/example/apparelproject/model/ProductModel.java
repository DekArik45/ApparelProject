package com.example.apparelproject.model;

import java.sql.Blob;

public class ProductModel {

    private long id;
    private byte[] image;
    private String nama;
    private String deskripsi;
    private String kategori;
    private Integer harga;
    private String warna;
    private String ukuran;

    public ProductModel() {

    }

    public ProductModel(long id, byte[] image, String nama, String deskripsi, String kategori, Integer harga, String warna, String ukuran) {
        this.id = id;
        this.image = image;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.kategori = kategori;
        this.harga = harga;
        this.warna = warna;
        this.ukuran = ukuran;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }
}
