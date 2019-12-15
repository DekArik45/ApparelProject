package com.example.apparelproject.model;

public class TransactionModel {
    private long id;
    private String nama_user;
    private String tanggal;
    private byte[] image;
    private String nama_produk;
    private int harga;
    private int jumlah;
    private String status;
    private byte[] bukti_pembayaran;

    public TransactionModel() {
    }

    public TransactionModel(long id, String nama_user, String tanggal, byte[] image, String nama_produk, int harga, int jumlah, String status, byte[] bukti_pembayaran) {
        this.id = id;
        this.nama_user = nama_user;
        this.tanggal = tanggal;
        this.image = image;
        this.nama_produk = nama_produk;
        this.harga = harga;
        this.jumlah = jumlah;
        this.status = status;
        this.bukti_pembayaran = bukti_pembayaran;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getBukti_pembayaran() {
        return bukti_pembayaran;
    }

    public void setBukti_pembayaran(byte[] bukti_pembayaran) {
        this.bukti_pembayaran = bukti_pembayaran;
    }
}
