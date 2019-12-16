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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apparelproject.EditNewsActivity;
import com.example.apparelproject.NewsDetailActivity;
import com.example.apparelproject.R;
import com.example.apparelproject.database.NewsQuery;
import com.example.apparelproject.model.NewsModel;
import com.example.apparelproject.utils.Config;
import com.example.apparelproject.utils.DbBitmapUtility;

import java.util.ArrayList;

public class NewsAdminAdapter extends RecyclerView.Adapter<NewsAdminAdapter.CategoryViewHolder> {

    private Context context;
    private ArrayList<NewsModel> listNews;
    private String status, category;
    private NewsQuery newsQuery;

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_list_news, viewGroup, false);
        return new CategoryViewHolder(itemRow);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, final int position) {
        categoryViewHolder.title.setText(getListNews().get(position).getJudul());
//        gridViewHolder.price.setText(getListNews().get(position).getHarga());
//        byte[] imageByte = Base64.decode(getListNews().get(position).getImage(),Base64.DEFAULT);
        Bitmap imageBitmap = DbBitmapUtility.getImage(getListNews().get(position).getImage());
        Glide.with(context)
                .load(imageBitmap)
                .apply(new RequestOptions())
                .into(categoryViewHolder.imgPhoto);

        categoryViewHolder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), NewsDetailActivity.class);
                i.putExtra(Config.COLUMN_NEWS_JUDUL, getListNews().get(position).getJudul());
                i.putExtra(Config.COLUMN_NEWS_IMAGE, getListNews().get(position).getImage());
                i.putExtra(Config.COLUMN_NEWS_DESKRIPSI, getListNews().get(position).getDeskripsi());
                context.startActivity(i);
            }
        });

        categoryViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), EditNewsActivity.class);
                i.putExtra(Config.COLUMN_NEWS_IMAGE, getListNews().get(position).getImage());
                i.putExtra(Config.COLUMN_NEWS_ID, String.valueOf(getListNews().get(position).getId()));
                i.putExtra(Config.COLUMN_NEWS_JUDUL, getListNews().get(position).getJudul());
                i.putExtra(Config.COLUMN_NEWS_DESKRIPSI, getListNews().get(position).getDeskripsi());

                context.startActivity(i);
            }
        });

        categoryViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("yakin ingin menghapus news ini?");
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
        NewsModel news = listNews.get(position);
        newsQuery = new NewsQuery(context);
        long count = newsQuery.deleteNewsByID(news.getId());

        if (count > 0) {
            listNews.remove(position);
            notifyDataSetChanged();
            Toast.makeText(context, "news berhasil di delete", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "gagal delete, ada yang salah!", Toast.LENGTH_LONG).show();
    }

    public NewsAdminAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return getListNews().size();
    }

    public ArrayList<NewsModel> getListNews() {
        return listNews;
    }

    public void setListNews(ArrayList<NewsModel> listNews) {
        this.listNews = listNews;
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView title;
        LinearLayout mCard;
        Button edit, delete;
        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.image_list_news);
            mCard = itemView.findViewById(R.id.list_news_card);
            title = itemView.findViewById(R.id.tittle_list_news);
//            price = itemView.findViewById(R.id.item_list_product_harga);
            edit = itemView.findViewById(R.id.button1_list_news);
            delete = itemView.findViewById(R.id.button2_list_news);
        }
    }
}
