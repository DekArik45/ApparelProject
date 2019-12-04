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

        if (this.status.equals("1")){
            if (getListProduct().get(position).getKategori().equals(this.category)){
                if (getListProduct().get(position).getStatus().equals("1")){
                    categoryViewHolder.title.setText(getListProduct().get(position).getNama());
                    categoryViewHolder.price.setText(getListProduct().get(position).getHarga());
                    Glide.with(context)
                            .load(getListProduct().get(position).getFoto())
                            .apply(new RequestOptions())
                            .into(categoryViewHolder.imgPhoto);

                    categoryViewHolder.mCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(context.getApplicationContext(), ProductDetailActivity.class);
                            i.putExtra("title",getListProduct().get(position).getNama());
                            i.putExtra("price",getListProduct().get(position).getHarga());
                            i.putExtra("image",getListProduct().get(position).getFoto());
                            i.putExtra("brand", getListProduct().get(position).getBrand());
                            i.putExtra("category", getListProduct().get(position).getKategori());
                            i.putExtra("size", getListProduct().get(position).getUkuran());
                            i.putExtra("color", getListProduct().get(position).getWarna());
                            i.putExtra("material", getListProduct().get(position).getBahan());
                            context.startActivity(i);
                        }
                    });
                }
                else{
                    categoryViewHolder.mCard.setVisibility(View.GONE);
                }
            }
            else{
                categoryViewHolder.mCard.setVisibility(View.GONE);
            }
        }
        else{
//            if (getListProduct().get(position).getKategori().equals(this.category)){
                categoryViewHolder.title.setText(getListProduct().get(position).getNama());
                categoryViewHolder.price.setText(getListProduct().get(position).getHarga());
                Glide.with(context)
                        .load(getListProduct().get(position).getFoto())
                        .apply(new RequestOptions())
                        .into(categoryViewHolder.imgPhoto);

                categoryViewHolder.mCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context.getApplicationContext(), ProductDetailActivity.class);
                        i.putExtra("title",getListProduct().get(position).getNama());
                        i.putExtra("price",getListProduct().get(position).getHarga());
                        i.putExtra("image",getListProduct().get(position).getFoto());
                        i.putExtra("brand", getListProduct().get(position).getBrand());
                        i.putExtra("category", getListProduct().get(position).getKategori());
                        i.putExtra("size", getListProduct().get(position).getUkuran());
                        i.putExtra("color", getListProduct().get(position).getWarna());
                        i.putExtra("material", getListProduct().get(position).getBahan());
                        context.startActivity(i);
                    }
                });
//            }
//            else{
//                categoryViewHolder.mCard.setVisibility(View.GONE);
//            }
        }



    }


    public ProductCategoryAdapter(Context context, String status, String category) {
        this.context = context;
        this.status = status;
        this.category = category;
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