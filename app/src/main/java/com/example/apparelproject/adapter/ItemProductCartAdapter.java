package com.example.apparelproject.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apparelproject.R;
import com.example.apparelproject.database.TransactionQuery;
import com.example.apparelproject.model.ProductModel;
import com.example.apparelproject.model.TransactionModel;
import com.example.apparelproject.utils.Config;
import com.example.apparelproject.utils.DbBitmapUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemProductCartAdapter extends RecyclerView.Adapter<ItemProductCartAdapter.CategoryViewHolder> {

    private Context context;
    private ArrayList<TransactionModel> listTransaction;
    private int total;
    int grandtotal ;
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_cart, viewGroup, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder categoryViewHolder, final int position) {

        categoryViewHolder.itemName.setText(getListTransaction().get(position).getNama_produk());
        categoryViewHolder.itemprice.setText("Rp. "+String.valueOf(getListTransaction().get(position).getHarga()));
        categoryViewHolder.itemqty.setText(String.valueOf(getListTransaction().get(position).getJumlah()));
        Bitmap imageBitmap = DbBitmapUtility.getImage(getListTransaction().get(position).getImage());
        Glide.with(context)
                .load(imageBitmap)
                .apply(new RequestOptions())
                .into(categoryViewHolder.imgPhoto);
        grandtotal = getTotal();
        grandtotal += getListTransaction().get(position).getJumlah()*getListTransaction().get(position).getHarga();
        setTotal(grandtotal);
        final int totalItem = getListTransaction().get(position).getJumlah()*getListTransaction().get(position).getHarga();

        Log.e("in adap","RP."+getTotal());
        categoryViewHolder.itemTotal.setText("Rp."+String.valueOf(totalItem));

        categoryViewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusCart(categoryViewHolder,position);
            }
        });

        categoryViewHolder.min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minCart(categoryViewHolder,position);
            }
        });
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    private void plusCart(CategoryViewHolder categoryViewHolder, final int position){
        int jumlah = Integer.parseInt(categoryViewHolder.itemqty.getText().toString());
        jumlah += 1;

        TransactionModel transactionModel = new TransactionModel(
                getListTransaction().get(position).getId(),
                getListTransaction().get(position).getNama_user(),
                getListTransaction().get(position).getTanggal(),
                getListTransaction().get(position).getImage(),
                getListTransaction().get(position).getNama_produk(),
                getListTransaction().get(position).getHarga(),
                jumlah,
                Config.STATUS_TRX_CART,
                null);
        TransactionQuery query = new TransactionQuery(context);

        long id =query.updateTransaction(transactionModel);

        if (id>0){

            int totalHarga = jumlah * getListTransaction().get(position).getHarga();
            categoryViewHolder.itemTotal.setText("Rp."+String.valueOf(totalHarga));
            onAddListener.onAdd(position, getListTransaction().get(position).getHarga());
            categoryViewHolder.itemqty.setText(String.valueOf(jumlah));
        }
        else {
            Log.e("erorr","gagal menambahkan");
//            Toast.makeText(getApplicationContext(), title + " gagal menambahkan ke cart", Toast.LENGTH_SHORT).show();
        }
    }

    private void minCart(CategoryViewHolder categoryViewHolder, final int position){
        int jumlah = Integer.parseInt(categoryViewHolder.itemqty.getText().toString());

        if (jumlah <1 ){
            deleteCard(categoryViewHolder,position);

        }
        else if (jumlah > 0){
            jumlah -= 1;
            int harga = getListTransaction().get(position).getHarga();
            if (jumlah < 1) {
                onMinListener.onMin(position, harga,0);
                deleteCard(categoryViewHolder,position);

                categoryViewHolder.itemTotal.setText("Rp."+String.valueOf(0));

            }
            else{

                TransactionModel transactionModel = new TransactionModel(
                        getListTransaction().get(position).getId(),
                        getListTransaction().get(position).getNama_user(),
                        getListTransaction().get(position).getTanggal(),
                        getListTransaction().get(position).getImage(),
                        getListTransaction().get(position).getNama_produk(),
                        getListTransaction().get(position).getHarga(),
                        jumlah,
                        Config.STATUS_TRX_CART,
                        null);
                TransactionQuery query = new TransactionQuery(context);

                long id =query.updateTransaction(transactionModel);

                if (id>0){
                    int totalHarga = jumlah * getListTransaction().get(position).getHarga();
                    categoryViewHolder.itemTotal.setText("Rp."+String.valueOf(totalHarga));
                    onMinListener.onMin(position, getListTransaction().get(position).getHarga(),jumlah);
                    categoryViewHolder.itemqty.setText(String.valueOf(jumlah));
                }
                else {
//                    Toast.makeText(getApplicationContext(), title + " gagal menambahkan ke cart", Toast.LENGTH_SHORT).show();
                    Log.e("erorr","gagal Mengurangi");
                }
            }
        }
    }

    private void deleteCard(CategoryViewHolder categoryViewHolder, final int position){

        TransactionQuery query = new TransactionQuery(context);

        long count =query.deleteTransactionByID(getListTransaction().get(position).getId());

        if (count>0){
//            Toast.makeText(getApplicationContext(), title+" telah ditambahkan ke cart", Toast.LENGTH_SHORT).show();
            categoryViewHolder.itemqty.setText("0");
            getListTransaction().remove(position);
            notifyDataSetChanged();
        }
        else {
//            Toast.makeText(getApplicationContext(), title + " gagal menambahkan ke cart", Toast.LENGTH_SHORT).show();
            Log.e("erorr","gagal mendelete");
        }
    }

    public ItemProductCartAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {

        return getListTransaction().size();
//        return 0;
    }

    public ArrayList<TransactionModel> getListTransaction() {
        return listTransaction;
    }

    public void setListTransaction(ArrayList<TransactionModel> listTransaction) {
        this.listTransaction = listTransaction;
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        CardView itemcard;
        TextView itemName, itemprice, itemqty, itemTotal;
        Button plus,min;
        CircleImageView imgPhoto;

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemcard = itemView.findViewById(R.id.item_cart_cardView);
            imgPhoto = itemView.findViewById(R.id.item_cart_circleImageView);
            itemName = itemView.findViewById(R.id.item_cart_product_name);
            itemprice = itemView.findViewById(R.id.item_cart_product_price);
            itemqty = itemView.findViewById(R.id.item_cart_qty);
            itemTotal = itemView.findViewById(R.id.product_cart_total);
            plus = itemView.findViewById(R.id.item_cart_btnPlus);
            min = itemView.findViewById(R.id.item_cart_btnMin);
        }
    }

    public interface OnAddListener {
        public void onAdd(int position, int harga);
    }
    private OnAddListener onAddListener;
    public void setOnAddListener(OnAddListener listener) {
        this.onAddListener = listener;
    }

    public interface OnMinListener {
        public void onMin(int position, int harga, int jumlah);
    }
    private OnMinListener onMinListener;
    public void setOnMinListener(OnMinListener listener) {
        this.onMinListener = listener;
    }
}