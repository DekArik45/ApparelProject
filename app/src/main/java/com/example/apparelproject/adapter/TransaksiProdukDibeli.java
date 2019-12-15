package com.example.apparelproject.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apparelproject.R;
import com.example.apparelproject.database.TransactionQuery;
import com.example.apparelproject.model.ProductModel;
import com.example.apparelproject.model.TransactionModel;
import com.example.apparelproject.utils.Config;
import com.example.apparelproject.utils.DbBitmapUtility;

import java.util.ArrayList;

public class TransaksiProdukDibeli extends RecyclerView.Adapter<TransaksiProdukDibeli.GridViewHolder> {

    private Context context;
    private ArrayList<TransactionModel> list;

    public TransaksiProdukDibeli(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_produk_dibeli, viewGroup, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder gridViewHolder, final int position) {

        gridViewHolder.mNama.setText(getList().get(position).getNama_produk());
        gridViewHolder.mHarga.setText("Rp."+String.valueOf(getList().get(position).getHarga()));
        gridViewHolder.mJumlah.setText(String.valueOf(getList().get(position).getJumlah()));
        Bitmap imageBitmap = DbBitmapUtility.getImage(getList().get(position).getImage());
        Glide.with(context)
                .load(imageBitmap)
                .apply(new RequestOptions())
                .into(gridViewHolder.mImage);
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    public ArrayList<TransactionModel> getList() {
        return list;
    }

    public void setList(ArrayList<TransactionModel> list) {
        this.list = list;
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView mImage;
        TextView mNama, mHarga, mJumlah;
        CardView mCard;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.item_produk_dibeli_image);
            mNama = itemView.findViewById(R.id.item_produk_dibeli_nama);
            mHarga = itemView.findViewById(R.id.item_produk_dibeli_hargaJual);
            mJumlah = itemView.findViewById(R.id.item_produk_dibeli_jumlah);
            mCard = itemView.findViewById(R.id.item_produk_dibeli_cardview);

        }
    }
}
