package com.example.apparelproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apparelproject.ProductDetailActivity;
import com.example.apparelproject.R;
import com.example.apparelproject.model.ProductModel;
import com.example.apparelproject.utils.Config;

import java.util.ArrayList;

public class ItemProductMainAdapter extends RecyclerView.Adapter<ItemProductMainAdapter.GridViewHolder> {

    private Context context;
    private ArrayList<ProductModel> listProduct;

    public ItemProductMainAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_main, viewGroup, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder gridViewHolder, final int position) {

        gridViewHolder.title.setText(getListProduct().get(position).getNama());
//        gridViewHolder.price.setText(getListProduct().get(position).getHarga());
        Glide.with(context)
                .load(getListProduct().get(position).getImage())
                .apply(new RequestOptions())
                .into(gridViewHolder.imgPhoto);

        gridViewHolder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), ProductDetailActivity.class);
                i.putExtra(Config.COLUMN_PRODUK_NAMA,getListProduct().get(position).getNama());
                i.putExtra(Config.COLUMN_PRODUK_HARGA,getListProduct().get(position).getHarga().toString());
                i.putExtra(Config.COLUMN_PRODUK_IMAGE,getListProduct().get(position).getImage());
                i.putExtra(Config.COLUMN_PRODUK_ID,String.valueOf(getListProduct().get(position).getId()));
                i.putExtra(Config.COLUMN_PRODUK_DESKRIPSI,getListProduct().get(position).getDeskripsi());
                i.putExtra(Config.COLUMN_PRODUK_KATEGORI, getListProduct().get(position).getKategori());
                i.putExtra(Config.COLUMN_PRODUK_UKURAN, getListProduct().get(position).getUkuran());
                i.putExtra(Config.COLUMN_PRODUK_WARNA, getListProduct().get(position).getWarna());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
//        return getListProduct().size();
        return 9;
    }

    public ArrayList<ProductModel> getListProduct() {
        return listProduct;
    }

    public void setListProduct(ArrayList<ProductModel> listProduct) {
        this.listProduct = listProduct;
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView title, price;
        CardView mCard;
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.item_product_main_image);
            mCard = itemView.findViewById(R.id.item_product_main_card);
            title = itemView.findViewById(R.id.item_product_main_title);
//            price = itemView.findViewById(R.id.item_product_main_price);
        }
    }
}

