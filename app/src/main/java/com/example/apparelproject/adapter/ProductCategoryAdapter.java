package com.example.apparelproject.adapter;

import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apparelproject.ProductDetailActivity;
import com.example.apparelproject.R;
import com.example.apparelproject.model.ProductModel;
import com.example.apparelproject.utils.Config;
import com.example.apparelproject.utils.DbBitmapUtility;

import java.util.ArrayList;


public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.CategoryViewHolder> {

    private Context context;
    private ArrayList<ProductModel> listProduct;
    private String status, category;

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_category, viewGroup, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, final int position) {
        String harga = getListProduct().get(position).getHarga().toString();
            categoryViewHolder.title.setText(getListProduct().get(position).getNama());
            categoryViewHolder.price.setText(harga);

        Bitmap imageBitmap = DbBitmapUtility.getImage(getListProduct().get(position).getImage());
            Glide.with(context)
                    .load(imageBitmap)
                    .apply(new RequestOptions())
                    .into(categoryViewHolder.imgPhoto);

            categoryViewHolder.mCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ProductDetailActivity.class);
                    i.putExtra(Config.COLUMN_PRODUK_NAMA,getListProduct().get(position).getNama());
                    i.putExtra(Config.COLUMN_PRODUK_HARGA,getListProduct().get(position).getHarga().toString());
                    i.putExtra(Config.COLUMN_PRODUK_IMAGE,getListProduct().get(position).getImage());
                    i.putExtra(Config.COLUMN_PRODUK_ID,String.valueOf(getListProduct().get(position).getId()));
                    i.putExtra(Config.COLUMN_PRODUK_DESKRIPSI,getListProduct().get(position).getDeskripsi());
                    i.putExtra(Config.COLUMN_PRODUK_KATEGORI, getListProduct().get(position).getKategori());
                    i.putExtra(Config.COLUMN_PRODUK_UKURAN, getListProduct().get(position).getUkuran());
                    i.putExtra(Config.COLUMN_PRODUK_WARNA, getListProduct().get(position).getWarna());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            });

    }


    public ProductCategoryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return getListProduct().size();
    }

    public ArrayList<ProductModel> getListProduct() {
        return listProduct;
    }

    public void setListProduct(ArrayList<ProductModel> listProduct) {
        this.listProduct = listProduct;
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView title, price;
        CardView mCard;
        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.item_product_category_image);
            mCard = itemView.findViewById(R.id.item_product_category_card);
            title = itemView.findViewById(R.id.item_product_category_title);
            price = itemView.findViewById(R.id.item_product_category_price);
        }
    }


}