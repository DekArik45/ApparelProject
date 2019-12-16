package com.example.apparelproject.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.apparelproject.NewsDetailActivity;
import com.example.apparelproject.R;
import com.example.apparelproject.TransaksiAdminDetailActivity;
import com.example.apparelproject.TransaksiCustomerActivity;
import com.example.apparelproject.TransaksiCustomerDetailActivity;
import com.example.apparelproject.database.TransactionQuery;
import com.example.apparelproject.model.NewsModel;
import com.example.apparelproject.model.TransactionModel;
import com.example.apparelproject.utils.Config;
import com.example.apparelproject.utils.DbBitmapUtility;

import java.util.ArrayList;

public class TransaksiAdminAdapter extends RecyclerView.Adapter<TransaksiAdminAdapter.GridViewHolder> {

    private Context context;
    private ArrayList<TransactionModel> list;
    private String status;

    public TransaksiAdminAdapter(Context context) {
        this.context = context;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @NonNull
    @Override
    public TransaksiAdminAdapter.GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_transaksi, viewGroup, false);
        return new TransaksiAdminAdapter.GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TransaksiAdminAdapter.GridViewHolder gridViewHolder, final int position) {

        SharedPreferences sharedpreferences = context.getSharedPreferences(Config.LOGIN, Context.MODE_PRIVATE);
        final String hakAkses = sharedpreferences.getString(Config.COLUMN_USER_HAKAKSES,"");

        if (getStatus().equals("riwayat")){
            gridViewHolder.mDelete.setVisibility(View.GONE);
        }
        else{
            if (hakAkses.equals("Admin")){
                gridViewHolder.mDelete.setVisibility(View.VISIBLE);
            }
            else if (hakAkses.equals("Customer")){
                gridViewHolder.mDelete.setVisibility(View.GONE);
            }
        }


        gridViewHolder.mNamaUser.setText(getList().get(position).getNama_user());
//        int total = getList().get(position).getJumlah() * getList().get(position).getHarga();
        gridViewHolder.mTotalBelanja.setText("Rp."+String.valueOf(getList().get(position).getHarga()));
        gridViewHolder.mStatus.setText(getList().get(position).getStatus());

        if (getList().get(position).getStatus().equals(Config.STATUS_TRX_DITOLAK)){
            gridViewHolder.mStatus.setTextColor(context.getColor(R.color.md_red_500));
        }
        else if (getList().get(position).getStatus().equals(Config.STATUS_TRX_SELESAI)){
            gridViewHolder.mStatus.setTextColor(context.getColor(R.color.md_green_500));
        }
        else{
            gridViewHolder. mStatus.setTextColor(context.getColor(R.color.md_blue_500));
        }
        gridViewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("yakin ingin menolak transaksi ini?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                ditolak(position);
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

        gridViewHolder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hakAkses.equals("Admin")){
                    Intent i = new Intent(context.getApplicationContext(), TransaksiAdminDetailActivity.class);
                    i.putExtra(Config.COLUMN_TRX_TANGGAL,getList().get(position).getTanggal());
                    i.putExtra(Config.COLUMN_TRX_NAMA_USER,getList().get(position).getNama_user());
                    i.putExtra(Config.COLUMN_TRX_STATUS,getList().get(position).getStatus());
                    i.putExtra(Config.COLUMN_TRX_HARGA,String.valueOf(getList().get(position).getHarga()));
                    i.putExtra(Config.COLUMN_TRX_JUMLAH,String.valueOf(getList().get(position).getJumlah()));
                    i.putExtra(Config.COLUMN_TRX_IMAGE_BUKTIPEMBAYARAN,getList().get(position).getBukti_pembayaran());
                    i.putExtra("statusRiwayat",getStatus());
                    context.startActivity(i);
                    ((Activity)context).finish();
                }
                else if (hakAkses.equals("Customer")){
                    Intent i = new Intent(context.getApplicationContext(), TransaksiCustomerDetailActivity.class);
                    i.putExtra(Config.COLUMN_TRX_TANGGAL,getList().get(position).getTanggal());
                    i.putExtra(Config.COLUMN_TRX_NAMA_USER,getList().get(position).getNama_user());
                    i.putExtra(Config.COLUMN_TRX_STATUS,getList().get(position).getStatus());
                    i.putExtra(Config.COLUMN_TRX_HARGA,String.valueOf(getList().get(position).getHarga()));
                    i.putExtra(Config.COLUMN_TRX_JUMLAH,String.valueOf(getList().get(position).getJumlah()));
                    i.putExtra(Config.COLUMN_TRX_IMAGE_BUKTIPEMBAYARAN,getList().get(position).getBukti_pembayaran());
                    i.putExtra("statusRiwayat",getStatus());
                    context.startActivity(i);
                    ((Activity)context).finish();
                }
            }
        });
    }

    private void ditolak(int position){

        TransactionQuery query = new TransactionQuery(context);

        long id =query.updateTransactionTolak(getList().get(position).getTanggal());

        if (id>0){
            Toast.makeText(context, "Transaksi ditolak", Toast.LENGTH_LONG).show();
            getList().remove(position);
            notifyDataSetChanged();
        }
        else{
            Toast.makeText(context, "data Gagal diUpdate", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        return getList().size();
//        return 9;
    }

    public ArrayList<TransactionModel> getList() {
        return list;
    }

    public void setList(ArrayList<TransactionModel> list) {
        this.list = list;
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView mDelete;
        TextView mNamaUser, mTotalBelanja, mStatus;
        CardView mCard;
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            mDelete = itemView.findViewById(R.id.item_transaksi_produk_delete);
            mNamaUser = itemView.findViewById(R.id.item_transaksi_produk_namaUser);
            mTotalBelanja = itemView.findViewById(R.id.item_transaksi_produk_totalBelanja);
            mStatus = itemView.findViewById(R.id.item_transaksi_produk_status);
            mCard = itemView.findViewById(R.id.item_transaksi_produk_cardView);

        }
    }
}
