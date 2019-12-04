package com.example.apparelproject.model;

public class ProductModel {

    private String nama, harga, kategori, deskripsi, ukuran, brand, bahan, warna, foto, status;

    public ProductModel() {
    }

    public ProductModel(String nama, String harga, String kategori, String deskripsi, String ukuran, String brand, String bahan, String warna, String foto, String status) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
        this.deskripsi = deskripsi;
        this.ukuran = ukuran;
        this.brand = brand;
        this.bahan = bahan;
        this.warna = warna;
        this.foto = foto;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBahan() {
        return bahan;
    }

    public void setBahan(String bahan) {
        this.bahan = bahan;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}
