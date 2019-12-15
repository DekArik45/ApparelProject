package com.example.apparelproject.model;

import java.sql.Blob;

public class UserModel {

    private long id;
    private String nama;
    private String username;
    private String password;
    private byte[] image;
    private String jenis_kelamin;
    private String email;
    private String alamat;
    private String hak_akses;

    public UserModel() {
    }

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserModel(long id, String nama, String username, String password, byte[] image, String jenis_kelamin, String email, String alamat, String hak_akses) {
        this.id = id;
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.image = image;
        this.jenis_kelamin = jenis_kelamin;
        this.email = email;
        this.alamat = alamat;
        this.hak_akses = hak_akses;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getHak_akses() {
        return hak_akses;
    }

    public void setHak_akses(String hak_akses) {
        this.hak_akses = hak_akses;
    }
}
