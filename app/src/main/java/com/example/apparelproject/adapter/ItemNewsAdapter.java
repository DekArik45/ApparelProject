package com.example.apparelproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apparelproject.NewsDetailActivity;
import com.example.apparelproject.ProductDetailActivity;
import com.example.apparelproject.R;
import com.example.apparelproject.model.NewsModel;
import com.example.apparelproject.model.ProductModel;
import com.example.apparelproject.utils.Config;
import com.example.apparelproject.utils.DbBitmapUtility;

import java.util.ArrayList;

public class ItemNewsAdapter extends RecyclerView.Adapter<ItemNewsAdapter.GridViewHolder> {

    private Context context;
    private ArrayList<NewsModel> listNews;

    public ItemNewsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ItemNewsAdapter.GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_main, viewGroup, false);
        return new ItemNewsAdapter.GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder gridViewHolder, final int position) {

        gridViewHolder.title.setText(getListNews().get(position).getJudul());
//        gridViewHolder.price.setText(getListProduct().get(position).getHarga());
//        byte[] imageByte = Base64.decode(getListNews().get(position).getImage(),Base64.DEFAULT);
        Bitmap imageBitmap = DbBitmapUtility.getImage(getListNews().get(position).getImage());
        Glide.with(context)
                .load(imageBitmap)
                .apply(new RequestOptions())
                .into(gridViewHolder.imgPhoto);

        gridViewHolder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), NewsDetailActivity.class);
                i.putExtra(Config.COLUMN_NEWS_JUDUL,getListNews().get(position).getJudul());
                i.putExtra(Config.COLUMN_NEWS_IMAGE,getListNews().get(position).getImage());
                i.putExtra(Config.COLUMN_NEWS_DESKRIPSI,getListNews().get(position).getDeskripsi());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getListNews().size();
//        return 9;
    }

    public ArrayList<NewsModel> getListNews() {
        return listNews;
    }

    public void setListNews(ArrayList<NewsModel> listNews) {
        this.listNews = listNews;
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView title;
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
