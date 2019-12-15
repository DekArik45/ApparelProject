package com.example.apparelproject.utils;

public class Config {

    public static final String DATABASE_NAME = "db_apparel";

    //column names of tabel produk
    public static final String TABEL_PRODUK = "tb_produk";
    public static final String COLUMN_PRODUK_ID = "_id";
    public static final String COLUMN_PRODUK_IMAGE = "image";
    public static final String COLUMN_PRODUK_NAMA = "nama";
    public static final String COLUMN_PRODUK_DESKRIPSI = "deskripsi";
    public static final String COLUMN_PRODUK_KATEGORI = "kategori";
    public static final String COLUMN_PRODUK_HARGA = "harga";
    public static final String COLUMN_PRODUK_WARNA = "warna";
    public static final String COLUMN_PRODUK_UKURAN = "ukuran";

    //column names of tabel news
    public static final String TABEL_NEWS = "tb_news";
    public static final String COLUMN_NEWS_ID = "_id";
    public static final String COLUMN_NEWS_JUDUL = "judul";
    public static final String COLUMN_NEWS_DESKRIPSI = "deskripsi";
    public static final String COLUMN_NEWS_IMAGE = "image";

    //column names of tabel user
    public static final String TABEL_USER = "tb_user";
    public static final String COLUMN_USER_ID = "_id";
    public static final String COLUMN_USER_NAMA = "nama";
    public static final String COLUMN_USER_JENISKELAMIN = "jenis_kelamin";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_USERNAME = "username";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_IMAGE = "image";
    public static final String COLUMN_USER_ALAMAT = "alamat";
    public static final String COLUMN_USER_HAKAKSES = "hak_akses";
    //keperluan login sharepreferenaces
    public static final String SESSION = "session";
    public static final String LOGIN = "login";

    //column names of tabel tranksaksi
    public static final String TABEL_TRX = "tb_transaksi";
    public static final String COLUMN_TRX_ID = "_id";
    public static final String COLUMN_TRX_NAMA_USER = "nama_user";
    public static final String COLUMN_TRX_IMAGE_PRODUK = "image_produk";
    public static final String COLUMN_TRX_NAMA_PRODUK = "nama_produk";
    public static final String COLUMN_TRX_HARGA = "harga";
    public static final String COLUMN_TRX_JUMLAH = "jumlah";
    public static final String COLUMN_TRX_TANGGAL = "tanggal";
    public static final String COLUMN_TRX_STATUS = "status";
    public static final String COLUMN_TRX_IMAGE_BUKTIPEMBAYARAN = "bukti_pembayaran";

    //others for general purpose key-value pair data
    public static final String TITLE = "title";

    public static final String CREATE_PRODUK = "create_produk";
    public static final String UPDATE_PRODUK = "update_produk";

    public static final String CREATE_NEWS = "create_news";
    public static final String UPDATE_NEWS = "update_news";

    public static final String CREATE_USER = "create_user";
    public static final String UPDATE_USER = "update_user";

    public static final String CREATE_TRX = "create_transaksi";
    public static final String UPDATE_TRX = "update_transaksi";

    //another
    public static final String STATUS_TRX_CART = "cart";
    public static final String STATUS_TRX_BELUMBAYAR = "Belum Bayar";
    public static final String STATUS_TRX_MENUNGGUVERIFIKASI = "Menunggu Verifikasi";
    public static final String STATUS_TRX_TERVERIFIKASI = "Terverifikasi";
    public static final String STATUS_TRX_PENGIRIMAN = "Pengiriman";
    public static final String STATUS_TRX_SELESAI = "Selesai";
    public static final String STATUS_TRX_DITOLAK = "Tolak";
    public static final String COATS = "Coats";
    public static final String TSHIRT = "Tshirt";
    public static final String SWEATERS = "Sweater";
    public static final String JERSEY = "Jersey";
    public static final String PANTS = "Pants";
    public static final String UNDERSHIRT = "Undershirt";
    public static final String URL_IMAGE = "http://firstbot18.000webhostapp.com/foto_apparel/";
}
