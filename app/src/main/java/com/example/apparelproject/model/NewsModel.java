package com.example.apparelproject.model;

import java.sql.Blob;

public class NewsModel {

    private long id;
    private String judul;
    private String deskripsi;
    private byte[] image;

    public NewsModel() {

    }

    public NewsModel(long id, String judul, String deskripsi, byte[] image) {
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
