package com.example.apparelproject.adapter;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apparelproject.EditProductActivity;
import com.example.apparelproject.ProductActivity;
import com.example.apparelproject.ProductDetailActivity;
import com.example.apparelproject.R;
import com.example.apparelproject.database.ProductQuery;
import com.example.apparelproject.model.ProductModel;
import com.example.apparelproject.utils.Config;
import com.example.apparelproject.utils.DbBitmapUtility;

import java.util.ArrayList;

public class ProductAdminAdapter extends RecyclerView.Adapter<ProductAdminAdapter.CategoryViewHolder> {

    private Context context;
    private ArrayList<ProductModel> listProduct;
    private String status, category;
    private ProductQuery productQuery;

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item_list_product, viewGroup, false);
        return new CategoryViewHolder(itemRow);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, final int position) {
        categoryViewHolder.title.setText(getListProduct().get(position).getNama());
        categoryViewHolder.price.setText(getListProduct().get(position).getHarga().toString());
//        gridViewHolder.price.setText(getListProduct().get(position).getHarga());
//        byte[] imageByte = Base64.decode(getListNews().get(position).getImage(),Base64.DEFAULT);
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
                context.startActivity(i);
            }
        });

        categoryViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), EditProductActivity.class);
                i.putExtra(Config.COLUMN_PRODUK_NAMA, getListProduct().get(position).getNama());
                i.putExtra(Config.COLUMN_PRODUK_DESKRIPSI, getListProduct().get(position).getDeskripsi());
                i.putExtra(Config.COLUMN_PRODUK_ID, String.valueOf(getListProduct().get(position).getId()));
                i.putExtra(Config.COLUMN_PRODUK_IMAGE, getListProduct().get(position).getImage());
                i.putExtra(Config.COLUMN_PRODUK_KATEGORI, getListProduct().get(position).getKategori());
                i.putExtra(Config.COLUMN_PRODUK_HARGA, getListProduct().get(position).getHarga().toString());
                i.putExtra(Config.COLUMN_PRODUK_WARNA, getListProduct().get(position).getWarna());
                i.putExtra(Config.COLUMN_PRODUK_UKURAN, getListProduct().get(position).getUkuran());
                context.startActivity(i);
                ((Activity)context).finish();
            }
        });

        categoryViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("yakin ingin menghapus Produk ini?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                delete(position);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    private void delete(int position){
        ProductModel produk = listProduct.get(position);
        productQuery = new ProductQuery(context);
        long count = productQuery.deleteProductByID(produk.getId());

        if (count > 0) {
            listProduct.remove(position);
            notifyDataSetChanged();
            Toast.makeText(context, "product berhasil di delete", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "gagal delete, ada yang salah!", Toast.LENGTH_LONG).show();
    }

    public ProductAdminAdapter(Context context) {
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
        Button edit, delete;
        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.item_list_product_gambar);
            mCard = itemView.findViewById(R.id.list_product_card);
            title = itemView.findViewById(R.id.item_list_product_namabarang);
            price = itemView.findViewById(R.id.item_list_product_harga);
            edit = itemView.findViewById(R.id.item_list_product_edit);
            delete = itemView.findViewById(R.id.item_list_product_delete);
        }
    }
}
