package com.example.apparelproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apparelproject.R;
import com.example.apparelproject.model.ProductModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemProductCartAdapter extends RecyclerView.Adapter<ItemProductCartAdapter.CategoryViewHolder> {

    private Context context;
    private ArrayList<ProductModel> listProduct;

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_cart, viewGroup, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, final int position) {
        categoryViewHolder.itemName.setText(getListProduct().get(position).getNama());
        categoryViewHolder.itemprice.setText(getListProduct().get(position).getHarga());
        Glide.with(context)
                .load(getListProduct().get(position).getFoto())
                .apply(new RequestOptions())
                .into(categoryViewHolder.imgPhoto);

    }

    public ItemProductCartAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return getListProduct().size();
//        return 0;
    }

    public ArrayList<ProductModel> getListProduct() {
        return listProduct;
    }

    public void setListProduct(ArrayList<ProductModel> listProduct) {
        this.listProduct = listProduct;
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        CardView itemcard;
        TextView itemName, itemprice, itemqty;
        CircleImageView imgPhoto;
        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemcard = itemView.findViewById(R.id.item_cart_cardView);
            imgPhoto = itemView.findViewById(R.id.item_cart_circleImageView);
            itemName = itemView.findViewById(R.id.item_cart_product_name);
            itemprice = itemView.findViewById(R.id.item_cart_product_price);
            itemqty = itemView.findViewById(R.id.item_cart_qty);
        }
    }


}