package com.example.apparelproject.model;

public class UserModel {
    private String namaUser, emailUser, username, password, fotoUser;

    public UserModel(String namaUser, String emailUser, String username, String password, String fotoUser) {
        this.namaUser = namaUser;
        this.emailUser = emailUser;
        this.username = username;
        this.password = password;
        this.fotoUser = fotoUser;
    }

    public UserModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFotoUser() {
        return fotoUser;
    }

    public void setFotoUser(String fotoUser) {
        this.fotoUser = fotoUser;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
